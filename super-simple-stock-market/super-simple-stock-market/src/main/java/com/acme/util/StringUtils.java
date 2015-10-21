package com.acme.util;

/**
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class StringUtils {
	public static boolean isValid(String value) {
		return value != null && value.trim().length() > 0;
	}
}
