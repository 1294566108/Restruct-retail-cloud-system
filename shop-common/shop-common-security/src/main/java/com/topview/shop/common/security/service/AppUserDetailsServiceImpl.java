package com.topview.shop.common.security.service;

import com.topview.shop.common.core.constant.CacheConstants;
import com.topview.shop.common.core.constant.SecurityConstants;
import com.topview.shop.common.core.util.CommonResult;
import com.topview.shop.admin.api.dto.UserInfo;
import com.topview.shop.admin.api.feign.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author admin
 */
@Slf4j
@RequiredArgsConstructor
public class AppUserDetailsServiceImpl implements UserDetailsService {

	private final RemoteUserService remoteUserService;

	private final CacheManager cacheManager;

	/**
	 * 手机号登录
	 * @param phone 手机号
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String phone) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(phone) != null) {
			return (AuthUser) cache.get(phone).get();
		}

		CommonResult<UserInfo> result = remoteUserService.infoByMobile(phone);

		UserDetails userDetails = getUserDetails(result);
		if (cache != null) {
			cache.put(phone, userDetails);
		}
		return userDetails;
	}

	/**
	 * check-token 使用
	 * @param authUser user
	 */
	@Override
	public UserDetails loadUserByUser(AuthUser authUser) {
		return this.loadUserByUsername(authUser.getPhone());
	}

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		return SecurityConstants.APP.equals(grantType);
	}

}
