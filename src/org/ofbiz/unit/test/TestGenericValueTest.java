package org.ofbiz.unit.test;

import java.util.Map;

import org.junit.Test;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.unit.entity.TestGenericValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestGenericValueTest {

    @Test
    public void create_fake_generic_value_with_enetity_name() {
        GenericValue fakeObject = new TestGenericValue("EntityName");
        assertEquals("EntityName", fakeObject.getEntityName());
    }

    @Test
    public void add_and_get_field_and_value_to_generic_value() throws Exception {
        TestGenericValue fakeObject = new TestGenericValue("EntityName");
        fakeObject.addFieldAndValue("field1", "value").addFieldAndValue("field2", "");

        assertTrue(fakeObject.containsKey("field1"));
        assertTrue(fakeObject.containsKey("field2"));

        assertEquals("value", fakeObject.getString("field1"));
        assertEquals("", fakeObject.getString("field2"));
    }

    @Test
    public void set_and_get_field_and_value_to_generic_value() throws Exception {
        GenericValue fakeObject = new TestGenericValue("EntityName");
        fakeObject.set("field1", "value");

        assertTrue(fakeObject.containsKey("field1"));

        assertEquals("value", fakeObject.getString("field1"));
    }

    @Test
    public void put_and_get_field_and_value_to_generic_value() throws Exception {
        GenericValue fakeObject = new TestGenericValue("EntityName");
        fakeObject.put("field1", "value");

        assertTrue(fakeObject.containsKey("field1"));

        assertEquals("value", fakeObject.getString("field1"));
    }

    @Test
    public void set_and_get_arguments_for_request() throws Exception {
        TestGenericValue fakeObject = new TestGenericValue("EntityName");
        Map<String, ? extends Object> argumentMap = UtilMisc.toMap("id", "12345");
        fakeObject.setArguments(argumentMap);

        assertEquals(argumentMap, fakeObject.getArguments());
    }

    @Test
    public void set_and_get_boolean_parameter_test_true_result() throws Exception {
        GenericValue fakeObject = new TestGenericValue("EntityName");
        fakeObject.set("isActive", "Y");
        assertTrue(fakeObject.getBoolean("isActive"));

        fakeObject.set("isActive", "T");
        assertTrue(fakeObject.getBoolean("isActive"));

        fakeObject.set("isActive", true);
        assertTrue(fakeObject.getBoolean("isActive"));

    }

    @Test
    public void set_and_get_boolean_parameter_test_false_result() throws Exception {
        GenericValue fakeObject = new TestGenericValue("EntityName");
        fakeObject.set("isActive", "N");
        assertFalse(fakeObject.getBoolean("isActive"));

        fakeObject.set("isActive", "F");
        assertFalse(fakeObject.getBoolean("isActive"));

        fakeObject.set("isActive", false);
        assertFalse(fakeObject.getBoolean("isActive"));
    }

    @Test
    public void set_and_get_boolean_parameter_test_null_result() throws Exception {
        GenericValue fakeObject = new TestGenericValue("EntityName");
        fakeObject.set("isActive", null);
        assertNull(fakeObject.getBoolean("isActive"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void set_and_get_boolean_parameter_test_exception_when_object_not_parsable() throws Exception {
        GenericValue fakeObject = new TestGenericValue("EntityName");
        fakeObject.set("isActive", new Object());
        fakeObject.getBoolean("isActive");
    }

    @Test(expected = IllegalArgumentException.class)
    public void set_and_get_boolean_parameter_test_exception_when_string_not_parsable() throws Exception {
        GenericValue fakeObject = new TestGenericValue("EntityName");
        fakeObject.set("isActive", "FOO");
        fakeObject.getBoolean("isActive");
    }

    @Test
    public void test_is_empty_when_no_fields_set() throws Exception {
        GenericValue emptyFakeObject = new TestGenericValue("EntityName");
        assertTrue(emptyFakeObject.isEmpty());
    }

    @Test
    public void test_is_not_empty_when_fields_set() throws Exception {
        GenericValue notEmptyFakeObject = new TestGenericValue("EntityName");
        notEmptyFakeObject.set("field1", "value");
        assertFalse(notEmptyFakeObject.isEmpty());
    }

    @Test
    public void test_contain_method() throws Exception {
        GenericValue fakeObject = new TestGenericValue("EntityName");
        fakeObject.set("field1", "value");

        assertTrue(fakeObject.containsKey("field1"));
        assertFalse(fakeObject.containsKey("field2"));
    }

}
