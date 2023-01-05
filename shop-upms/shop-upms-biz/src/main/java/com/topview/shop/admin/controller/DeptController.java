package com.topview.shop.admin.controller;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.topview.shop.admin.api.entity.SysDept;
import com.topview.shop.admin.service.SysDeptService;
import com.topview.shop.common.core.util.CommonResult;
import com.topview.shop.common.log.annotation.SysLog;
import com.topview.shop.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 部门管理 前端控制器
 *
 * @author admin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dept")
@Tag(name = "部门管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class DeptController {

	private final SysDeptService sysDeptService;

	/**
	 * 通过ID查询
	 * @param id ID
	 * @return SysDept
	 */
	@GetMapping("/{id:\\d+}")
	public CommonResult<SysDept> getById(@PathVariable Long id) {
		return CommonResult.ok(sysDeptService.getById(id));
	}

	/**
	 * 返回树形菜单集合
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	public CommonResult<List<Tree<Long>>> listDeptTrees() {
		return CommonResult.ok(sysDeptService.listDeptTrees());
	}

	/**
	 * 返回当前用户树形菜单集合
	 * @return 树形菜单
	 */
	@GetMapping(value = "/user-tree")
	public CommonResult<List<Tree<Long>>> listCurrentUserDeptTrees() {
		return CommonResult.ok(sysDeptService.listCurrentUserDeptTrees());
	}

	/**
	 * 添加
	 * @param sysDept 实体
	 * @return success/false
	 */
	@SysLog("添加部门")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_dept_add')")
	public CommonResult<Boolean> save(@Valid @RequestBody SysDept sysDept) {
		return CommonResult.ok(sysDeptService.saveDept(sysDept));
	}

	/**
	 * 删除
	 * @param id ID
	 * @return success/false
	 */
	@SysLog("删除部门")
	@DeleteMapping("/{id:\\d+}")
	@PreAuthorize("@pms.hasPermission('sys_dept_del')")
	public CommonResult<Boolean> removeById(@PathVariable Long id) {
		return CommonResult.ok(sysDeptService.removeDeptById(id));
	}

	/**
	 * 编辑
	 * @param sysDept 实体
	 * @return success/false
	 */
	@SysLog("编辑部门")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_dept_edit')")
	public CommonResult<Boolean> update(@Valid @RequestBody SysDept sysDept) {
		return CommonResult.ok(sysDeptService.updateDeptById(sysDept));
	}

	/**
	 * 根据部门名查询部门信息
	 * @param deptname 部门名
	 */
	@GetMapping("/details/{deptname}")
	public CommonResult<SysDept> user(@PathVariable String deptname) {
		SysDept condition = new SysDept();
		condition.setName(deptname);
		return CommonResult.ok(sysDeptService.getOne(new QueryWrapper<>(condition)));
	}

	/**
	 * 查收子级id列表
	 * @return 返回子级id列表
	 */
	@Inner
	@GetMapping(value = "/child-id/{deptId:\\d+}")
	public CommonResult<List<Long>> listChildDeptId(@PathVariable Long deptId) {
		return CommonResult.ok(sysDeptService.listChildDeptId(deptId));
	}

}
