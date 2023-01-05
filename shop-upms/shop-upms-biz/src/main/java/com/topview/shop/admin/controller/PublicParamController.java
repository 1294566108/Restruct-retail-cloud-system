package com.topview.shop.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topview.shop.admin.api.entity.SysPublicParam;
import com.topview.shop.admin.service.SysPublicParamService;
import com.topview.shop.common.core.util.CommonResult;
import com.topview.shop.common.log.annotation.SysLog;
import com.topview.shop.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 公共参数
 *
 * @author admin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/param")
@Tag(name = "公共参数配置")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class PublicParamController {

	private final SysPublicParamService sysPublicParamService;

	/**
	 * 通过key查询公共参数值
	 * @param publicKey
	 * @return
	 */
	@Inner(value = false)
	@Operation(summary = "查询公共参数值", description = "根据key查询公共参数值")
	@GetMapping("/publicValue/{publicKey}")
	public CommonResult publicKey(@PathVariable("publicKey") String publicKey) {
		return CommonResult.ok(sysPublicParamService.getSysPublicParamKeyToValue(publicKey));
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param sysPublicParam 公共参数
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	public CommonResult getSysPublicParamPage(Page page, SysPublicParam sysPublicParam) {
		return CommonResult.ok(sysPublicParamService.page(page,
				Wrappers.<SysPublicParam>lambdaQuery()
						.like(StrUtil.isNotBlank(sysPublicParam.getPublicName()), SysPublicParam::getPublicName,
								sysPublicParam.getPublicName())
						.like(StrUtil.isNotBlank(sysPublicParam.getPublicKey()), SysPublicParam::getPublicKey,
								sysPublicParam.getPublicKey())));
	}

	/**
	 * 通过id查询公共参数
	 * @param publicId id
	 * @return R
	 */
	@Operation(summary = "通过id查询公共参数", description = "通过id查询公共参数")
	@GetMapping("/{publicId}")
	public CommonResult getById(@PathVariable("publicId") Long publicId) {
		return CommonResult.ok(sysPublicParamService.getById(publicId));
	}

	/**
	 * 新增公共参数
	 * @param sysPublicParam 公共参数
	 * @return R
	 */
	@Operation(summary = "新增公共参数", description = "新增公共参数")
	@SysLog("新增公共参数")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_publicparam_add')")
	public CommonResult save(@RequestBody SysPublicParam sysPublicParam) {
		return CommonResult.ok(sysPublicParamService.save(sysPublicParam));
	}

	/**
	 * 修改公共参数
	 * @param sysPublicParam 公共参数
	 * @return R
	 */
	@Operation(summary = "修改公共参数", description = "修改公共参数")
	@SysLog("修改公共参数")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_publicparam_edit')")
	public CommonResult updateById(@RequestBody SysPublicParam sysPublicParam) {
		return sysPublicParamService.updateParam(sysPublicParam);
	}

	/**
	 * 通过id删除公共参数
	 * @param publicId id
	 * @return R
	 */
	@Operation(summary = "删除公共参数", description = "删除公共参数")
	@SysLog("删除公共参数")
	@DeleteMapping("/{publicId}")
	@PreAuthorize("@pms.hasPermission('sys_publicparam_del')")
	public CommonResult removeById(@PathVariable Long publicId) {
		return sysPublicParamService.removeParam(publicId);
	}

	/**
	 * 同步参数
	 * @return R
	 */
	@SysLog("同步参数")
	@PutMapping("/sync")
	public CommonResult sync() {
		return sysPublicParamService.syncParamCache();
	}

}
