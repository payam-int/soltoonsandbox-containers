package ir.pint.soltoon.utils.shared.facades.reflection;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrivateCallTest {
    static class WithPrivateMethod {
        private boolean isTrue(Integer a) {
            return false;
        }
    }

    @Test
    public void call() throws Exception {
        WithPrivateMethod withPrivateMethod = new WithPrivateMethod();
        Boolean isTrue = PrivateCall.<Boolean>call(withPrivateMethod, "isTrue", 5);
        Assert.assertEquals(false, isTrue);
    }

}