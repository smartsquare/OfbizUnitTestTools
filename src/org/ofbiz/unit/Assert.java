package org.ofbiz.unit;

import static org.junit.Assert.assertEquals;

public class Assert {

    private static String RESPOND_ERROR = "error";
    private static String RESPOND_SUCCESS = "success";

    static public void assertEventError(String condition) {
        assertEquals(RESPOND_ERROR, condition);
    }

    static public void assertEventSuccess(String condition) {
        assertEquals(RESPOND_SUCCESS, condition);
    }
}
