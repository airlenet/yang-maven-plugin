/* 
 * @(#)User.java        1.0 31/10/17
 *
 * This file has been auto-generated by JNC, the
 * Java output format plug-in of pyang.
 * Origin: module "demo", revision: "unknown".
 */

package com.airlenet.yang.model.demo.demo;

import com.airlenet.yang.model.demo.Demo;
import com.tailf.jnc.Element;
import com.tailf.jnc.JNCException;
import com.tailf.jnc.Leaf;
import com.tailf.jnc.YangElement;
import com.tailf.jnc.YangString;
import com.tailf.jnc.YangUInt32;
import com.tailf.jnc.YangUInt8;

/**
 * This class represents an element from 
 * the namespace http://tail-f.com/ns/example/demo
 * generated to "/Users/lig/Documents/workspace/play-yang/yang-example/src/main/java/com.airlenet.yang.model/demo/demo/user"
 * <p>
 * See line 26 in
 * /Users/lig/Documents/workspace/play-yang/yang-example/src/main/yang/demo.yang
 *
 * @version 1.0 2017-10-31
 * @author Auto Generated
 */
public class User extends YangElement {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor for an empty User object.
     */
    public User() {
        super(Demo.NAMESPACE, "user");
    }

    /**
     * Constructor for an initialized User object,
     * 
     * @param idValue Key argument of child.
     */
    public User(YangUInt32 idValue) throws JNCException {
        super(Demo.NAMESPACE, "user");
        Leaf id = new Leaf(Demo.NAMESPACE, "id");
        id.setValue(idValue);
        insertChild(id, childrenNames());
    }

    /**
     * Constructor for an initialized User object,
     * with String keys.
     * @param idValue Key argument of child.
     */
    public User(String idValue) throws JNCException {
        super(Demo.NAMESPACE, "user");
        Leaf id = new Leaf(Demo.NAMESPACE, "id");
        id.setValue(new YangUInt32(idValue));
        insertChild(id, childrenNames());
    }

    /**
     * Constructor for an initialized User object,
     * with keys of built in Java types.
     * @param idValue Key argument of child.
     */
    public User(long idValue) throws JNCException {
        super(Demo.NAMESPACE, "user");
        Leaf id = new Leaf(Demo.NAMESPACE, "id");
        id.setValue(new YangUInt32(idValue));
        insertChild(id, childrenNames());
    }

    /**
     * Clones this object, returning an exact copy.
     * @return A clone of the object.
     */
    public User clone() {
        User copy;
        try {
            copy = new User(getIdValue().toString());
        } catch (JNCException e) {
            copy = null;
        }
        return (User)cloneContent(copy);
    }

    /**
     * Clones this object, returning a shallow copy.
     * @return A clone of the object. Children are not included.
     */
    public User cloneShallow() {
        User copy;
        try {
            copy = new User(getIdValue().toString());
        } catch (JNCException e) {
            copy = null;
        }
        return (User)cloneShallowContent(copy);
    }

    /**
     * @return An array with the identifiers of any key children
     */
    public String[] keyNames() {
        return new String[] {
            "id",
        };
    }

    /**
     * @return An array with the identifiers of any children, in order.
     */
    public String[] childrenNames() {
        return new String[] {
            "id",
            "name",
            "age",
            "title",
            "city",
        };
    }

    /* Access methods for leaf child: "id". */

    /**
     * Gets the value for child leaf "id".
     * @return The value of the leaf.
     */
    public YangUInt32 getIdValue() throws JNCException {
        return (YangUInt32)getValue("id");
    }

    /**
     * Sets the value for child leaf "id",
     * using instance of generated typedef class.
     * @param idValue The value to set.
     * @param idValue used during instantiation.
     */
    public void setIdValue(YangUInt32 idValue) throws JNCException {
        setLeafValue(Demo.NAMESPACE,
            "id",
            idValue,
            childrenNames());
    }

    /**
     * Sets the value for child leaf "id",
     * using Java primitive values.
     * @param idValue used during instantiation.
     */
    public void setIdValue(long idValue) throws JNCException {
        setIdValue(new YangUInt32(idValue));
    }

