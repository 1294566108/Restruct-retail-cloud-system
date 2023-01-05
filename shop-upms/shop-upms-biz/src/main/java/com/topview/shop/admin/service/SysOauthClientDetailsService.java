package com.topview.shop.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.topview.shop.admin.api.entity.SysOauthClientDetails;

/**
 * 服务类
 *
 * @author admin
 */
public interface SysOauthClientDetailsService extends IService<SysOauthClientDetails> {

	/**
	 * 通过ID删除客户端
	 * @param id id标识
	 */
	Boolean removeClientDetailsById(String id);

	/**
	 * 修改客户端信息
	 * @param sysOauthClientDetails 客户端信息
	 */
	Boolean updateClientDetailsById(SysOauthClientDetails sysOauthClientDetails);

	/**
	 * 清除客户端缓存
	 */
	void clearClientCache();

}
