package com.topview.shop.common.security.service;

import com.topview.shop.admin.api.dto.UserInfo;
import com.topview.shop.admin.api.feign.RemoteUserService;
import com.topview.shop.common.core.constant.CacheConstants;
import com.topview.shop.common.core.util.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author admin
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final RemoteUserService remoteUserService;

	private final CacheManager cacheManager;

	/**
	 * 用户名密码登录
	 * @param username 用户名
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(username) != null) {
			return (AuthUser) cache.get(username).get();
		}
		CommonResult<UserInfo> result = remoteUserService.info(username);
		UserDetails userDetails = getUserDetails(result);
		if (cache != null) {
			cache.put(username, userDetails);
		}
		return userDetails;
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
