package com.topview.shop.admin.controller;

import com.topview.shop.admin.api.feign.RemoteTokenService;
import com.topview.shop.common.core.util.CommonResult;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author admin getTokenPage 管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
@Tag(name = "令牌管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class TokenController {

	private final RemoteTokenService remoteTokenService;

	/**
	 * 分页token 信息
	 * @param params 参数集
	 * @return token集合
	 */
	@GetMapping("/page")
	public CommonResult token(@RequestParam Map<String, Object> params) {
		return remoteTokenService.getTokenPage(params);
	}

	/**
	 * 删除
	 * @param id ID
	 * @return success/false
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_token_del')")
	public CommonResult<Boolean> delete(@PathVariable String id) {
		return remoteTokenService.removeToken(id);
	}

}
