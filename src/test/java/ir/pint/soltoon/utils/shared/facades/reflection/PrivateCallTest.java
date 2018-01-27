package ir.pint.soltoon.utils.shared.facades.reflection;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrivateCallTest {
    static class Parent {

    }

    static class Child extends Parent {

    }

    static class WithPrivateMethod {
        private boolean isTrue(Integer a) {
            return false;
        }

        private boolean isTrue(Parent p, Child... c) {
            return false;
        }
    }

    @Test
    public void call() throws Exception {
        WithPrivateMethod withPrivateMethod = new WithPrivateMethod();
        Boolean isTrue = PrivateCall.<Boolean>call(withPrivateMethod, "isTrue", 5);
        Boolean isTrue2 = PrivateCall.<Boolean>call(withPrivateMethod, "isTrue", new Child());
        Assert.assertEquals(false, isTrue);
        Assert.assertEquals(false, isTrue2);
    }

}