package com.topview.shop.common.log.aspect;

import cn.hutool.core.util.StrUtil;
import com.topview.shop.admin.api.entity.SysLog;
import com.topview.shop.common.core.util.SpringContextHolder;
import com.topview.shop.common.log.event.SysLogEvent;
import com.topview.shop.common.log.util.LogTypeEnum;
import com.topview.shop.common.log.util.SysLogUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;

/**
 * 操作日志使用spring event异步入库
 *
 * @author admin
 */
@Aspect
@Slf4j
public class SysLogAspect {

	@Around("@annotation(sysLog)")
	@SneakyThrows
	public Object around(ProceedingJoinPoint point, com.topview.shop.common.log.annotation.SysLog sysLog) {
		String strClassName = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

		String value = sysLog.value();
		String expression = sysLog.expression();
		// 当前表达式存在 SPEL，会覆盖 value 的值
		if (StrUtil.isNotBlank(expression)) {
			// 解析SPEL
			MethodSignature signature = (MethodSignature) point.getSignature();
			EvaluationContext context = SysLogUtils.getContext(point.getArgs(), signature.getMethod());
			try {
				value = SysLogUtils.getValue(context, expression, String.class);
			}
			catch (Exception e) {
				// SPEL 表达式异常，获取 value 的值
				log.error("@SysLog 解析SPEL {} 异常", expression);
			}
		}

		SysLog logVo = SysLogUtils.getSysLog();
		logVo.setTitle(value);

		// 发送异步日志事件
		Long startTime = System.currentTimeMillis();
		Object obj;

		try {
			obj = point.proceed();
		}
		catch (Exception e) {
			logVo.setType(LogTypeEnum.ERROR.getType());
			logVo.setException(e.getMessage());
			throw e;
		}
		finally {
			Long endTime = System.currentTimeMillis();
			logVo.setTime(endTime - startTime);
			SpringContextHolder.publishEvent(new SysLogEvent(logVo));
		}

		return obj;
	}

}
