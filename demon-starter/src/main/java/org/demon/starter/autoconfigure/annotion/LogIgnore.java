package org.demon.starter.autoconfigure.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略日志切面的注解 {@link org.demon.starter.common.logger.WebLoggerAspect}
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogIgnore {
    /**
     * 忽略的原因
     */
    String value() default "";

}
