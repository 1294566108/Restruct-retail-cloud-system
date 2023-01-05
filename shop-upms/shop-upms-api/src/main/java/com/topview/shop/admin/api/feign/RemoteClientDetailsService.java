package com.topview.shop.admin.api.feign;

import com.topview.shop.admin.api.entity.SysOauthClientDetails;
import com.topview.shop.common.core.constant.SecurityConstants;
import com.topview.shop.common.core.constant.ServiceNameConstants;
import com.topview.shop.common.core.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Akai
 */
@FeignClient(contextId = "remoteClientDetailsService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteClientDetailsService {

	/**
	 * 通过clientId 查询客户端信息
	 * @param clientId 用户名
	 * @return R
	 */
	@GetMapping(value = "/client/getClientDetailsById/{clientId}", headers = SecurityConstants.HEADER_FROM_IN)
	CommonResult<SysOauthClientDetails> getClientDetailsById(@PathVariable("clientId") String clientId);

	/**
	 * 查询全部客户端
	 * @return R
	 */
	@GetMapping(value = "/client/list", headers = SecurityConstants.HEADER_FROM_IN)
	CommonResult<List<SysOauthClientDetails>> listClientDetails();

}
