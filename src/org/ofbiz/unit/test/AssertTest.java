package org.ofbiz.unit.test;

import org.junit.ComparisonFailure;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.ofbiz.unit.Assert.assertEventError;

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

}
