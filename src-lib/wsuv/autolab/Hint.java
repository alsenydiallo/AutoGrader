package wsuv.autolab;

import java.lang.annotation.*;

/**
 * Hint --
 *
 * The Hint annotation specifies a specific message to show in the case of failure.
 *
 * Hints at the Method/Constructor level are shown in preference to Hints at the class/name level.
 * Thus, if a hint exists at both the method and class level, only the method level hint will be
 * shown.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface Hint {
    String hint() default "Try turning it off and on again";
}
