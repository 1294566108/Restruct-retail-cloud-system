package com.topview.shop.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.topview.shop.admin.api.dto.UserInfo;
import com.topview.shop.admin.api.entity.SysUser;
import com.topview.shop.common.core.constant.CommonConstants;
import com.topview.shop.common.core.constant.SecurityConstants;
import com.topview.shop.common.core.util.CommonResult;
import com.topview.shop.common.core.util.RetOps;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author admin
 */
public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService, Ordered {

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	default boolean support(String clientId, String grantType) {
		return true;
	}

	/**
	 * 排序值 默认取最大的
	 * @return 排序值
	 */
	@Override
	default int getOrder() {
		return 0;
	}

	/**
	 * 构建userdetails
	 * @param result 用户信息
	 * @return UserDetails
	 */
	default UserDetails getUserDetails(CommonResult<UserInfo> result) {
		UserInfo info = RetOps.of(result).getData().orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
		Set<String> dbAuthsSet = new HashSet<>();
		if (ArrayUtil.isNotEmpty(info.getRoles())) {
			// 获取角色
			Arrays.stream(info.getRoles()).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
			// 获取资源
			dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));
		}
		Collection<GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(dbAuthsSet.toArray(new String[0]));
		SysUser user = info.getSysUser();
		// 构造security用户
		return new AuthUser(user.getUserId(), user.getDeptId(), user.getUsername(),
				SecurityConstants.BCRYPT + user.getPassword(), user.getPhone(), true, true, true,
				StrUtil.equals(user.getLockFlag(), CommonConstants.STATUS_NORMAL), authorities);
	}

	/**
	 * 通过用户实体查询
	 * @param authUser user
	 */
	default UserDetails loadUserByUser(AuthUser authUser) {
		return this.loadUserByUsername(authUser.getUsername());
	}

}
