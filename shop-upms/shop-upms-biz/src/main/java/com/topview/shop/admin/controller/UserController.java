package com.topview.shop.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topview.shop.admin.api.dto.UserDTO;
import com.topview.shop.admin.api.dto.UserInfo;
import com.topview.shop.admin.api.entity.SysUser;
import com.topview.shop.admin.api.vo.UserExcelVO;
import com.topview.shop.admin.api.vo.UserInfoVO;
import com.topview.shop.admin.api.vo.UserVO;
import com.topview.shop.admin.service.SysUserService;
import com.topview.shop.common.core.exception.ErrorCodes;
import com.topview.shop.common.core.util.MsgUtils;
import com.topview.shop.common.core.util.CommonResult;
import com.topview.shop.common.log.annotation.SysLog;
import com.topview.shop.common.security.annotation.Inner;
import com.topview.shop.common.security.util.SecurityUtils;
import com.topview.shop.common.xss.core.XssCleanIgnore;
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
import java.util.Set;

/**
 * @author admin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "用户管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class UserController {

	private final SysUserService userService;

	/**
	 * 获取当前用户全部信息
	 * @return 用户信息
	 */
	@GetMapping(value = { "/info" })
	public CommonResult<UserInfoVO> info() {
		String username = SecurityUtils.getUser().getUsername();
		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
		if (user == null) {
			return CommonResult.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_QUERY_ERROR));
		}
		UserInfo userInfo = userService.getUserInfo(user);
		UserInfoVO vo = new UserInfoVO();
		vo.setSysUser(userInfo.getSysUser());
		vo.setRoles(userInfo.getRoles());
		vo.setPermissions(userInfo.getPermissions());
		return CommonResult.ok(vo);
	}

	/**
	 * 获取指定用户全部信息
	 * @return 用户信息
	 */
	@Inner
	@GetMapping("/info/{username}")
	public CommonResult<UserInfo> info(@PathVariable String username) {
		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
		if (user == null) {
			return CommonResult.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERINFO_EMPTY, username));
		}
		return CommonResult.ok(userService.getUserInfo(user));
	}

	/**
	 * 根据部门id，查询对应的用户 id 集合
	 * @param deptIds 部门id 集合
	 * @return 用户 id 集合
	 */
	@Inner
	@GetMapping("/ids")
	public CommonResult<List<Long>> listUserIdByDeptIds(@RequestParam("deptIds") Set<Long> deptIds) {
		return CommonResult.ok(userService.listUserIdByDeptIds(deptIds));
	}

	/**
	 * 通过ID查询用户信息
	 * @param id ID
	 * @return 用户信息
	 */
	@GetMapping("/{id:\\d+}")
	public CommonResult<UserVO> user(@PathVariable Long id) {
		return CommonResult.ok(userService.getUserVoById(id));
	}

	/**
	 * 判断用户是否存在
	 * @param userDTO 查询条件
	 * @return
	 */
	@Inner(false)
	@GetMapping("/check/exsit")
	public CommonResult<Boolean> isExsit(UserDTO userDTO) {
		List<SysUser> sysUserList = userService.list(new QueryWrapper<>(userDTO));
		if (CollUtil.isNotEmpty(sysUserList)) {
			return CommonResult.ok(Boolean.TRUE, MsgUtils.getMessage(ErrorCodes.SYS_USER_EXISTING));
		}
		return CommonResult.ok(Boolean.FALSE);
	}

	/**
	 * 删除用户信息
	 * @param id ID
	 * @return R
	 */
	@SysLog("删除用户信息")
	@DeleteMapping("/{id:\\d+}")
	@PreAuthorize("@pms.hasPermission('sys_user_del')")
	public CommonResult<Boolean> userDel(@PathVariable Long id) {
		SysUser sysUser = userService.getById(id);
		return CommonResult.ok(userService.removeUserById(sysUser));
	}

	/**
	 * 添加用户
	 * @param userDto 用户信息
	 * @return success/false
	 */
	@SysLog("添加用户")
	@PostMapping
	@XssCleanIgnore({ "password" })
	@PreAuthorize("@pms.hasPermission('sys_user_add')")
	public CommonResult<Boolean> user(@RequestBody UserDTO userDto) {
		return CommonResult.ok(userService.saveUser(userDto));
	}

	/**
	 * 管理员更新用户信息
	 * @param userDto 用户信息
	 * @return R
	 */
	@SysLog("更新用户信息")
	@PutMapping
	@XssCleanIgnore({ "password" })
	@PreAuthorize("@pms.hasPermission('sys_user_edit')")
	public CommonResult<Boolean> updateUser(@Valid @RequestBody UserDTO userDto) {
		return userService.updateUser(userDto);
	}

	/**
	 * 分页查询用户
	 * @param page 参数集
	 * @param userDTO 查询参数列表
	 * @return 用户集合
	 */
	@GetMapping("/page")
	public CommonResult<IPage<UserVO>> getUserPage(Page page, UserDTO userDTO) {
		return CommonResult.ok(userService.getUserWithRolePage(page, userDTO));
	}

	/**
	 * 个人修改个人信息
	 * @param userDto userDto
	 * @return success/false
	 */
	@SysLog("修改个人信息")
	@PutMapping("/edit")
	@XssCleanIgnore({ "password", "newpassword1" })
	public CommonResult<Boolean> updateUserInfo(@Valid @RequestBody UserDTO userDto) {
		userDto.setUsername(SecurityUtils.getUser().getUsername());
		return userService.updateUserInfo(userDto);
	}

	/**
	 * @param username 用户名称
	 * @return 上级部门用户列表
	 */
	@GetMapping("/ancestor/{username}")
	public CommonResult<List<SysUser>> listAncestorUsers(@PathVariable String username) {
		return CommonResult.ok(userService.listAncestorUsersByUsername(username));
	}

	/**
	 * 导出excel 表格
	 * @param userDTO 查询条件
	 * @return
	 */
	@ResponseExcel
	@GetMapping("/export")
	@PreAuthorize("@pms.hasPermission('sys_user_import_export')")
	public List<UserExcelVO> export(UserDTO userDTO) {
		return userService.listUser(userDTO);
	}

	/**
	 * 导入用户
	 * @param excelVOList 用户列表
	 * @param bindingResult 错误信息列表
	 * @return R
	 */
	@PostMapping("/import")
	@PreAuthorize("@pms.hasPermission('sys_user_import_export')")
	public CommonResult importUser(@RequestExcel List<UserExcelVO> excelVOList, BindingResult bindingResult) {
		return userService.importUser(excelVOList, bindingResult);
	}

}
