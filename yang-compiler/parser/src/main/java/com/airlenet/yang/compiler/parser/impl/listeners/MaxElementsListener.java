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

import com.airlenet.yang.compiler.datamodel.YangMaxElement;
import com.airlenet.yang.compiler.datamodel.YangMaxElementHolder;
import com.airlenet.yang.compiler.datamodel.utils.Parsable;
import com.airlenet.yang.compiler.datamodel.utils.YangConstructType;
import com.airlenet.yang.compiler.parser.exceptions.ParserException;
import com.airlenet.yang.compiler.parser.impl.TreeWalkListener;
import com.airlenet.yang.compiler.parser.impl.parserutils.*;

import static com.airlenet.yang.compiler.datamodel.utils.YangConstructType.MAX_ELEMENT_DATA;
import static com.airlenet.yang.compiler.parser.antlrgencode.GeneratedYangParser.MaxElementsStatementContext;

/*
 * Reference: RFC6020 and YANG ANTLR Grammar
 *
 * ABNF grammar as per RFC6020
 *  max-elements-stmt   = max-elements-keyword sep
 *                        max-value-arg-str stmtend
 *  max-value-arg-str   = < a string that matches the rule
 *                          max-value-arg >
 *
 * ANTLR grammar rule
 * maxElementsStatement : MAX_ELEMENTS_KEYWORD maxValue STMTEND;
 * maxValue             : string;
 */

/**
 * Represents listener based call back function corresponding to the
 * "max-elements" rule defined in ANTLR grammar file for corresponding ABNF rule
 * in RFC 6020.
 */
public final class MaxElementsListener {

    private static final String POSITIVE_INTEGER_PATTERN = "[1-9][0-9]*";
    private static final String UNBOUNDED_KEYWORD = "unbounded";

    /**
     * Creates a new max-elements listener.
     */
    private MaxElementsListener() {
    }

    /**
     * It is called when parser receives an input matching the grammar rule
     * (max-elements), performs validation and updates the data model tree.
     *
     * @param listener listener's object
     * @param ctx      context object of the grammar rule
     */
    public static void processMaxElementsEntry(TreeWalkListener listener,
                                               MaxElementsStatementContext ctx) {

        // Check for stack to be non empty.
        ListenerValidation.checkStackIsNotEmpty(listener, ListenerErrorType.MISSING_HOLDER, MAX_ELEMENT_DATA, "", ListenerErrorLocation.ENTRY);

        int maxElementsValue = getValidMaxElementValue(ctx);

        YangMaxElement maxElement = new YangMaxElement();
        maxElement.setMaxElement(maxElementsValue);

        maxElement.setLineNumber(ctx.getStart().getLine());
        maxElement.setCharPosition(ctx.getStart().getCharPositionInLine());
        maxElement.setFileName(listener.getFileName());
        Parsable tmpData = listener.getParsedDataStack().peek();
        if (tmpData instanceof YangMaxElementHolder) {
            YangMaxElementHolder holder = ((YangMaxElementHolder) tmpData);
            holder.setMaxElements(maxElement);
        } else {
            throw new ParserException(ListenerErrorMessageConstruction.constructListenerErrorMessage(
                    ListenerErrorType.INVALID_HOLDER, MAX_ELEMENT_DATA, "", ListenerErrorLocation.ENTRY));
        }
    }

    /**
     * Validates max element value and returns the value from context.
     *
     * @param ctx context object of the grammar rule
     * @return max element's value
     */
    private static int getValidMaxElementValue(MaxElementsStatementContext ctx) {

        int maxElementsValue;

        String value = ListenerUtil.removeQuotesAndHandleConcat(ctx.maxValue().getText());
        if (value.equals(UNBOUNDED_KEYWORD)) {
            maxElementsValue = Integer.MAX_VALUE;
        } else if (value.matches(POSITIVE_INTEGER_PATTERN)) {
            try {
                maxElementsValue = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                ParserException parserException = new ParserException("YANG file error : " +
                                                                              YangConstructType.getYangConstructType(
                                                                                      MAX_ELEMENT_DATA) + " value " +
                                                                              value + " is not " +
                                                                              "valid.");
                parserException.setLine(ctx.getStart().getLine());
                parserException.setCharPosition(ctx.getStart().getCharPositionInLine());
                throw parserException;
            }
        } else {
            ParserException parserException = new ParserException("YANG file error : " +
                                                                          YangConstructType.getYangConstructType(
                                                                                  MAX_ELEMENT_DATA) + " value " +
                                                                          value + " is not " +
                                                                          "valid.");
            parserException.setLine(ctx.getStart().getLine());
            parserException.setCharPosition(ctx.getStart().getCharPositionInLine());
            throw parserException;
        }

        return maxElementsValue;
    }
}