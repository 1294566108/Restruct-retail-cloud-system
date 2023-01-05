package com.topview.shop.site.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.topview.shop.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日常上报
 * @author Akai
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Reporting extends BaseEntity {
	/**
	 * 主键ID
	 */
	@TableId(value = "report_id", type = IdType.ASSIGN_ID)
	private Long reportId;

	/**
	 * 上报商铺
	 */
	private Long site;

	/**
	 * 上报类型(缴费凭证、员工信息、工资结算凭证、社保缴费凭证、商铺信息)
	 */
	private int type;

	/**
	 * 附件
	 */
	private int attachment;
}
