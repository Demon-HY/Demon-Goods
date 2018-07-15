package org.demon.starter.autoconfigure.annotion;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface RequestEnv {
//    String value();
}
