package com.topview.shop.common.security.component;

import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.topview.shop.common.security.annotation.Inner;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Akai 资源服务器对外直接暴露URL,如果设置contex-path 要特殊处理
 * <p>
 * 获取有Inner注解的类和方法，然后获取我们配置的uri，经过正则替换后面的可变参数为*，然后将此uri加入到ignore-url中。
 * 此时我们就能达到所有Inner配置的方法/类上的接口地址，都统一在项目加载阶段自动帮我们加到ignore-url中，
 * 不需要我们手动配置，免去了很多开发工作，同时也能避免我们忘记配置，而浪费开发时间
 */
@Slf4j
@ConfigurationProperties(prefix = "security.oauth2.ignore")
public class PermitAllUrlProperties implements InitializingBean {

	private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

	private static final String[] DEFAULT_IGNORE_URLS = new String[] { "/actuator/**", "/error", "/v3/api-docs" };

	@Getter
	@Setter
	private List<String> urls = new ArrayList<>();

	@Override
	public void afterPropertiesSet() {
		urls.addAll(Arrays.asList(DEFAULT_IGNORE_URLS));
		RequestMappingHandlerMapping mapping = SpringUtil.getBean("requestMappingHandlerMapping");
		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

		map.keySet().forEach(info -> {
			HandlerMethod handlerMethod = map.get(info);

			// 获取方法上边的注解 替代path variable 为 *
			Inner method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Inner.class);
			Optional.ofNullable(method).ifPresent(inner -> Objects.requireNonNull(info.getPathPatternsCondition())
					.getPatternValues().forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, "*"))));

			// 获取类上边的注解, 替代path variable 为 *
			Inner controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Inner.class);
			Optional.ofNullable(controller).ifPresent(inner -> Objects.requireNonNull(info.getPathPatternsCondition())
					.getPatternValues().forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, "*"))));
		});
	}

}
