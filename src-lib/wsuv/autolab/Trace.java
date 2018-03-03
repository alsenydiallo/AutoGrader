package wsuv.autolab;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Trace --
 *
 *   The Trace annotation indicates that only the messages from the failed asserts
 *   should be displayed...  I'm not sure if this is actually what we want.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR})
public @interface Trace {
}
