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
package com.airlenet.yang.compiler.translator.tojava.javamodel;

import com.airlenet.yang.compiler.datamodel.javadatamodel.YangJavaLeafList;
import com.airlenet.yang.compiler.translator.tojava.JavaQualifiedTypeInfoTranslator;
import com.airlenet.yang.compiler.utils.io.YangToJavaNamingConflictUtil;

import static com.airlenet.yang.compiler.translator.tojava.JavaQualifiedTypeInfoTranslator.updateLeavesJavaQualifiedInfo;
import static com.airlenet.yang.compiler.utils.io.impl.YangIoUtils.getCamelCase;

/**
 * Represents java information corresponding to the YANG leaf-list.
 */
public class YangJavaLeafListTranslator
        extends YangJavaLeafList
        implements JavaLeafInfoContainer {

    private static final long serialVersionUID = 806201638L;

    private transient YangToJavaNamingConflictUtil conflictResolveConfig;

    /**
     * Returns a new YANG leaf object with java qualified access details.
     */
    public YangJavaLeafListTranslator() {
        super();
        setJavaQualifiedInfo(new JavaQualifiedTypeInfoTranslator());
    }

    @Override
    public String getJavaName(YangToJavaNamingConflictUtil conflictResolveConfig) {
        return getCamelCase(getName(), conflictResolveConfig);
    }

    @Override
    public boolean isLeafList() {
        return true;
    }

    @Override
    public void updateJavaQualifiedInfo() {
        updateLeavesJavaQualifiedInfo(this);
    }

    /**
     * Returns java naming conflict resolve configurations.
     *
     * @return java naming conflict resolve configurations
     */
    @Override
    public YangToJavaNamingConflictUtil getConflictResolveConfig() {
        return conflictResolveConfig;
    }

    /**
     * Sets java naming conflict resolve configurations.
     *
     * @param conflictResolveConfig java naming conflict resolve configurations
     */
    @Override
    public void setConflictResolveConfig(YangToJavaNamingConflictUtil conflictResolveConfig) {
        this.conflictResolveConfig = conflictResolveConfig;
    }
}
