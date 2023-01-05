package com.topview.shop.common.log.annotation;

import java.lang.annotation.*;

/**
 * @author admin 操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	/**
	 * 描述
	 * @return {String}
	 */
	String value() default "";

	/**
	 * spel 表达式
	 * @return 日志描述
	 */
	String expression() default "";

}
