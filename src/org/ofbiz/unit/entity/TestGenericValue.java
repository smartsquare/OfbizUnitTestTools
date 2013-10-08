package org.ofbiz.unit.entity;

import java.util.HashMap;
import java.util.Map;

import org.ofbiz.entity.GenericEntity;
import org.ofbiz.entity.GenericValue;

/**
 * This class extends the {@link GenericValue} class and overrides their methods for a better unit test support.
 * The class also provides some methods to help testing.
 *
 * @author sascharodekamp
 *
 */
@SuppressWarnings("serial")
public class TestGenericValue extends GenericValue {

    private final String entityName;
    private Boolean cacheUsed;

    private Map<String, Object> fieldMap = new HashMap<String, Object>();
    private Map<String, ? extends Object> argumentMap = new HashMap<String, Object>();

    public TestGenericValue(String entityName) {
        this.entityName = entityName;
    }

    /**
     * Add a field with a value and returns the GenericValue object.
     * @param fieldName
     * @param value
     * @return
     */
    public TestGenericValue addFieldAndValue(String fieldName, Object value) {
        this.set(fieldName, value);
        return this;
    }

    @Override
    public void set(String name, Object value) {
        this.fieldMap.put(name, value);
    }

    @Override
    public Object put(String name, Object value) {
        return this.fieldMap.put(name, value);
    }

    /**
     * Set a boolean flag which indicates that the cache is used for the last request which returns this {@link GenericValue}
     * @param useCache
     * @return
     */
    public TestGenericValue useCache(Boolean useCache) {
        this.cacheUsed = useCache;
        return this;
    }

    @Override
    public String getString(String fieldName) {
        Object fieldValue = getValueForField(fieldName);
        return (String) fieldValue;
    }

    @Override
    public String getEntityName() {
        return this.entityName;
    }

    public boolean hasField(String fieldName) {
        return fieldMap.containsKey(fieldName);
    }

    public Object getValueForField(String fieldName) {
        return fieldMap.get(fieldName);
    }

    /**
     * Set a map which contains this request arguments.
     * @param argumentMap
     */
    public TestGenericValue setArguments(Map<String, ? extends Object> argumentMap) {
        this.argumentMap = argumentMap;
        return this;
    }

    /**
     * Returns the argument list or an empty map
     * @return
     */
    public Object getArguments() {
        return this.argumentMap;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((argumentMap == null) ? 0 : argumentMap.hashCode());
        result = prime * result + ((cacheUsed == null) ? 0 : cacheUsed.hashCode());
        result = prime * result + ((entityName == null) ? 0 : entityName.hashCode());
        result = prime * result + ((fieldMap == null) ? 0 : fieldMap.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestGenericValue other = (TestGenericValue) obj;
        if (argumentMap == null) {
            if (other.argumentMap != null)
                return false;
        } else if (!argumentMap.equals(other.argumentMap))
            return false;
        if (cacheUsed == null) {
            if (other.cacheUsed != null)
                return false;
        } else if (!cacheUsed.equals(other.cacheUsed))
            return false;
        if (entityName == null) {
            if (other.entityName != null)
                return false;
        } else if (!entityName.equals(other.entityName))
            return false;
        if (fieldMap == null) {
            if (other.fieldMap != null)
                return false;
        } else if (!fieldMap.equals(other.fieldMap))
            return false;
        return true;
    }


    @Override
    public int compareTo(GenericEntity that) {
        if (that == null)
            return -1;

        int tempResult = this.entityName.compareTo(that.getEntityName());

        // if they did not match, we know the order, otherwise compare the primary keys
        if (tempResult != 0)
            return tempResult;

        if (fieldMap.entrySet().equals(that.entrySet())) {
            return 0;
        }

        return -1;
    }

    @Override
    public Boolean getBoolean(String name) {
        Object obj = fieldMap.get(name);

        if (obj == null) {
            return null;
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        } else if (obj instanceof String) {
            String value = (String) obj;

            if ("Y".equalsIgnoreCase(value) || "T".equalsIgnoreCase(value)) {
                return Boolean.TRUE;
            } else if ("N".equalsIgnoreCase(value) || "F".equalsIgnoreCase(value)) {
                return Boolean.FALSE;
            } else {
                throw new IllegalArgumentException("getBoolean could not map the String '" + value + "' to Boolean type");
            }
        } else {
            throw new IllegalArgumentException("getBoolean could not map the object '" + obj.toString() + "' to Boolean type, unknown object type: "
                                               + obj.getClass().getName());
        }
    }

}
