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

import com.airlenet.yang.compiler.datamodel.YangNode;
import com.airlenet.yang.compiler.datamodel.YangOutput;
import com.airlenet.yang.compiler.datamodel.YangRpc;
import com.airlenet.yang.compiler.datamodel.exceptions.DataModelException;
import com.airlenet.yang.compiler.datamodel.utils.Parsable;
import com.airlenet.yang.compiler.parser.exceptions.ParserException;
import com.airlenet.yang.compiler.parser.impl.TreeWalkListener;
import com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorLocation;
import com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorMessageConstruction;
import com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorType;
import com.airlenet.yang.compiler.parser.impl.parserutils.ListenerValidation;

import static com.airlenet.yang.compiler.datamodel.utils.GeneratedLanguage.JAVA_GENERATION;
import static com.airlenet.yang.compiler.datamodel.utils.YangConstructType.OUTPUT_DATA;
import static com.airlenet.yang.compiler.parser.antlrgencode.GeneratedYangParser.OutputStatementContext;
import static com.airlenet.yang.compiler.translator.tojava.YangDataModelFactory.getYangOutputNode;
import static com.airlenet.yang.compiler.utils.UtilConstants.OUTPUT;

/*
 * Reference: RFC6020 and YANG ANTLR Grammar
 *
 * ABNF grammar as per RFC6020
 *
 *  output-stmt         = output-keyword optsep
 *                        "{" stmtsep
 *                            ;; these stmts can appear in any order
 *                            *((typedef-stmt /
 *                               grouping-stmt) stmtsep)
 *                            1*(data-def-stmt stmtsep)
 *                        "}"
 *
 *  outputStatement : OUTPUT_KEYWORD LEFT_CURLY_BRACE outputStatementBody RIGHT_CURLY_BRACE;

 *  outputStatementBody : typedefStatement* dataDefStatement+
 *                      | dataDefStatement+ typedefStatement*
 *                      | groupingStatement* dataDefStatement+
 *                      | dataDefStatement+ groupingStatement*;
 */

/**
 * Represents listener based call back function corresponding to the "output"
 * rule defined in ANTLR grammar file for corresponding ABNF rule in RFC 6020.
 */
public final class OutputListener {

    /**
     * Creates a new output listener.
     */
    private OutputListener() {
    }

    /**
     * It is called when parser receives an input matching the grammar rule
     * (output), performs validation and updates the data model tree.
     *
     * @param listener listener's object
     * @param ctx      context object of the grammar rule
     */
    public static void processOutputEntry(TreeWalkListener listener,
                                          OutputStatementContext ctx) {

        // Check for stack to be non empty.
        ListenerValidation.checkStackIsNotEmpty(listener, ListenerErrorType.MISSING_HOLDER, OUTPUT_DATA, "", ListenerErrorLocation.ENTRY);

        Parsable curData = listener.getParsedDataStack().peek();
        if (curData instanceof YangRpc) {

            YangOutput yangOutput = getYangOutputNode(JAVA_GENERATION);
            yangOutput.setName(OUTPUT);
            yangOutput.setLineNumber(ctx.getStart().getLine());
            yangOutput.setCharPosition(ctx.getStart().getCharPositionInLine());
            yangOutput.setFileName(listener.getFileName());
            YangNode curNode = (YangNode) curData;
            try {
                curNode.addChild(yangOutput);
            } catch (DataModelException e) {
                throw new ParserException(ListenerErrorMessageConstruction.constructExtendedListenerErrorMessage(ListenerErrorType.UNHANDLED_PARSED_DATA,
                                                                                OUTPUT_DATA,
                                                                                "", ListenerErrorLocation.ENTRY, e.getMessage()));
            }
            listener.getParsedDataStack().push(yangOutput);
        } else {
            throw new ParserException(ListenerErrorMessageConstruction.constructListenerErrorMessage(ListenerErrorType.INVALID_HOLDER, OUTPUT_DATA,
                                                                    "", ListenerErrorLocation.ENTRY));
        }
    }

    /**
     * It is called when parser exits from grammar rule (output), it perform
     * validations and updates the data model tree.
     *
     * @param listener listener's object
     * @param ctx      context object of the grammar rule
     */
    public static void processOutputExit(TreeWalkListener listener,
                                         OutputStatementContext ctx) {

        // Check for stack to be non empty.
        ListenerValidation.checkStackIsNotEmpty(listener, ListenerErrorType.MISSING_HOLDER, OUTPUT_DATA, "", ListenerErrorLocation.EXIT);

        if (!(listener.getParsedDataStack().peek() instanceof YangOutput)) {
            throw new ParserException(ListenerErrorMessageConstruction.constructListenerErrorMessage(ListenerErrorType.MISSING_CURRENT_HOLDER, OUTPUT_DATA,
                                                                    "", ListenerErrorLocation.EXIT));
        }
        listener.getParsedDataStack().pop();
    }
}