    /**
     * Sets the value for child leaf "id",
     * using a String value.
     * @param idValue used during instantiation.
     */
    public void setIdValue(String idValue) throws JNCException {
        setIdValue(new YangUInt32(idValue));
    }

    /**
     * This method is used for creating a subtree filter.
     * The added "id" leaf will not have a value.
     */
    public void addId() throws JNCException {
        setLeafValue(Demo.NAMESPACE,
            "id",
            null,
            childrenNames());
    }

    /* Access methods for optional leaf child: "name". */

    /**
     * Gets the value for child leaf "name".
     * @return The value of the leaf.
     */
    public YangString getNameValue() throws JNCException {
        return (YangString)getValue("name");
    }

    /**
     * Sets the value for child leaf "name",
     * using instance of generated typedef class.
     * @param nameValue The value to set.
     * @param nameValue used during instantiation.
     */
    public void setNameValue(YangString nameValue) throws JNCException {
        setLeafValue(Demo.NAMESPACE,
            "name",
            nameValue,
            childrenNames());
    }

    /**
     * Sets the value for child leaf "name",
     * using a String value.
     * @param nameValue used during instantiation.
     */
    public void setNameValue(String nameValue) throws JNCException {
        setNameValue(new YangString(nameValue));
    }

    /**
     * Unsets the value for child leaf "name".
     */
    public void unsetNameValue() throws JNCException {
        delete("name");
    }

    /**
     * This method is used for creating a subtree filter.
     * The added "name" leaf will not have a value.
     */
    public void addName() throws JNCException {
        setLeafValue(Demo.NAMESPACE,
            "name",
            null,
            childrenNames());
    }

    /**
     * Marks the leaf "name" with operation "replace".
     */
    public void markNameReplace() throws JNCException {
        markLeafReplace("name");
    }

    /**
     * Marks the leaf "name" with operation "merge".
     */
    public void markNameMerge() throws JNCException {
        markLeafMerge("name");
    }

    /**
     * Marks the leaf "name" with operation "create".
     */
    public void markNameCreate() throws JNCException {
        markLeafCreate("name");
    }

    /**
     * Marks the leaf "name" with operation "delete".
     */
    public void markNameDelete() throws JNCException {
        markLeafDelete("name");
    }

    /* Access methods for optional leaf child: "age". */

    /**
     * Gets the value for child leaf "age".
     * @return The value of the leaf.
     */
    public YangUInt8 getAgeValue() throws JNCException {
        return (YangUInt8)getValue("age");
    }

    /**
     * Sets the value for child leaf "age",
     * using instance of generated typedef class.
     * @param ageValue The value to set.
     * @param ageValue used during instantiation.
     */
    public void setAgeValue(YangUInt8 ageValue) throws JNCException {
        setLeafValue(Demo.NAMESPACE,
            "age",
            ageValue,
            childrenNames());
    }

    /**
     * Sets the value for child leaf "age",
     * using Java primitive values.
     * @param ageValue used during instantiation.
     */
    public void setAgeValue(short ageValue) throws JNCException {
        setAgeValue(new YangUInt8(ageValue));
    }

    /**
     * Sets the value for child leaf "age",
     * using a String value.
     * @param ageValue used during instantiation.
     */
    public void setAgeValue(String ageValue) throws JNCException {
        setAgeValue(new YangUInt8(ageValue));
    }

    /**
     * Unsets the value for child leaf "age".
     */
    public void unsetAgeValue() throws JNCException {
        delete("age");
    }

    /**
     * This method is used for creating a subtree filter.
     * The added "age" leaf will not have a value.
     */
    public void addAge() throws JNCException {
        setLeafValue(Demo.NAMESPACE,
            "age",
            null,
            childrenNames());
    }

    /**
     * Marks the leaf "age" with operation "replace".
     */
    public void markAgeReplace() throws JNCException {
        markLeafReplace("age");
    }

    /**
     * Marks the leaf "age" with operation "merge".
     */
    public void markAgeMerge() throws JNCException {
        markLeafMerge("age");
    }

