package com.topview.shop.admin.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author admin 日志查询传输对象
 */
@Data
public class SysLogDTO {

	/**
	 * 查询日志类型
	 */
	private String type;

	/**
	 * 创建时间区间 [开始时间，结束时间]
	 */
	private LocalDateTime[] createTime;

	/**
	 * 请求IP
	 */
	private String remoteAddr;

}
