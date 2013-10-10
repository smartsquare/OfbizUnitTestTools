package org.ofbiz.unit.test;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.base.util.cache.UtilCache;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.unit.entity.TestGenericValue;
import org.ofbiz.unit.provider.OfbizDelegatorProvider;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ UtilCache.class, UtilProperties.class })
public class OfbizDelegatorProviderTest {

    private OfbizDelegatorProvider delegatorProvider;
    private TestGenericValue fakeGenericValue;

    @Mock
    private Delegator delegator;

    @Mock
    private LocalDispatcher dispatcher;

    @Before
    public void setup() {
        PowerMockito.mockStatic(UtilCache.class);
        when(UtilCache.createUtilCache(anyString(), anyInt(), anyLong(), anyBoolean())).thenReturn(null);

        fakeGenericValue = new TestGenericValue("EntityName");
        delegatorProvider = new OfbizDelegatorProvider(delegator);
    }


    @Test
    public void when_delegator_find_one_return_value() throws Exception {
        delegatorProvider.whenDelegatorFindOneThenReturn(fakeGenericValue);

        GenericValue result = delegator.findOne("EntityName", false, UtilMisc.toMap("", ""));

        assertTrue(result instanceof TestGenericValue);
        TestGenericValue resultValue = (TestGenericValue) result;
        assertEquals(fakeGenericValue, resultValue);
    }

    @Test
    public void when_delegator_find_by_return_values() throws Exception {
        List<TestGenericValue> returnValues = UtilMisc.toList(fakeGenericValue, fakeGenericValue, fakeGenericValue);
        delegatorProvider.whenDelegatorFindByAndThenReturn(returnValues);

        List<GenericValue> results = delegator.findByAnd("EntityName", UtilMisc.toMap("", ""), null, false);

        assertTrue(UtilValidate.isNotEmpty(results));
        assertTrue(results.get(0) instanceof TestGenericValue);
        List<TestGenericValue> resultValues = UtilGenerics.checkList(results, TestGenericValue.class);
        assertEquals(returnValues, resultValues);
    }

    @Test
    public void when_delegator_make_value_mock() throws Exception {
        delegatorProvider.whenDelegatorMakeValueThenReturn(fakeGenericValue);

        GenericValue result = delegator.makeValue("EntityName");

        assertNotNull(result);
        assertTrue(result instanceof TestGenericValue);
        assertEquals(fakeGenericValue, result);

    }

    @Test
    public void when_util_properties_get_message_return_uilabel_indicator() throws Exception {
        delegatorProvider.initUtilPropertiesGetMessage();

        String message = UtilProperties.getMessage("TestUiLabels", "Label", new Locale("DE"));
        assertEquals("TestUiLabels:Label", message);

        message = UtilProperties.getMessage("TestUiLabels", "Label2", new HashMap<String, Object>(), new Locale("DE"));
        assertEquals("TestUiLabels:Label2", message);
    }

    @Test
    public void when_delegator_find_list_return_list_of_values() throws Exception {
        List<TestGenericValue> returnValues = UtilMisc.toList(fakeGenericValue, fakeGenericValue, fakeGenericValue);
        delegatorProvider.whenDelegatorFindListThenReturn(returnValues);

        List<GenericValue> results = delegator.findList("EntityName", null, null, null, null, false);

        assertTrue(UtilValidate.isNotEmpty(results));
        assertTrue(results.get(0) instanceof TestGenericValue);
        List<TestGenericValue> resultValues = UtilGenerics.checkList(results, TestGenericValue.class);
        assertEquals(returnValues, resultValues);
    }

}
