package com.topview.shop.gateway.filter;

import com.topview.shop.common.core.constant.CommonConstants;
import com.topview.shop.common.core.constant.SecurityConstants;
import io.lettuce.core.ScriptOutputType;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * @author namelesssss 全局拦截器，作用所有的微服务 1. 对请求头中参数进行处理 from 参数进行清洗 2. 重写StripPrefix = 1,支持全局
 * 3. 支持swagger添加X-Forwarded-Prefix header （F SR2 已经支持，不需要自己维护）
 */
public class RequestGlobalFilter implements GlobalFilter, Ordered {

	/**
	 * 通过给定的{@link GatewayFilterChain}处理 Web请求并（可选）委托给下一个 {@code WebFilter}
	 * @param exchange 当前服务器交换机
	 * @param chain 提供了委派到下一个筛选器的方法
	 * @return {@code Mono<Void>} 指示请求处理何时完成
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 1. 清洗请求头中from 参数
		ServerHttpRequest request = exchange.getRequest().mutate().headers(httpHeaders -> {
			System.out.println("清洗前：" + httpHeaders);
			httpHeaders.remove(SecurityConstants.FROM);
			System.out.println("清洗后：" + httpHeaders);
			// 设置请求时间
			httpHeaders.put(CommonConstants.REQUEST_START_TIME,
					Collections.singletonList(String.valueOf(System.currentTimeMillis())));
		}).build();
		// 2. 重写StripPrefix
		addOriginalRequestUrl(exchange, request.getURI());
		String rawPath = request.getURI().getRawPath();
		System.out.println("raw是" + rawPath);
		String newPath = "/" + Arrays.stream(StringUtils.tokenizeToStringArray(rawPath, "/")).skip(1L)
				.collect(Collectors.joining("/"));
		System.out.println("new是" + newPath);
		ServerHttpRequest newRequest = request.mutate().path(newPath).build();
		System.out.println("uri是" + newRequest.getURI());
		exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());
		return chain.filter(exchange.mutate().request(newRequest.mutate().build()).build());
	}

	@Override
	public int getOrder() {
		return 10;
	}

}
