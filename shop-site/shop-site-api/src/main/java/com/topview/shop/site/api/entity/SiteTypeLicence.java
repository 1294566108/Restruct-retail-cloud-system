package com.topview.shop.site.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.topview.shop.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Akai
 * 一个商铺类型对应需要的证照
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SiteTypeLicence extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 证照id
	 */
	private Long licenceId;

	/**
	 * 站点类型id
	 */
	private Long siteTypeId;

	/**
	 * 是否被删除
	 */
	@TableLogic
	private String delFlag;
}
