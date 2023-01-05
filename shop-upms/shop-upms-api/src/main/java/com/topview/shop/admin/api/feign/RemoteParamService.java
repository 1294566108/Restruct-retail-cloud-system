package com.topview.shop.admin.api.feign;

import com.topview.shop.common.core.constant.SecurityConstants;
import com.topview.shop.common.core.constant.ServiceNameConstants;
import com.topview.shop.common.core.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Akai 查询参数相关
 */
@FeignClient(contextId = "remoteParamService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteParamService {

	/**
	 * 通过key 查询参数配置
	 * @param key key
	 */
	@GetMapping(value = "/param/publicValue/{key}", headers = SecurityConstants.HEADER_FROM_IN)
	CommonResult<String> getByKey(@PathVariable("key") String key);

}
