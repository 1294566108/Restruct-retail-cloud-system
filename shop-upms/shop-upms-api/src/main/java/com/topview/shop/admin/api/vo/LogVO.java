package com.topview.shop.admin.api.vo;

import com.topview.shop.admin.api.entity.SysLog;
import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 */
@Data
public class LogVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private SysLog sysLog;

	private String username;

}
