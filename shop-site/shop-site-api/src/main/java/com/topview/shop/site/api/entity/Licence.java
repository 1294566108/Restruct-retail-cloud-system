package com.topview.shop.site.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.topview.shop.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 证照
 * @author Akai
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Licence extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "licence_id", type = IdType.ASSIGN_ID)
	private Long licenceId;

	/**
	 * 是否必要(0:不必要，1：必要)
	 */
	private int necessary;

	/**
	 * 是否被删除
	 */
	@TableLogic
	private String delFlag;

}
