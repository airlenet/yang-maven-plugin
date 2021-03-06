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

import com.airlenet.yang.compiler.datamodel.YangModule;
import com.airlenet.yang.compiler.datamodel.utils.Parsable;
import com.airlenet.yang.compiler.parser.exceptions.ParserException;
import com.airlenet.yang.compiler.parser.impl.TreeWalkListener;

import java.net.URI;

import static com.airlenet.yang.compiler.datamodel.utils.YangConstructType.NAMESPACE_DATA;
import static com.airlenet.yang.compiler.parser.antlrgencode.GeneratedYangParser.NamespaceStatementContext;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorLocation.ENTRY;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorMessageConstruction.constructListenerErrorMessage;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorType.INVALID_HOLDER;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerErrorType.MISSING_HOLDER;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerUtil.removeQuotesAndHandleConcat;
import static com.airlenet.yang.compiler.parser.impl.parserutils.ListenerValidation.checkStackIsNotEmpty;

/*
 * Reference: RFC6020 and YANG ANTLR Grammar
 *
 * ABNF grammar as per RFC6020
 * module-header-stmts = ;; these stmts can appear in any order
 *                       [yang-version-stmt stmtsep]
 *                        namespace-stmt stmtsep
 *                        prefix-stmt stmtsep
 *
 * namespace-stmt      = namespace-keyword sep uri-str optsep stmtend
 *
 * ANTLR grammar rule
 * module_header_statement : yang_version_stmt? namespace_stmt prefix_stmt
 *                         | yang_version_stmt? prefix_stmt namespace_stmt
 *                         | namespace_stmt yang_version_stmt? prefix_stmt
 *                         | namespace_stmt prefix_stmt yang_version_stmt?
 *                         | prefix_stmt namespace_stmt yang_version_stmt?
 *                         | prefix_stmt yang_version_stmt? namespace_stmt
 *                         ;
 * namespace_stmt : NAMESPACE_KEYWORD string STMTEND;
 */

/**
 * Represents listener based call back function corresponding to the "namespace"
 * rule defined in ANTLR grammar file for corresponding ABNF rule in RFC 6020.
 */
public final class NamespaceListener {

    /**
     * Creates a new namespace listener.
     */
    private NamespaceListener() {
    }

    /**
     * It is called when parser receives an input matching the grammar rule
     * (namespace), perform validations and update the data model tree.
     *
     * @param listener Listener's object
     * @param ctx      context object of the grammar rule
     */
    public static void processNamespaceEntry(TreeWalkListener listener,
                                             NamespaceStatementContext ctx) {

        // Check for stack to be non empty.
        checkStackIsNotEmpty(listener, MISSING_HOLDER, NAMESPACE_DATA, ctx.string().getText(), ENTRY);

        if (!validateUriValue(ctx.string().getText())) {
            ParserException parserException = new ParserException("YANG file error: Invalid namespace URI");
            parserException.setLine(ctx.string().STRING(0).getSymbol().getLine());
            parserException.setCharPosition(ctx.string().STRING(0).getSymbol().getCharPositionInLine());
            throw parserException;
        }

        // Obtain the node of the stack.
        Parsable tmpNode = listener.getParsedDataStack().peek();
        switch (tmpNode.getYangConstructType()) {
            case MODULE_DATA: {
                YangModule module = (YangModule) tmpNode;
                module.setModuleNamespace(removeQuotesAndHandleConcat(ctx.string().getText()));
                break;
            }
            default:
                throw new ParserException(constructListenerErrorMessage(INVALID_HOLDER, NAMESPACE_DATA,
                        ctx.string().getText(), ENTRY));
        }
    }

    /**
     * Validate input URI.
     *
     * @param uri input namespace URI
     * @return validation result
     */
    private static boolean validateUriValue(String uri) {
        uri = uri.replace("\"", "");
        try {
            URI.create(uri);
        } catch (Exception e1) {
            return false;
        }
        return true;
    }
}
