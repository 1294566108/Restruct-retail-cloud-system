package com.topview.shop.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topview.shop.admin.api.dto.SysLogDTO;
import com.topview.shop.admin.api.entity.SysLog;
import com.topview.shop.admin.service.SysLogService;
import com.topview.shop.common.core.util.CommonResult;
import com.topview.shop.common.security.annotation.Inner;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 日志表 前端控制器
 *
 * @author admin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
@Tag(name = "日志管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class LogController {

	private final SysLogService sysLogService;

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param sysLog 系统日志
	 */
	@GetMapping("/page")
	public CommonResult<IPage<SysLog>> getLogPage(Page page, SysLogDTO sysLog) {
		return CommonResult.ok(sysLogService.getLogByPage(page, sysLog));
	}

	/**
	 * 删除日志
	 * @param id ID
	 * @return success/false
	 */
	@DeleteMapping("/{id:\\d+}")
	@PreAuthorize("@pms.hasPermission('sys_log_del')")
	public CommonResult<Boolean> removeById(@PathVariable Long id) {
		return CommonResult.ok(sysLogService.removeById(id));
	}

	/**
	 * 插入日志
	 * @param sysLog 日志实体
	 * @return success/false
	 */
	@Inner
	@PostMapping
	public CommonResult<Boolean> save(@Valid @RequestBody SysLog sysLog) {
		return CommonResult.ok(sysLogService.save(sysLog));
	}

	/**
	 * 导出excel 表格
	 * @param sysLog 查询条件
	 * @return EXCEL
	 */
	@ResponseExcel
	@GetMapping("/export")
	@PreAuthorize("@pms.hasPermission('sys_log_import_export')")
	public List<SysLog> export(SysLogDTO sysLog) {
		return sysLogService.getLogList(sysLog);
	}

}
