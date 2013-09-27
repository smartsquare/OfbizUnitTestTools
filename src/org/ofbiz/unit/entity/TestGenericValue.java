package org.ofbiz.unit.entity;

import java.util.HashMap;
import java.util.Map;

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
        if (!(fieldValue instanceof String)) {
            throw new IllegalStateException("The requested value is not a String -> " + fieldValue.getClass().getName());
        }
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

}
