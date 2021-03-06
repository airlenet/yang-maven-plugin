/*
 * Copyright 2016-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.airlenet.yang.compiler.parser.impl.listeners;

import com.airlenet.yang.compiler.datamodel.YangContainer;
import com.airlenet.yang.compiler.datamodel.YangGrouping;
import com.airlenet.yang.compiler.datamodel.YangInput;
import com.airlenet.yang.compiler.datamodel.YangList;
import com.airlenet.yang.compiler.datamodel.YangModule;
import com.airlenet.yang.compiler.datamodel.YangNode;
import com.airlenet.yang.compiler.datamodel.YangNotification;
import com.airlenet.yang.compiler.datamodel.YangOutput;
import com.airlenet.yang.compiler.datamodel.YangRpc;
import com.airlenet.yang.compiler.datamodel.YangSubModule;
import com.airlenet.yang.compiler.datamodel.YangTypeDef;
import com.airlenet.yang.compiler.datamodel.exceptions.DataModelException;
import com.airlenet.yang.compiler.datamodel.utils.Parsable;
import com.airlenet.yang.compiler.parser.exceptions.ParserException;
import com.airlenet.yang.compiler.parser.impl.TreeWalkListener;

import static com.airlenet.yang.compiler.datamodel.utils.GeneratedLanguage.JAVA_GENERATION;
import static com.airlenet.yang.compiler.datamodel.utils.YangConstructType.DEFAULT_DATA;
import static com.airlenet.yang.compiler.datamodel.utils.YangConstructType.DESCRIPTION_DATA;
import static com.airlenet.yang.compiler.datamodel.utils.YangConstructType.REFERENCE_DATA;
import static com.airlenet.yang.compiler.datamodel.utils.YangConstructType.STATUS_DATA;
import static com.airlenet.yang.compiler.datamodel.utils.YangConstructType.TYPEDEF_DATA;
import static com.airlenet.yang.compiler.datamodel.utils.YangConstructType.TYPE_DATA;
import static com.airlenet.yang.compiler.datamodel.utils.YangConstructType.UNITS_DATA;
import static com.airlenet.yang.compiler.parser.antlrgencode.GeneratedYangParser.TypedefStatementContext;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerCollisionDetector.detectCollidingChildUtil;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorLocation.ENTRY;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorLocation.EXIT;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorMessageConstruction.constructExtendedListenerErrorMessage;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorMessageConstruction.constructListenerErrorMessage;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorType.INVALID_CONTENT;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorType.INVALID_HOLDER;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorType.MISSING_CURRENT_HOLDER;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorType.MISSING_HOLDER;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorType.UNHANDLED_PARSED_DATA;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerUtil.getValidIdentifier;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerValidation.checkStackIsNotEmpty;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerValidation.validateCardinalityEqualsOne;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerValidation.validateCardinalityMaxOne;
import static com.airlenet.yang.compiler.translator.tojava.YangDataModelFactory.getYangTypeDefNode;

/*
 * Reference: RFC6020 and YANG ANTLR Grammar
 *
 * body-stmts          = *((extension-stmt /
 *                          feature-stmt /
 *                          identity-stmt /
 *                          typedef-stmt /
 *                          grouping-stmt /
 *                          data-def-stmt /
 *                          augment-stmt /
 *                          rpc-stmt /
 *                          notification-stmt /
 *                          deviation-stmt) stmtsep)
 *
 * typedef-stmt        = typedef-keyword sep identifier-arg-str optsep
 *                       "{" stmtsep
 *                           ;; these stmts can appear in any order
 *                           type-stmt stmtsep
 *                          [units-stmt stmtsep]
 *                           [default-stmt stmtsep]
 *                           [status-stmt stmtsep]
 *                           [description-stmt stmtsep]
 *                           [reference-stmt stmtsep]
 *                         "}"
 *
 * ANTLR grammar rule
 * typedefStatement : TYPEDEF_KEYWORD identifier LEFT_CURLY_BRACE
 *                (typeStatement | unitsStatement | defaultStatement | statusStatement
 *                | descriptionStatement | referenceStatement)* RIGHT_CURLY_BRACE;
 */

/**
 * Represents listener based call back function corresponding to the "typedef"
 * rule defined in ANTLR grammar file for corresponding ABNF rule in RFC 6020.
 */
public final class TypeDefListener {

    /**
     * Creates a new typedef listener.
     */
    private TypeDefListener() {
    }

