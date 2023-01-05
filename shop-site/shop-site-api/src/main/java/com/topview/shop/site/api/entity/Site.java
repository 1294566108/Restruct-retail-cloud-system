package com.topview.shop.site.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.topview.shop.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 服务站点
 * @author Akai
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Site extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	@TableId(value = "site_id", type = IdType.ASSIGN_ID)
	private Long siteId;
	/**
	 * 站点名
	 */
	private String siteName;
	/**
	 * 经度
	 */
	private BigDecimal longitude;
	/**
	 * 纬度
	 */
	private BigDecimal latitude;
	/**
	 * 是否正在运营
	 */
	private int isRun;
	/**
	 * 地址
	 */
	private String location;
	/**
	 * 站点创建人
	 */
	private int creator;
	/**
	 * 站点主要营业人或主管
	 */
	private int inCharge;
	/**
	 * 介绍
	 */
	private String introduce;
	/**
	 * 图标
	 */
	private int icon;
	/**
	 * 联系方式
	 */
	private String phone;
	/**
	 * 是否被删除
	 */
	@TableLogic
	private String delFlag;

}
