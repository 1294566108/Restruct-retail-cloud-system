package com.topview.shop.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.topview.shop.admin.api.entity.SysDictItem;

/**
 * 字典项
 *
 * @author admin
 */
public interface SysDictItemService extends IService<SysDictItem> {

	/**
	 * 删除字典项
	 * @param id 字典项ID
	 */
	void removeDictItem(Long id);

	/**
	 * 更新字典项
	 * @param item 字典项
	 */
	void updateDictItem(SysDictItem item);

}
