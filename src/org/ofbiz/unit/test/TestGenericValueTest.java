package org.ofbiz.unit.test;

import java.util.Map;

import org.junit.Test;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.unit.entity.TestGenericValue;

import static org.junit.Assert.assertEquals;
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

        assertTrue(fakeObject.hasField("field1"));
        assertTrue(fakeObject.hasField("field2"));

        assertEquals("value", fakeObject.getValueForField("field1"));
        assertEquals("", fakeObject.getValueForField("field2"));
    }

    @Test
    public void set_and_get_field_and_value_to_generic_value() throws Exception {
        TestGenericValue fakeObject = new TestGenericValue("EntityName");
        fakeObject.set("field1", "value");

        assertTrue(fakeObject.hasField("field1"));

        assertEquals("value", fakeObject.getValueForField("field1"));
    }

    @Test
    public void get_string_value_from_object() throws Exception {
        TestGenericValue fakeObject = new TestGenericValue("EntityName");
        fakeObject.addFieldAndValue("field1", "value");
        assertEquals("value", fakeObject.getString("field1"));
    }

    @Test(expected = IllegalStateException.class)
    public void get_string_throws_expection_when_not_a_string() {
        TestGenericValue fakeObject = new TestGenericValue("EntityName");
        fakeObject.addFieldAndValue("field1", 42);
        fakeObject.getString("field1");
    }

    @Test
    public void set_and_get_arguments_for_request() throws Exception {
        TestGenericValue fakeObject = new TestGenericValue("EntityName");
        Map<String, ? extends Object> argumentMap = UtilMisc.toMap("id", "12345");
        fakeObject.setArguments(argumentMap);

        assertEquals(argumentMap, fakeObject.getArguments());
    }

}
