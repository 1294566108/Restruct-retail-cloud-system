package com.topview.shop.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.topview.shop.admin.api.entity.SysDict;
import com.topview.shop.admin.api.entity.SysDictItem;
import com.topview.shop.admin.mapper.SysDictItemMapper;
import com.topview.shop.admin.service.SysDictItemService;
import com.topview.shop.admin.service.SysDictService;
import com.topview.shop.common.core.constant.CacheConstants;
import com.topview.shop.common.core.constant.enums.DictTypeEnum;
import com.topview.shop.common.core.exception.ErrorCodes;
import com.topview.shop.common.core.util.MsgUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 字典项
 *
 * @author admin
 */
@Service
@RequiredArgsConstructor
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {

	private final SysDictService dictService;

	/**
	 * 删除字典项
	 * @param id 字典项ID
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
	public void removeDictItem(Long id) {
		// 根据ID查询字典ID
		SysDictItem dictItem = this.getById(id);
		SysDict dict = dictService.getById(dictItem.getDictId());
		// 系统内置
		Assert.state(!DictTypeEnum.SYSTEM.getType().equals(dict.getSystemFlag()),
				MsgUtils.getMessage(ErrorCodes.SYS_DICT_DELETE_SYSTEM));
		this.removeById(id);
	}

	/**
	 * 更新字典项
	 * @param item 字典项
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.DICT_DETAILS, key = "#item.type")
	public void updateDictItem(SysDictItem item) {
		// 查询字典
		SysDict dict = dictService.getById(item.getDictId());
		// 系统内置
		Assert.state(!DictTypeEnum.SYSTEM.getType().equals(dict.getSystemFlag()),
				MsgUtils.getMessage(ErrorCodes.SYS_DICT_UPDATE_SYSTEM));
		this.updateById(item);
	}

}