    /**
     * It is called when parser enters grammar rule (typedef), it perform
     * validations and updates the data model tree.
     *
     * @param listener listener's object
     * @param ctx      context object of the grammar rule
     */
    public static void processTypeDefEntry(TreeWalkListener listener,
                                           TypedefStatementContext ctx) {

        // Check for stack to be non empty.
        checkStackIsNotEmpty(listener, MISSING_HOLDER, TYPEDEF_DATA, ctx.identifier().getText(), ENTRY);

        String identifier = getValidIdentifier(ctx.identifier().getText(), TYPEDEF_DATA, ctx);

        // Validate sub statement cardinality.
        validateSubStatementsCardinality(ctx);

        // Check for identifier collision
        int line = ctx.getStart().getLine();
        int charPositionInLine = ctx.getStart().getCharPositionInLine();
        detectCollidingChildUtil(listener, line, charPositionInLine, identifier, TYPEDEF_DATA);

        /*
         * Create a derived type information, the base type must be set in type
         * listener.
         */
        YangTypeDef typeDefNode = getYangTypeDefNode(JAVA_GENERATION);
        typeDefNode.setName(identifier);

        typeDefNode.setLineNumber(ctx.getStart().getLine());
        typeDefNode.setCharPosition(ctx.getStart().getCharPositionInLine());
        typeDefNode.setFileName(listener.getFileName());
        Parsable curData = listener.getParsedDataStack().peek();

        if (curData instanceof YangModule || curData instanceof YangSubModule || curData instanceof YangContainer
                || curData instanceof YangList || curData instanceof YangNotification || curData instanceof YangRpc
                || curData instanceof YangInput || curData instanceof YangOutput || curData instanceof YangGrouping) {

            YangNode curNode = (YangNode) curData;
            try {
                curNode.addChild(typeDefNode);
            } catch (DataModelException e) {
                throw new ParserException(constructExtendedListenerErrorMessage(UNHANDLED_PARSED_DATA,
                        TYPEDEF_DATA, ctx.identifier().getText(), ENTRY, e.getMessage()));
            }
            listener.getParsedDataStack().push(typeDefNode);
        } else {
            throw new ParserException(constructListenerErrorMessage(INVALID_HOLDER,
                    TYPEDEF_DATA, ctx.identifier().getText(), ENTRY));
        }
    }

    /**
     * It is called when parser exits from grammar rule (typedef), it perform
     * validations and updates the data model tree.
     *
     * @param listener listener's object
     * @param ctx      context object of the grammar rule
     */
    public static void processTypeDefExit(TreeWalkListener listener,
                                          TypedefStatementContext ctx) {

        // Check for stack to be non empty.
        checkStackIsNotEmpty(listener, MISSING_HOLDER, TYPEDEF_DATA, ctx.identifier().getText(), EXIT);

        if (listener.getParsedDataStack().peek() instanceof YangTypeDef) {
            YangTypeDef typeDefNode = (YangTypeDef) listener.getParsedDataStack().peek();
            try {
                typeDefNode.validateDataOnExit();
            } catch (DataModelException e) {
                throw new ParserException(constructListenerErrorMessage(INVALID_CONTENT, TYPEDEF_DATA,
                        ctx.identifier().getText(), EXIT));
            }

            listener.getParsedDataStack().pop();
        } else {
            throw new ParserException(constructListenerErrorMessage(MISSING_CURRENT_HOLDER, TYPEDEF_DATA,
                    ctx.identifier().getText(), EXIT));
        }
    }

    /**
     * Validates the cardinality of typedef sub-statements as per grammar.
     *
     * @param ctx context object of the grammar rule
     */
    private static void validateSubStatementsCardinality(TypedefStatementContext ctx) {

        validateCardinalityMaxOne(ctx.unitsStatement(), UNITS_DATA, TYPEDEF_DATA, ctx.identifier().getText());
        validateCardinalityMaxOne(ctx.defaultStatement(), DEFAULT_DATA, TYPEDEF_DATA, ctx.identifier().getText());
        validateCardinalityEqualsOne(ctx.typeStatement(), TYPE_DATA, TYPEDEF_DATA, ctx.identifier().getText(), ctx);
        validateCardinalityMaxOne(ctx.descriptionStatement(), DESCRIPTION_DATA, TYPEDEF_DATA,
                ctx.identifier().getText());
        validateCardinalityMaxOne(ctx.referenceStatement(), REFERENCE_DATA, TYPEDEF_DATA, ctx.identifier().getText());
        validateCardinalityMaxOne(ctx.statusStatement(), STATUS_DATA, TYPEDEF_DATA, ctx.identifier().getText());
    }
}
