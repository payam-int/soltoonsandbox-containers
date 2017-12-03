package ir.pint.soltoon.soltoongame.shared.data.map;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by amirkasra on 10/1/2017 AD.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrespondingAttributes {
    GameObjectType value();
}
