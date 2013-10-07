package org.ofbiz.unit.provider;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.unit.entity.TestGenericValue;
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.when;

/**
 * The class fakes {@link Delegator} and {@link LocalDispatcher} calls.
 *
 * @author sascharodekamp
 *
 */
public class OfbizTestProvider {

    private final Delegator delegator;
    private final DispatchContext ctxMock;
    private final LocalDispatcher dispatcher;

    public OfbizTestProvider(Delegator delegator, DispatchContext ctxMock, LocalDispatcher dispatcher) {
        this.delegator = delegator;
        this.ctxMock = ctxMock;
        this.dispatcher = dispatcher;

    }

    /**
     * Fakes the {@link Delegator} findOne method and returns the {@link TestGenericValue} object.
     *
     * @param fakeGenericValue
     * @throws GenericEntityException
     */
    public void initDelegatorFindOne(final TestGenericValue fakeGenericValue) throws GenericEntityException {
        when(delegator.findOne(anyString(), anyBoolean(), anyMap())).then(new Answer<GenericValue>() {

            @Override
            public GenericValue answer(InvocationOnMock invocation) throws Throwable {
                Boolean useCache = (Boolean) invocation.getArguments()[1];
                Map<String, Object> arguments = (Map<String, Object>) invocation.getArguments()[2];

                fakeGenericValue.useCache(useCache).setArguments(arguments);

                return fakeGenericValue;
            }

        });
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
    public void initServiceCalls(final Class serviceClass, String... methods) throws GenericServiceException, NoSuchMethodException, SecurityException {
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
                    result = (Map<String, Object>) methodToCall.invoke(serviceClass.newInstance(), ctxMock, context);
                } else {
                    throw new NoSuchMethodException("Can not call service with name: " + serviceNameToCall);
                }

                return result;
            }

        });
    }

    /**
     * Fakes the {@link Delegator} findByAnd method and returns the passed list of {@link TestGenericValue} objects.
     * @param returnValues
     * @throws GenericEntityException
     */
    public void initDelegatorFindByAnd(final List<TestGenericValue> returnValues) throws GenericEntityException {
        when(delegator.findByAnd(anyString(), anyMap(), anyList(), anyBoolean())).then(new Answer<List<TestGenericValue>>() {

            @Override
            public List<TestGenericValue> answer(InvocationOnMock invocation) throws Throwable {
                Boolean useCache = (Boolean) invocation.getArguments()[3];
                Map<String, Object> arguments = (Map<String, Object>) invocation.getArguments()[1];
                // TODO add the input sort list to the output

                // TODO use an object as return value not a list to store the parameters along the object
                for (TestGenericValue fakeGenericValue : returnValues) {
                    fakeGenericValue.useCache(useCache).setArguments(arguments);
                }

                return returnValues;
            }

        });

    }

    public void initDelegatorMakeValue(final TestGenericValue fakeGenericValue) {
        when(delegator.makeValue(anyString())).then(new Answer<GenericValue>() {

            @Override
            public GenericValue answer(InvocationOnMock invocation) throws Throwable {
                String entityName = (String) invocation.getArguments()[0];

                return fakeGenericValue;
            }

        });

    }

    /**
     * Fakes the static {@link UtilProperties}.getMessage method for any uiLabel.
     * When the method is called within your code a String will returned that shows
     * which Label was loaded: {code}UiLabelName:LabelName{code}
     */
    public void initUtilPropertiesGetMessage() {
        PowerMockito.mockStatic(UtilProperties.class);
        when(UtilProperties.getMessage(anyString(), anyString(), (Locale) anyObject())).thenAnswer(new Answer<String>() {

            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                String requestedUiLabels = (String) invocation.getArguments()[0];
                String requestedLabel = (String) invocation.getArguments()[1];

                return requestedUiLabels + ":" + requestedLabel;
            }
        });
    }
}
