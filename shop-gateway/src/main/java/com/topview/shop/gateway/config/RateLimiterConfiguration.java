package com.topview.shop.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author Akai 路由限流配置
 */
@Configuration(proxyBeanMethods = false)
public class RateLimiterConfiguration {

	/**
	 * Remote addr key resolver
	 */
	@Bean
	public KeyResolver remoteAddrKeyResolver() {
		return exchange -> Mono
				.just(Objects.requireNonNull(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()))
						.getAddress().getHostAddress());
	}

}
