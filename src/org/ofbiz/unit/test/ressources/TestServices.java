package org.ofbiz.unit.test.ressources;

import java.util.Map;

import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;


public class TestServices {

    public static Map<String, Object> updateService(DispatchContext ctx, Map<String, Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("spy", "Y");

        return result;
    }
}
