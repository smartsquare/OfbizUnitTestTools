package org.ofbiz.unit;

import static org.junit.Assert.assertEquals;

public class Assert {

    private static String RESPOND_ERROR = "error";

    static public void assertEventError(String condition) {
        assertEquals(RESPOND_ERROR, condition);
    }
}
