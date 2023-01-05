package com.topview.shop.admin.api.feign;

import com.topview.shop.common.core.constant.SecurityConstants;
import com.topview.shop.common.core.constant.ServiceNameConstants;
import com.topview.shop.common.core.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Akai
 */
@FeignClient(contextId = "remoteTokenService", value = ServiceNameConstants.AUTH_SERVICE)
public interface RemoteTokenService {

	/**
	 * 分页查询token 信息
	 * @param params 分页参数
	 * @return page
	 */
	@PostMapping(value = "/token/page", headers = SecurityConstants.HEADER_FROM_IN)
	CommonResult getTokenPage(@RequestBody Map<String, Object> params);

	/**
	 * 删除token
	 * @param token token
	 */
	@DeleteMapping(value = "/token/{token}", headers = SecurityConstants.HEADER_FROM_IN)
	CommonResult<Boolean> removeToken(@PathVariable("token") String token);

}
