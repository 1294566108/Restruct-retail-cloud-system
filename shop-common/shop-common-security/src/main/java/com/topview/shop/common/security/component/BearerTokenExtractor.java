package com.topview.shop.common.security.component;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author admin
 */
public class BearerTokenExtractor implements BearerTokenResolver {

	private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$",
			Pattern.CASE_INSENSITIVE);

	private boolean allowFormEncodedBodyParameter = false;

	private boolean allowUriQueryParameter = true;

	private String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

	private final PathMatcher pathMatcher = new AntPathMatcher();

	private final PermitAllUrlProperties urlProperties;

	public BearerTokenExtractor(PermitAllUrlProperties urlProperties) {
		this.urlProperties = urlProperties;
	}

	@Override
	public String resolve(HttpServletRequest request) {
		boolean match = urlProperties.getUrls().stream()
				.anyMatch(url -> pathMatcher.match(url, request.getRequestURI()));
		// 白名单直接放行
		if (match) {
			return null;
		}
		final String authorizationHeaderToken = resolveFromAuthorizationHeader(request);
		final String parameterToken = isParameterTokenSupportedForRequest(request)
				? resolveFromRequestParameters(request) : null;
		// 解析header中的token或者参数中的access_token字段皆不为空
		if (authorizationHeaderToken != null) {
			if (parameterToken != null) {
				final BearerTokenError error = BearerTokenErrors
						.invalidRequest("Found multiple bearer tokens in the request");
				throw new OAuth2AuthenticationException(error);
			}
			return authorizationHeaderToken;
		}
		if (parameterToken != null && isParameterTokenEnabledForRequest(request)) {
			return parameterToken;
		}
		return null;
	}

	/**
	 * 解析token
	 */
	private String resolveFromAuthorizationHeader(HttpServletRequest request) {
		String authorization = request.getHeader(this.bearerTokenHeaderName);
		if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
			return null;
		}
		Matcher matcher = authorizationPattern.matcher(authorization);
		if (!matcher.matches()) {
			BearerTokenError error = BearerTokenErrors.invalidToken("令牌格式不正确");
			throw new OAuth2AuthenticationException(error);
		}
		return matcher.group("token");
	}

	/**
	 * request参数解析access_token
	 */
	private static String resolveFromRequestParameters(HttpServletRequest request) {
		String[] values = request.getParameterValues("access_token");
		if (values == null || values.length == 0) {
			return null;
		}
		if (values.length == 1) {
			return values[0];
		}
		BearerTokenError error = BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request");
		throw new OAuth2AuthenticationException(error);
	}

	/**
	 * post请求+application/x-www-form-urlencoded 或者 get请求
	 */
	private boolean isParameterTokenSupportedForRequest(final HttpServletRequest request) {
		return (("POST".equals(request.getMethod())
				&& MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
				|| "GET".equals(request.getMethod()));
	}

	/**
	 * post请求+application/x-www-form-urlencoded+allowFormEncodedBodyParameter==true 或者
	 * get请求+allowUriQueryParameter==true
	 */
	private boolean isParameterTokenEnabledForRequest(final HttpServletRequest request) {
		return ((this.allowFormEncodedBodyParameter && "POST".equals(request.getMethod())
				&& MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
				|| (this.allowUriQueryParameter && "GET".equals(request.getMethod())));
	}

}
