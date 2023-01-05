package com.topview.shop.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topview.shop.admin.api.entity.SysRole;
import com.topview.shop.admin.api.vo.RoleExcelVO;
import com.topview.shop.admin.api.vo.RoleVo;
import com.topview.shop.admin.service.SysRoleMenuService;
import com.topview.shop.admin.service.SysRoleService;
import com.topview.shop.common.core.util.CommonResult;
import com.topview.shop.common.log.annotation.SysLog;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author admin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@Tag(name = "角色管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class RoleController {

	private final SysRoleService sysRoleService;

	private final SysRoleMenuService sysRoleMenuService;

	/**
	 * 通过ID查询角色信息
	 * @param id ID
	 * @return 角色信息
	 */
	@GetMapping("/{id:\\d+}")
	public CommonResult<SysRole> getById(@PathVariable Long id) {
		return CommonResult.ok(sysRoleService.getById(id));
	}

	/**
	 * 添加角色
	 * @param sysRole 角色信息
	 * @return success、false
	 */
	@SysLog("添加角色")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_role_add')")
	public CommonResult<Boolean> save(@Valid @RequestBody SysRole sysRole) {
		return CommonResult.ok(sysRoleService.save(sysRole));
	}

	/**
	 * 修改角色
	 * @param sysRole 角色信息
	 * @return success/false
	 */
	@SysLog("修改角色")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_role_edit')")
	public CommonResult<Boolean> update(@Valid @RequestBody SysRole sysRole) {
		return CommonResult.ok(sysRoleService.updateById(sysRole));
	}

	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@SysLog("删除角色")
	@DeleteMapping("/{id:\\d+}")
	@PreAuthorize("@pms.hasPermission('sys_role_del')")
	public CommonResult<Boolean> removeById(@PathVariable Long id) {
		return CommonResult.ok(sysRoleService.removeRoleById(id));
	}

	/**
	 * 获取角色列表
	 * @return 角色列表
	 */
	@GetMapping("/list")
	public CommonResult<List<SysRole>> listRoles() {
		return CommonResult.ok(sysRoleService.list(Wrappers.emptyWrapper()));
	}

	/**
	 * 分页查询角色信息
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/page")
	public CommonResult<IPage<SysRole>> getRolePage(Page page) {
		return CommonResult.ok(sysRoleService.page(page, Wrappers.emptyWrapper()));
	}

	/**
	 * 更新角色菜单
	 * @param roleVo 角色对象
	 * @return success、false
	 */
	@SysLog("更新角色菜单")
	@PutMapping("/menu")
	@PreAuthorize("@pms.hasPermission('sys_role_perm')")
	public CommonResult<Boolean> saveRoleMenus(@RequestBody RoleVo roleVo) {
		return CommonResult.ok(sysRoleMenuService.saveRoleMenus(roleVo.getRoleId(), roleVo.getMenuIds()));
	}

	/**
	 * 导出excel 表格
	 * @return
	 */
	@ResponseExcel
	@GetMapping("/export")
	@PreAuthorize("@pms.hasPermission('sys_role_import_export')")
	public List<RoleExcelVO> export() {
		return sysRoleService.listRole();
	}

	/**
	 * 导入角色
	 * @param excelVOList 角色列表
	 * @param bindingResult 错误信息列表
	 * @return ok fail
	 */
	@PostMapping("/import")
	@PreAuthorize("@pms.hasPermission('sys_role_import_export')")
	public CommonResult importRole(@RequestExcel List<RoleExcelVO> excelVOList, BindingResult bindingResult) {
		return sysRoleService.importRole(excelVOList, bindingResult);
	}

}
