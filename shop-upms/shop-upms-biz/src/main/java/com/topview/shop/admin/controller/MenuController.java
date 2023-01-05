package com.topview.shop.admin.controller;

import cn.hutool.core.lang.tree.Tree;
import com.topview.shop.admin.api.entity.SysMenu;
import com.topview.shop.admin.service.SysMenuService;
import com.topview.shop.common.core.util.CommonResult;
import com.topview.shop.common.log.annotation.SysLog;
import com.topview.shop.common.security.util.SecurityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author admin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
@Tag(name = "菜单管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MenuController {

	private final SysMenuService sysMenuService;

	/**
	 * 返回当前用户的树形菜单集合
	 * @param parentId 父节点ID
	 * @return 当前用户的树形菜单
	 */
	@GetMapping
	public CommonResult<List<Tree<Long>>> getUserMenu(Long parentId) {
		// 获取符合条件的菜单
		Set<SysMenu> menuSet = SecurityUtils.getRoles().stream().map(sysMenuService::findMenuByRoleId)
				.flatMap(Collection::stream).collect(Collectors.toSet());
		return CommonResult.ok(sysMenuService.filterMenu(menuSet, parentId));
	}

	/**
	 * 返回树形菜单集合
	 * @param lazy 是否是懒加载
	 * @param parentId 父节点ID
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	public CommonResult<List<Tree<Long>>> getTree(boolean lazy, Long parentId) {
		return CommonResult.ok(sysMenuService.treeMenu(lazy, parentId));
	}

	/**
	 * 返回角色的菜单集合
	 * @param roleId 角色ID
	 * @return 属性集合
	 */
	@GetMapping("/tree/{roleId}")
	public CommonResult<List<Long>> getRoleTree(@PathVariable Long roleId) {
		return CommonResult.ok(
				sysMenuService.findMenuByRoleId(roleId).stream().map(SysMenu::getMenuId).collect(Collectors.toList()));
	}

	/**
	 * 通过ID查询菜单的详细信息
	 * @param id 菜单ID
	 * @return 菜单详细信息
	 */
	@GetMapping("/{id:\\d+}")
	public CommonResult<SysMenu> getById(@PathVariable Long id) {
		return CommonResult.ok(sysMenuService.getById(id));
	}

	/**
	 * 新增菜单
	 * @param sysMenu 菜单信息
	 * @return 含ID 菜单信息
	 */
	@SysLog("新增菜单")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_menu_add')")
	public CommonResult<SysMenu> save(@Valid @RequestBody SysMenu sysMenu) {
		sysMenuService.save(sysMenu);
		return CommonResult.ok(sysMenu);
	}

	/**
	 * 删除菜单
	 * @param id 菜单ID
	 * @return success/false
	 */
	@SysLog("删除菜单")
	@DeleteMapping("/{id:\\d+}")
	@PreAuthorize("@pms.hasPermission('sys_menu_del')")
	public CommonResult<Boolean> removeById(@PathVariable Long id) {
		return CommonResult.ok(sysMenuService.removeMenuById(id));
	}

	/**
	 * 更新菜单
	 * @param sysMenu
	 */
	@SysLog("更新菜单")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_menu_edit')")
	public CommonResult<Boolean> update(@Valid @RequestBody SysMenu sysMenu) {
		return CommonResult.ok(sysMenuService.updateMenuById(sysMenu));
	}

	/**
	 * 清除菜单缓存
	 */
	@SysLog("清除菜单缓存")
	@DeleteMapping("/cache")
	@PreAuthorize("@pms.hasPermission('sys_menu_del')")
	public CommonResult clearMenuCache() {
		sysMenuService.clearMenuCache();
		return CommonResult.ok();
	}

}
