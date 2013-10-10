package org.ofbiz.unit.test;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.cache.UtilCache;
import org.ofbiz.entity.Delegator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.unit.provider.OfbizDispatchProvider;
import org.ofbiz.unit.test.ressources.TestServices;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ UtilCache.class, UtilProperties.class })
public class OfbizDispatchProviderTest {

    private OfbizDispatchProvider provider;

    @Mock
    private Delegator delegator;

    @Mock
    private LocalDispatcher dispatcher;

    @Before
    public void setup() {
        PowerMockito.mockStatic(UtilCache.class);
        when(UtilCache.createUtilCache(anyString(), anyInt(), anyLong(), anyBoolean())).thenReturn(null);

        DispatchContext ctxMock = mock(DispatchContext.class);
        provider = new OfbizDispatchProvider(delegator, ctxMock, dispatcher);

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

}
