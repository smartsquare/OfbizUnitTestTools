package org.ofbiz.unit.test;

import java.util.Map;

import org.junit.ComparisonFailure;
import org.junit.Test;
import org.ofbiz.service.ServiceUtil;

import static org.junit.Assert.fail;

import static org.ofbiz.unit.Assert.assertEventError;
import static org.ofbiz.unit.Assert.assertEventSuccess;
import static org.ofbiz.unit.Assert.assertServiceError;
import static org.ofbiz.unit.Assert.assertServiceSuccess;

public class AssertTest {

    @Test
    public void test_that_the_event_return_is_an_error() {
        String eventReturn = "error";
        assertEventError(eventReturn);
    }

    @Test
    public void test_that_the_event_return_is_not_an_error() {
        String eventReturn = "foo";
        try {
            assertEventError(eventReturn);
        }
        catch (ComparisonFailure e) {
            return;
        }

        fail();
    }

    @Test
    public void test_that_an_event_return_is_a_success() throws Exception {
        String eventReturn = "success";
        assertEventSuccess(eventReturn);
    }

    @Test
    public void test_that_an_event_return_is_not_a_success() {
        String eventReturn = "foo";
        try {
            assertEventSuccess(eventReturn);
        }
        catch (ComparisonFailure e) {
            return;
        }

        fail();
    }

    @Test
    public void test_that_a_service_return_is_an_error() throws Exception {
        Map<String, Object> serviceReturn = ServiceUtil.returnError("");
        assertServiceError(serviceReturn);
    }

    @Test
    public void test_that_a_service_return_is_not_an_error() {
        Map<String, Object> serviceReturn = ServiceUtil.returnSuccess();
        try {
            assertServiceError(serviceReturn);
        }
        catch (AssertionError e) {
            return;
        }

        fail();
    }

    @Test
    public void test_that_a_service_return_is_a_success() throws Exception {
        Map<String, Object> serviceReturn = ServiceUtil.returnSuccess();
        assertServiceSuccess(serviceReturn);
    }

    @Test
    public void test_that_a_service_return_is_not_a_success() {
        Map<String, Object> serviceReturn = ServiceUtil.returnFailure();
        try {
            assertServiceSuccess(serviceReturn);
        }
        catch (AssertionError e) {
            return;
        }

        fail();
    }

}
