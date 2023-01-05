package com.topview.shop.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topview.shop.admin.api.entity.SysDict;
import com.topview.shop.admin.api.entity.SysDictItem;
import com.topview.shop.admin.service.SysDictItemService;
import com.topview.shop.admin.service.SysDictService;
import com.topview.shop.common.core.constant.CacheConstants;
import com.topview.shop.common.core.util.CommonResult;
import com.topview.shop.common.log.annotation.SysLog;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 字典表 前端控制器
 *
 * @author admin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dict")
@Tag(name = "字典管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class DictController {

	private final SysDictItemService sysDictItemService;

	private final SysDictService sysDictService;

	/**
	 * 通过ID查询字典信息
	 * @param id ID
	 * @return 字典信息
	 */
	@GetMapping("/{id:\\d+}")
	public CommonResult<SysDict> getById(@PathVariable Long id) {
		return CommonResult.ok(sysDictService.getById(id));
	}

	/**
	 * 分页查询字典信息
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/page")
	public CommonResult<IPage<SysDict>> getDictPage(Page page, SysDict sysDict) {
		return CommonResult.ok(sysDictService.page(page, Wrappers.<SysDict>lambdaQuery()
				.like(StrUtil.isNotBlank(sysDict.getDictKey()), SysDict::getDictKey, sysDict.getDictKey())));
	}

	/**
	 * 通过字典类型查找字典
	 * @param type 类型
	 * @return 同类型字典
	 */
	@GetMapping("/key/{key}")
	@Cacheable(value = CacheConstants.DICT_DETAILS, key = "#key")
	public CommonResult<List<SysDictItem>> getDictByKey(@PathVariable String key) {
		return CommonResult
				.ok(sysDictItemService.list(Wrappers.<SysDictItem>query().lambda().eq(SysDictItem::getDictKey, key)));
	}

	/**
	 * 添加字典
	 * @param sysDict 字典信息
	 * @return success、false
	 */
	@SysLog("添加字典")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_dict_add')")
	public CommonResult<Boolean> save(@Valid @RequestBody SysDict sysDict) {
		return CommonResult.ok(sysDictService.save(sysDict));
	}

	/**
	 * 删除字典，并且清除字典缓存
	 * @param id ID
	 * @return R
	 */
	@SysLog("删除字典")
	@DeleteMapping("/{id:\\d+}")
	@PreAuthorize("@pms.hasPermission('sys_dict_del')")
	public CommonResult removeById(@PathVariable Long id) {
		sysDictService.removeDict(id);
		return CommonResult.ok();
	}

	/**
	 * 修改字典
	 * @param sysDict 字典信息
	 * @return success/false
	 */
	@PutMapping
	@SysLog("修改字典")
	@PreAuthorize("@pms.hasPermission('sys_dict_edit')")
	public CommonResult updateById(@Valid @RequestBody SysDict sysDict) {
		sysDictService.updateDict(sysDict);
		return CommonResult.ok();
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param sysDictItem 字典项
	 * @return
	 */
	@GetMapping("/item/page")
	public CommonResult<IPage<SysDictItem>> getSysDictItemPage(Page page, SysDictItem sysDictItem) {
		return CommonResult.ok(sysDictItemService.page(page, Wrappers.query(sysDictItem)));
	}

	/**
	 * 通过id查询字典项
	 * @param id id
	 * @return R
	 */
	@GetMapping("/item/{id:\\d+}")
	public CommonResult<SysDictItem> getDictItemById(@PathVariable("id") Long id) {
		return CommonResult.ok(sysDictItemService.getById(id));
	}

	/**
	 * 新增字典项
	 * @param sysDictItem 字典项
	 * @return R
	 */
	@SysLog("新增字典项")
	@PostMapping("/item")
	@CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
	public CommonResult<Boolean> save(@RequestBody SysDictItem sysDictItem) {
		return CommonResult.ok(sysDictItemService.save(sysDictItem));
	}

	/**
	 * 修改字典项
	 * @param sysDictItem 字典项
	 * @return R
	 */
	@SysLog("修改字典项")
	@PutMapping("/item")
	public CommonResult updateById(@RequestBody SysDictItem sysDictItem) {
		sysDictItemService.updateDictItem(sysDictItem);
		return CommonResult.ok();
	}

	/**
	 * 通过id删除字典项
	 * @param id id
	 * @return R
	 */
	@SysLog("删除字典项")
	@DeleteMapping("/item/{id:\\d+}")
	public CommonResult removeDictItemById(@PathVariable Long id) {
		sysDictItemService.removeDictItem(id);
		return CommonResult.ok();
	}

	@SysLog("清除字典缓存")
	@DeleteMapping("/cache")
	@PreAuthorize("@pms.hasPermission('sys_dict_del')")
	public CommonResult clearDictCache() {
		sysDictService.clearDictCache();
		return CommonResult.ok();
	}

}
