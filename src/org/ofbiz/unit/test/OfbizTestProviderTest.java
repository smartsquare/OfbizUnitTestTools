package org.ofbiz.unit.test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.base.util.cache.UtilCache;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.unit.entity.TestGenericValue;
import org.ofbiz.unit.provider.OfbizTestProvider;
import org.ofbiz.unit.test.ressources.TestServices;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ UtilCache.class })
public class OfbizTestProviderTest {

    private OfbizTestProvider provider;
    private TestGenericValue fakeGenericValue;

    @Mock
    private Delegator delegator;

    @Mock
    private LocalDispatcher dispatcher;

    @Before
    public void setup() {
        PowerMockito.mockStatic(UtilCache.class);
        when(UtilCache.createUtilCache(anyString(), anyInt(), anyLong(), anyBoolean())).thenReturn(null);

        DispatchContext ctxMock = mock(DispatchContext.class);
        provider = new OfbizTestProvider(delegator, ctxMock, dispatcher);
        fakeGenericValue = new TestGenericValue("EntityName");

    }

    @Test
    public void init_dispatcher_run_sync_mock() throws Exception {
        provider.initServiceCalls(TestServices.class, "updateService");
        Map<String, Object> result = dispatcher.runSync("updateService", UtilMisc.toMap("", ""));

        assertTrue(ServiceUtil.isSuccess(result));
        assertTrue(result.containsKey("spy"));
        assertEquals("Y", result.get("spy"));
    }

    @Test(expected = NoSuchMethodException.class)
    public void init_dispatcher_whith_not_existing_method() throws Exception {
        provider.initServiceCalls(TestServices.class, "createService");
    }

    @Test(expected = NoSuchMethodException.class)
    public void init_dispatcher_with_wrong_method_call() throws Exception {
        provider.initServiceCalls(TestServices.class, "updateService");
        dispatcher.runSync("createService", UtilMisc.toMap("", ""));
    }

    @Test
    public void init_delegator_find_one_mock() throws Exception {
        provider.initDelegatorFindOne(fakeGenericValue);

        GenericValue result = delegator.findOne("EntityName", false, UtilMisc.toMap("", ""));

        assertTrue(result instanceof TestGenericValue);
        TestGenericValue resultValue = (TestGenericValue) result;
        assertEquals(fakeGenericValue, resultValue);
    }

    @Test
    public void init_delegator_find_by_and_mock() throws Exception {
        List<TestGenericValue> returnValues = UtilMisc.toList(fakeGenericValue, fakeGenericValue, fakeGenericValue);
        provider.initDelegatorFindByAnd(returnValues);

        List<GenericValue> results = delegator.findByAnd("EntityName", UtilMisc.toMap("", ""), null, false);

        assertTrue(UtilValidate.isNotEmpty(results));
        assertTrue(results.get(0) instanceof TestGenericValue);
        List<TestGenericValue> resultValues = UtilGenerics.checkList(results, TestGenericValue.class);
        assertEquals(returnValues, resultValues);
    }

    @Test
    public void init_delegator_make_value_mock() throws Exception {
        provider.initDelegatorMakeValue(fakeGenericValue);

        GenericValue result = delegator.makeValue("EntityName");

        assertNotNull(result);
        assertTrue(result instanceof TestGenericValue);
        assertEquals(fakeGenericValue, result);

    }

}
