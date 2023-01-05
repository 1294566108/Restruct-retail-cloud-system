package com.topview.shop.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.topview.shop.admin.api.dto.SysLogDTO;
import com.topview.shop.admin.api.entity.SysLog;

import java.util.List;

/**
 * 日志表 服务类
 *
 * @author admin
 */
public interface SysLogService extends IService<SysLog> {

	/**
	 * 分页查询日志
	 * @param page
	 * @param sysLog
	 * @return
	 */
	Page<SysLog> getLogByPage(Page page, SysLogDTO sysLog);

	/**
	 * 列表查询日志
	 * @param sysLog 查询条件
	 * @return List
	 */
	List<SysLog> getLogList(SysLogDTO sysLog);

}
