package com.topview.shop.common.log.event;

import com.topview.shop.admin.api.entity.SysLog;
import org.springframework.context.ApplicationEvent;

/**
 * @author admin 系统日志事件
 */
public class SysLogEvent extends ApplicationEvent {

	public SysLogEvent(SysLog source) {
		super(source);
	}

}
