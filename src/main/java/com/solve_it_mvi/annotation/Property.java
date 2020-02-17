package com.solve_it_mvi.annotation;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Target({TYPE, ElementType.METHOD, FIELD, PARAMETER,CONSTRUCTOR})
public @interface Property {

    @Nonbinding String value() default "";

    @Nonbinding boolean required() default true;
}
