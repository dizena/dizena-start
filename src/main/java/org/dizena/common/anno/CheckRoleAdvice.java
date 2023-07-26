package org.dizena.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckRoleAdvice {
    String[] roles() default {""};

    String type() default AND;

    String AND = "and";

    String OR = "or";
}
