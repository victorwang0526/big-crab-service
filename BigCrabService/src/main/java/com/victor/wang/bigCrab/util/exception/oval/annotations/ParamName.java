/*
 * Victor Wang. All Rights Reserved.
 */
package com.victor.wang.bigCrab.util.exception.oval.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to provide information about a method parameter name. This
 * is required if you want to access the name of a parameter after the java code
 * has been compiled to byte code without the -g flag as without -g the param names
 * are not included in the byte code.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ParamName
{
	String value();
}

