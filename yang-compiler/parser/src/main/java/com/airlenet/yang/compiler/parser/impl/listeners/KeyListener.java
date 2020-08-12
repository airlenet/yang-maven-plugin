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

import com.airlenet.yang.compiler.datamodel.YangList;
import com.airlenet.yang.compiler.datamodel.exceptions.DataModelException;
import com.airlenet.yang.compiler.datamodel.utils.Parsable;
import com.airlenet.yang.compiler.parser.exceptions.ParserException;
import com.airlenet.yang.compiler.parser.impl.TreeWalkListener;
import com.airlenet.yang.compiler.parser.impl.parserutils.*;

import static com.airlenet.yang.compiler.datamodel.utils.YangConstructType.KEY_DATA;
import static com.airlenet.yang.compiler.parser.antlrgencode.GeneratedYangParser.KeyStatementContext;

/*
 * Reference: RFC6020 and YANG ANTLR Grammar
 *
 * ABNF grammar as per RFC6020
 * key-stmt            = key-keyword sep key-arg-str stmtend
 *
 * ANTLR grammar rule
 * keyStatement : KEY_KEYWORD key STMTEND;
 * key          : string;
 */

/**
 * Represesnts listener based call back function corresponding to the "key" rule
 * defined in ANTLR grammar file for corresponding ABNF rule in RFC 6020.
 */
public final class KeyListener {

    /**
     * Creates a new key listener.
     */
    private KeyListener() {
    }

    /**
     * It is called when parser receives an input matching the grammar rule
     * (key), perform validations and updates the data model tree.
     *
     * @param listener listener's object
     * @param ctx      context object of the grammar rule
     */
    public static void processKeyEntry(TreeWalkListener listener,
                                       KeyStatementContext ctx) {

        // Check for stack to be non empty.
        ListenerValidation.checkStackIsNotEmpty(listener, ListenerErrorType.MISSING_HOLDER, KEY_DATA, ctx.key().getText(), ListenerErrorLocation.ENTRY);

        Parsable tmpData = listener.getParsedDataStack().peek();
        if (listener.getParsedDataStack().peek() instanceof YangList) {
            YangList yangList = (YangList) tmpData;
            String tmpKeyValue = ListenerUtil.removeQuotesAndHandleConcat(ctx.key().getText());
            if (tmpKeyValue.contains(" ")) {
                String[] keyValues = tmpKeyValue.split(" ");
                for (String keyValue : keyValues) {
                    try {
                        yangList.addKey(keyValue);
                    } catch (DataModelException e) {
                        throw new ParserException(ListenerErrorMessageConstruction.constructExtendedListenerErrorMessage(ListenerErrorType.UNHANDLED_PARSED_DATA, KEY_DATA,
                                ctx.key().getText(), ListenerErrorLocation.ENTRY, e.getMessage()));
                    }
                }
            } else {
                try {
                    yangList.addKey(tmpKeyValue);
                } catch (DataModelException e) {
                    throw new ParserException(ListenerErrorMessageConstruction.constructExtendedListenerErrorMessage(ListenerErrorType.UNHANDLED_PARSED_DATA, KEY_DATA,
                            ctx.key().getText(), ListenerErrorLocation.ENTRY, e.getMessage()));
                }
            }
        } else {
            throw new ParserException(ListenerErrorMessageConstruction.constructListenerErrorMessage(ListenerErrorType.INVALID_HOLDER, KEY_DATA, ctx.key().getText(),
                    ListenerErrorLocation.ENTRY));
        }
    }
}