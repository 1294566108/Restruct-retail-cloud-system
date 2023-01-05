package com.topview.shop.admin.api.feign;

import com.topview.shop.admin.api.entity.SysLog;
import com.topview.shop.common.core.constant.SecurityConstants;
import com.topview.shop.common.core.constant.ServiceNameConstants;
import com.topview.shop.common.core.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Akai
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteLogService {

	/**
	 * 保存日志
	 * @param sysLog 日志实体
	 * @return succes、false
	 */
	@PostMapping(value = "/log", headers = SecurityConstants.HEADER_FROM_IN)
	CommonResult<Boolean> saveLog(@RequestBody SysLog sysLog);

}
