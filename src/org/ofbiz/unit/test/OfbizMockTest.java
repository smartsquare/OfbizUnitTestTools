package org.ofbiz.unit.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ofbiz.unit.test.ressources.TestStaticMethods;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.ofbiz.unit.OfbizMock.whenStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ TestStaticMethods.class })
public class OfbizMockTest {

    @Test
    public void test_whenStatic_call() {
        whenStatic(TestStaticMethods.class, TestStaticMethods.getABoolean()).thenReturn(true);

        Boolean result = TestStaticMethods.getABoolean();
        assertTrue(result);

        whenStatic(TestStaticMethods.class, TestStaticMethods.getABoolean()).thenReturn(false);

        result = TestStaticMethods.getABoolean();
        assertFalse(result);
    }

}
