package com.topview.shop.admin.controller;

import com.topview.shop.admin.api.dto.UserDTO;
import com.topview.shop.admin.service.SysUserService;
import com.topview.shop.common.core.util.CommonResult;
import com.topview.shop.common.log.annotation.SysLog;
import com.topview.shop.common.security.annotation.Inner;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin 客户端注册功能 register.user = false
 */
@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "register.user", matchIfMissing = true)
public class RegisterController {

	private final SysUserService userService;

	/**
	 * 注册用户
	 * @param userDto 用户信息
	 * @return success/false
	 */
	@Inner(value = false)
	@SysLog("注册用户")
	@PostMapping("/user")
	public CommonResult<Boolean> registerUser(@RequestBody UserDTO userDto) {
		return userService.registerUser(userDto);
	}

}
