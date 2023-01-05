package com.topview.shop.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topview.shop.admin.api.entity.SysPost;
import com.topview.shop.admin.api.vo.PostExcelVO;
import com.topview.shop.admin.service.SysPostService;
import com.topview.shop.common.core.util.CommonResult;
import com.topview.shop.common.log.annotation.SysLog;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author admin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Tag(name = "岗位管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class PostController {

	private final SysPostService sysPostService;

	/**
	 * 获取岗位列表
	 * @return 岗位列表
	 */
	@GetMapping("/list")
	public CommonResult<List<SysPost>> listPosts() {
		return CommonResult.ok(sysPostService.list(Wrappers.emptyWrapper()));
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('sys_post_get')")
	public CommonResult getSysPostPage(Page page) {
		return CommonResult
				.ok(sysPostService.page(page, Wrappers.<SysPost>lambdaQuery().orderByAsc(SysPost::getPostSort)));
	}

	/**
	 * 通过id查询岗位信息表
	 * @param postId id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{postId}")
	@PreAuthorize("@pms.hasPermission('sys_post_get')")
	public CommonResult getById(@PathVariable("postId") Long postId) {
		return CommonResult.ok(sysPostService.getById(postId));
	}

	/**
	 * 新增岗位信息表
	 * @param sysPost 岗位信息表
	 * @return R
	 */
	@Operation(summary = "新增岗位信息表", description = "新增岗位信息表")
	@SysLog("新增岗位信息表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_post_add')")
	public CommonResult save(@RequestBody SysPost sysPost) {
		return CommonResult.ok(sysPostService.save(sysPost));
	}

	/**
	 * 修改岗位信息表
	 * @param sysPost 岗位信息表
	 * @return R
	 */
	@Operation(summary = "修改岗位信息表", description = "修改岗位信息表")
	@SysLog("修改岗位信息表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_post_edit')")
	public CommonResult updateById(@RequestBody SysPost sysPost) {
		return CommonResult.ok(sysPostService.updateById(sysPost));
	}

	/**
	 * 通过id删除岗位信息表
	 * @param postId id
	 * @return R
	 */
	@Operation(summary = "通过id删除岗位信息表", description = "通过id删除岗位信息表")
	@SysLog("通过id删除岗位信息表")
	@DeleteMapping("/{postId}")
	@PreAuthorize("@pms.hasPermission('sys_post_del')")
	public CommonResult removeById(@PathVariable Long postId) {
		return CommonResult.ok(sysPostService.removeById(postId));
	}

	/**
	 * 导出excel 表格
	 * @return
	 */
	@ResponseExcel
	@GetMapping("/export")
	@PreAuthorize("@pms.hasPermission('sys_post_import_export')")
	public List<PostExcelVO> export() {
		return sysPostService.listPost();
	}

	/**
	 * 导入岗位
	 * @param excelVOList 岗位列表
	 * @param bindingResult 错误信息列表
	 * @return ok fail
	 */
	@PostMapping("/import")
	@PreAuthorize("@pms.hasPermission('sys_post_import_export')")
	public CommonResult importRole(@RequestExcel List<PostExcelVO> excelVOList, BindingResult bindingResult) {
		return sysPostService.importPost(excelVOList, bindingResult);
	}

}
