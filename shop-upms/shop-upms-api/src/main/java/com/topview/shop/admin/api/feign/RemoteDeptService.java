package com.topview.shop.admin.api.feign;

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
@FeignClient(contextId = "remoteDeptService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteDeptService {

	/**
	 * 查收子级id列表
	 * @return 返回子级id列表
	 */
	@GetMapping(value = "/dept/child-id/{deptId}", headers = SecurityConstants.HEADER_FROM_IN)
	CommonResult<List<Long>> listChildDeptId(@PathVariable("deptId") Long deptId);

}
