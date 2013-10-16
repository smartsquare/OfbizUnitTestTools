package org.ofbiz.unit;

import java.util.Map;

import org.ofbiz.service.ServiceUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Assert {

    private static String RESPOND_ERROR = "error";
    private static String RESPOND_SUCCESS = "success";

    static public void assertEventError(String condition) {
        assertEquals(RESPOND_ERROR, condition);
    }

    static public void assertEventSuccess(String condition) {
        assertEquals(RESPOND_SUCCESS, condition);
    }

    static public void assertServiceError(Map<String, Object> condition) {
        assertTrue(ServiceUtil.isError(condition));
    }

    static public void assertServiceSuccess(Map<String, Object> condition) {
        assertTrue(ServiceUtil.isSuccess(condition));
    }
}
