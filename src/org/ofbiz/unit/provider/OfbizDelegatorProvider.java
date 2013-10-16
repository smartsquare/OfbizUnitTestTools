package org.ofbiz.unit.provider;

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
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.unit.entity.TestGenericValue;
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.when;

/**
 * The class fakes {@link Delegator} and {@link LocalDispatcher} calls.
 *
 * @author sascharodekamp
 *
 */
public class OfbizDelegatorProvider {

    private final Delegator delegator;

    public OfbizDelegatorProvider(Delegator delegator) {
        this.delegator = delegator;

    }

    /**
     * Fakes the {@link Delegator} findOne method and returns the {@link TestGenericValue} object.
     *
     * @param fakeGenericValue
     * @throws GenericEntityException
     */
    public void whenDelegatorFindOneThenReturn(final TestGenericValue fakeGenericValue) throws GenericEntityException {
        when(delegator.findOne(anyString(), anyBoolean(), anyMap())).then(new Answer<GenericValue>() {

            @Override
            public GenericValue answer(InvocationOnMock invocation) throws Throwable {
                Boolean useCache = (Boolean) invocation.getArguments()[1];
                Map<String, Object> arguments = UtilGenerics.checkMap(invocation.getArguments()[2]);

                fakeGenericValue.useCache(useCache).setArguments(arguments);

                return fakeGenericValue;
            }

        });
    }

    /**
     * Fakes the {@link Delegator} findByAnd method and returns the passed list of {@link TestGenericValue} objects.
     * @param returnValues
     * @throws GenericEntityException
     */
    public void whenDelegatorFindByAndThenReturn(final List<TestGenericValue> returnValues) throws GenericEntityException {
        when(delegator.findByAnd(anyString(), anyMap(), anyList(), anyBoolean())).then(new Answer<List<TestGenericValue>>() {

            @Override
            public List<TestGenericValue> answer(InvocationOnMock invocation) throws Throwable {
                Boolean useCache = (Boolean) invocation.getArguments()[3];
                Map<String, Object> arguments = UtilGenerics.checkMap(invocation.getArguments()[1]);
                // TODO add the input sort list to the output

                // TODO use an object as return value not a list to store the parameters along the object
                for (TestGenericValue fakeGenericValue : returnValues) {
                    fakeGenericValue.useCache(useCache).setArguments(arguments);
                }

                return returnValues;
            }

        });

    }

    /**
     * Fakes the {@link Delegator} findList method and returns the passed list of {@link TestGenericValue} objects.
     * @param returnValues
     * @throws GenericEntityException
     */
    public void whenDelegatorFindListThenReturn(final List<TestGenericValue> returnValues) throws GenericEntityException {
        when(delegator.findList(anyString(), any(EntityCondition.class), anySet(), anyList(), any(EntityFindOptions.class), anyBoolean())).then(
                new Answer<List<TestGenericValue>>() {

                    @Override
                    public List<TestGenericValue> answer(InvocationOnMock invocation) throws Throwable {
                        Boolean useCache = (Boolean) invocation.getArguments()[5];
                        // TODO add the input sort list to the output

                        // TODO use an object as return value not a list to store the parameters along the object

                        return returnValues;
                    }

                });

    }

    /**
     * Fakes the {@link Delegator} makeValue method and returns the passed {@link TestGenericValue} objects.
     *
     * @param returnValue
     * @throws GenericEntityException
     */
    public void whenDelegatorMakeValueThenReturn(final TestGenericValue fakeGenericValue) {
        when(delegator.makeValue(anyString())).then(new Answer<GenericValue>() {

            @Override
            public GenericValue answer(InvocationOnMock invocation) throws Throwable {
                // String entityName = (String) invocation.getArguments()[0];
                return fakeGenericValue;
            }

        });

    }

    /**
     * Fakes the static {@link UtilProperties}.getMessage method for any uiLabel.
     * When the method is called within your code a String will returned that shows
     * which Label was loaded: {code}UiLabelResourceName:LabelName{code}
     */
    public void initUtilPropertiesGetMessage() {
        PowerMockito.mockStatic(UtilProperties.class);
        when(UtilProperties.getMessage(anyString(), anyString(), (Locale) anyObject())).thenAnswer(requestedMessage());
        when(UtilProperties.getMessage(anyString(), anyString(), anyMap(), (Locale) anyObject())).thenAnswer(requestedMessage());
    }

    private Answer<String> requestedMessage() {
        return new Answer<String>() {

            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                String requestedUiLabels = (String) invocation.getArguments()[0];
                String requestedLabel = (String) invocation.getArguments()[1];

                return requestedUiLabels + ":" + requestedLabel;
            }
        };
    }
}
