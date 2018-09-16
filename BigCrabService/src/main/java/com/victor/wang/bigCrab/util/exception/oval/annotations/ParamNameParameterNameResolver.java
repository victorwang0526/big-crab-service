/*
 * Victor Wang. All Rights Reserved.
 */
package com.victor.wang.bigCrab.util.exception.oval.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/**
 * A singleton class that uses introspection, and the ParamName annotation in order to determine the
 * names of parameters for a method or constructor at runtime.
 */
public abstract class ParamNameParameterNameResolver
{
	protected static final WeakHashMap<AccessibleObject, String[]> cache = new WeakHashMap<AccessibleObject, String[]>();

	public static String[] getParameterNames(Constructor<?> constructor)
	{
		String[] names = cache.get(constructor);
		if (names == null)
		{
			names = calculateNames(constructor, constructor.getParameterAnnotations());

		}

		return names;
	}

	public static String[] getParameterNames(Method method)
	{
		String[] names = cache.get(method);
		if (names == null)
		{
			names = calculateNames(method, method.getParameterAnnotations());
		}

		return names;
	}

	private static synchronized String[] calculateNames(AccessibleObject ctorOrMethod, Annotation[][] annos)
	{
		int num = annos.length;
		String[] names = new String[num];
		for (int i = 0; i < num; i++)
		{
			String name = "arg" + i;


			Annotation[] paramAnnos = annos[i];
			if (paramAnnos != null)
			{
				for (Annotation anno : paramAnnos)
				{
					if (!ParamName.class.isAssignableFrom(anno.annotationType()))
					{
						continue;
					}
					name = ((ParamName) anno).value();
					break;
				}
			}
			names[i] = name;
		}

		cache.put(ctorOrMethod, names);
		return names;
	}
}
