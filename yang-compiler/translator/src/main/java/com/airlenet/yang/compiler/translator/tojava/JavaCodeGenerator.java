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

package com.airlenet.yang.compiler.translator.tojava;

import com.airlenet.yang.compiler.translator.exception.TranslatorException;
import com.airlenet.yang.compiler.utils.io.YangPluginConfig;

import java.io.IOException;

/**
 * Abstraction of an entity which provides Code generator functionalities.
 */
public interface JavaCodeGenerator {

    /**
     * Traverse the schema of application and generate corresponding code.
     *
     * @param yangPlugin YANG plugin config
     * @throws TranslatorException when fails to translate the data model tree
     */
    void generateCodeEntry(YangPluginConfig yangPlugin)
            throws TranslatorException;

    /**
     * Traverse the schema of application and generate corresponding code.
     *
     * @throws TranslatorException when fails to generate java code
     * @throws IOException         a violation in IO rule
     */
    void generateCodeExit()
            throws TranslatorException, IOException;
}
