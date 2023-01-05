package com.topview.shop.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.topview.shop.admin.api.entity.SysDict;

/**
 * 字典表
 *
 * @author admin
 */
public interface SysDictService extends IService<SysDict> {

	/**
	 * 根据ID 删除字典
	 * @param id
	 * @return
	 */
	void removeDict(Long id);

	/**
	 * 更新字典
	 * @param sysDict 字典
	 * @return
	 */
	void updateDict(SysDict sysDict);

	/**
	 * 清除缓存
	 */
	void clearDictCache();

}
