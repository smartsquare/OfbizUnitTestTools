package org.ofbiz.unit;

import org.mockito.internal.MockitoCore;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;

public class OfbizMock {

    private static final MockitoCore MOCKITO_CORE = new MockitoCore();

    public static <T> OngoingStubbing<T> whenStatic(Class<? extends Object> clazz, T methodCall) {
        PowerMockito.mockStatic(clazz);
        return MOCKITO_CORE.when(methodCall);
    }
}
