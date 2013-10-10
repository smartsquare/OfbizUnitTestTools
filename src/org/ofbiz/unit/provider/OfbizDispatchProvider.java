package org.ofbiz.unit.provider;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.entity.Delegator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.when;

/**
 * The class fakes {@link Delegator} and {@link LocalDispatcher} calls.
 *
 * @author sascharodekamp
 *
 */
public class OfbizDispatchProvider {

    private final Delegator delegator;
    private final DispatchContext ctxMock;
    private final LocalDispatcher dispatcher;

    public OfbizDispatchProvider(Delegator delegator, DispatchContext ctxMock, LocalDispatcher dispatcher) {
        this.delegator = delegator;
        this.ctxMock = ctxMock;
        this.dispatcher = dispatcher;

    }

    /**
     * Simulates the {@ink LocalDispatcher} runSync method and class the methods from a service class directly.
     * The service class under test and the methods have to be passed as arguments.
     *
     * @param serviceClass
     * @param methods
     * @throws GenericServiceException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public void initServiceCalls(final Class<? extends Object> serviceClass, String... methods) throws GenericServiceException, NoSuchMethodException,
            SecurityException {
        when(ctxMock.getDelegator()).thenReturn(delegator);

        final Map<String, Method> methodCallMap = new HashMap<String, Method>();
        for (String method : methods) {
            methodCallMap.put(method, serviceClass.getMethod(method, DispatchContext.class, Map.class));
        }

        when(dispatcher.runSync(anyString(), anyMap())).thenAnswer(new Answer<Map<String, Object>>() {

            @Override
            public Map<String, Object> answer(InvocationOnMock invocation) throws Throwable {

                String serviceNameToCall = (String) invocation.getArguments()[0];
                Map<String, Object> context = UtilGenerics.checkMap(invocation.getArguments()[1], String.class, Object.class);

                // TODO get service information from service.xml
                Map<String, Object> result = null;

                if (methodCallMap.containsKey(serviceNameToCall)) {
                    Method methodToCall = methodCallMap.get(serviceNameToCall);
                    result = UtilGenerics.checkMap(methodToCall.invoke(serviceClass.newInstance(), ctxMock, context));
                } else {
                    throw new NoSuchMethodException("Can not call service with name: " + serviceNameToCall);
                }

                return result;
            }

        });
    }
}