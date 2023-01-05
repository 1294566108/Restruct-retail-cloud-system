package com.topview.shop.site.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 日常上报关联的文件
 * @author
 */
public class ReportingFile {

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 上报信息id
	 */
	private Long reportId;

	/**
	 * 附件
	 */
	private int attachment;
}
