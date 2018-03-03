package wsuv.autolab;

import java.lang.annotation.*;

/**
 * Secret --
 *
 * The Secret annotation indicates that the test(s) should be hidden from the user.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Secret {
}