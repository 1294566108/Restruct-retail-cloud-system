package com.topview.shop.common.security.service;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;

import com.topview.shop.common.core.constant.CacheConstants;
import com.topview.shop.common.core.constant.SecurityConstants;
import com.topview.shop.common.core.util.RetOps;
import com.topview.shop.admin.api.entity.SysOauthClientDetails;
import com.topview.shop.admin.api.feign.RemoteClientDetailsService;
import com.topview.shop.common.security.util.OAuthClientException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

/**
 * 查询客户端相关信息实现
 *
 * @author admin
 */
@RequiredArgsConstructor
public class RemoteRegisteredClientRepository implements RegisteredClientRepository {

	/**
	 * 刷新令牌有效期默认 30 天 对应数据库字段：RefreshTokenValidity
	 */
	private final static int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30;

	/**
	 * 请求令牌有效期默认 12 小时 对应数据库字段：AccessTokenValidity
	 */
	private final static int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 12;

	private final RemoteClientDetailsService clientDetailsService;

	/**
	 * 保存已注册的客户端 注意:敏感信息应在执行过程外部编码 例如:{@link RegisteredClient#getClientSecret()}
	 * @param registeredClient the {@link RegisteredClient}
	 */
	@Override
	public void save(RegisteredClient registeredClient) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 返回由提供的id标识的注册客户端
	 * @param id the registration identifier
	 * @return the {@link RegisteredClient} if found, otherwise {@code null}
	 */
	@Override
	public RegisteredClient findById(String id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 返回由提供clientId标识的注册客户端信息
	 * @param clientId the client identifier
	 * @return the {@link RegisteredClient} if found, otherwise {@code null}
	 * 重写原生方法支持redis缓存 spEL表达式
	 */
	@Override
	@SneakyThrows
	@Cacheable(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
	public RegisteredClient findByClientId(String clientId) {
		SysOauthClientDetails clientDetails = RetOps.of(clientDetailsService.getClientDetailsById(clientId)).getData()
				.orElseThrow(() -> new OAuthClientException("客户端查询异常，请检查数据库链接"));
		RegisteredClient.Builder builder = RegisteredClient.withId(clientDetails.getClientId())
				.clientId(clientDetails.getClientId())
				.clientSecret(SecurityConstants.NOOP + clientDetails.getClientSecret())
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
		// 授权模式
		Optional.ofNullable(clientDetails.getAuthorizedGrantTypes())
				.ifPresent(grants -> StringUtils.commaDelimitedListToSet(grants)
						.forEach(s -> builder.authorizationGrantType(new AuthorizationGrantType(s))));
		// 回调地址
		Optional.ofNullable(clientDetails.getWebServerRedirectUri()).ifPresent(redirectUri -> Arrays
				.stream(redirectUri.split(StrUtil.COMMA)).filter(StrUtil::isNotBlank).forEach(builder::redirectUri));
		// scope
		Optional.ofNullable(clientDetails.getScope()).ifPresent(
				scope -> Arrays.stream(scope.split(StrUtil.COMMA)).filter(StrUtil::isNotBlank).forEach(builder::scope));
		return builder.tokenSettings(TokenSettings.builder().accessTokenFormat(OAuth2TokenFormat.REFERENCE)
				.accessTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(clientDetails.getAccessTokenValidity())
						.orElse(ACCESS_TOKEN_VALIDITY_SECONDS)))
				.refreshTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(clientDetails.getRefreshTokenValidity())
						.orElse(REFRESH_TOKEN_VALIDITY_SECONDS)))
				.build())
				.clientSettings(ClientSettings.builder()
						.requireAuthorizationConsent(!BooleanUtil.toBoolean(clientDetails.getAutoapprove())).build())
				.build();
	}

}