    /**
     * Marks the leaf "age" with operation "create".
     */
    public void markAgeCreate() throws JNCException {
        markLeafCreate("age");
    }

    /**
     * Marks the leaf "age" with operation "delete".
     */
    public void markAgeDelete() throws JNCException {
        markLeafDelete("age");
    }

    /* Access methods for optional leaf child: "title". */

    /**
     * Gets the value for child leaf "title".
     * @return The value of the leaf.
     */
    public YangString getTitleValue() throws JNCException {
        return (YangString)getValue("title");
    }

    /**
     * Sets the value for child leaf "title",
     * using instance of generated typedef class.
     * @param titleValue The value to set.
     * @param titleValue used during instantiation.
     */
    public void setTitleValue(YangString titleValue) throws JNCException {
        setLeafValue(Demo.NAMESPACE,
            "title",
            titleValue,
            childrenNames());
    }

    /**
     * Sets the value for child leaf "title",
     * using a String value.
     * @param titleValue used during instantiation.
     */
    public void setTitleValue(String titleValue) throws JNCException {
        setTitleValue(new YangString(titleValue));
    }

    /**
     * Unsets the value for child leaf "title".
     */
    public void unsetTitleValue() throws JNCException {
        delete("title");
    }

    /**
     * This method is used for creating a subtree filter.
     * The added "title" leaf will not have a value.
     */
    public void addTitle() throws JNCException {
        setLeafValue(Demo.NAMESPACE,
            "title",
            null,
            childrenNames());
    }

    /**
     * Marks the leaf "title" with operation "replace".
     */
    public void markTitleReplace() throws JNCException {
        markLeafReplace("title");
    }

    /**
     * Marks the leaf "title" with operation "merge".
     */
    public void markTitleMerge() throws JNCException {
        markLeafMerge("title");
    }

    /**
     * Marks the leaf "title" with operation "create".
     */
    public void markTitleCreate() throws JNCException {
        markLeafCreate("title");
    }

    /**
     * Marks the leaf "title" with operation "delete".
     */
    public void markTitleDelete() throws JNCException {
        markLeafDelete("title");
    }

    /* Access methods for optional leaf child: "city". */

    /**
     * Gets the value for child leaf "city".
     * @return The value of the leaf.
     */
    public YangString getCityValue() throws JNCException {
        return (YangString)getValue("city");
    }

    /**
     * Sets the value for child leaf "city",
     * using instance of generated typedef class.
     * @param cityValue The value to set.
     * @param cityValue used during instantiation.
     */
    public void setCityValue(YangString cityValue) throws JNCException {
        setLeafValue(Demo.NAMESPACE,
            "city",
            cityValue,
            childrenNames());
    }

    /**
     * Sets the value for child leaf "city",
     * using a String value.
     * @param cityValue used during instantiation.
     */
    public void setCityValue(String cityValue) throws JNCException {
        setCityValue(new YangString(cityValue));
    }

    /**
     * Unsets the value for child leaf "city".
     */
    public void unsetCityValue() throws JNCException {
        delete("city");
    }

    /**
     * This method is used for creating a subtree filter.
     * The added "city" leaf will not have a value.
     */
    public void addCity() throws JNCException {
        setLeafValue(Demo.NAMESPACE,
            "city",
            null,
            childrenNames());
    }

    /**
     * Marks the leaf "city" with operation "replace".
     */
    public void markCityReplace() throws JNCException {
        markLeafReplace("city");
    }

    /**
     * Marks the leaf "city" with operation "merge".
     */
    public void markCityMerge() throws JNCException {
        markLeafMerge("city");
    }

    /**
     * Marks the leaf "city" with operation "create".
     */
    public void markCityCreate() throws JNCException {
        markLeafCreate("city");
    }

    /**
     * Marks the leaf "city" with operation "delete".
     */
    public void markCityDelete() throws JNCException {
        markLeafDelete("city");
    }

    /**
     * Support method for addChild.
     * Adds a child to this object.
     * 
     * @param child The child to add
     */
    public void addChild(Element child) {
        super.addChild(child);
    }

}
