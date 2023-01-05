package com.topview.shop.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.topview.shop.admin.api.entity.SysOauthClientDetails;
import com.topview.shop.admin.mapper.SysOauthClientDetailsMapper;
import com.topview.shop.admin.service.SysOauthClientDetailsService;
import com.topview.shop.common.core.constant.CacheConstants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author admin
 */
@Service
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetails>
		implements SysOauthClientDetailsService {

	/**
	 * 通过ID删除客户端
	 * @param id 唯一标识id
	 */
	@Override
	@CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#id")
	public Boolean removeClientDetailsById(String id) {
		return this.removeById(id);
	}

	/**
	 * 根据客户端信息
	 * @param clientDetails 客户端信息
	 */
	@Override
	@CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientDetails.clientId")
	public Boolean updateClientDetailsById(SysOauthClientDetails clientDetails) {
		return this.updateById(clientDetails);
	}

	/**
	 * 清除客户端缓存 allEntries = true --> 清除所有的元素
	 */
	@Override
	@CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, allEntries = true)
	public void clearClientCache() {

	}

}
