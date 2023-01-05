package com.topview.shop.site.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.topview.shop.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商铺类型
 * @author Akai
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SiteType extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "type_id", type = IdType.ASSIGN_ID)
	private Long typeId;

	/**
	 * 类型名
	 */
	private String typeName;

	/**
	 * 综合类(0:单独类型 1:综合类型)
	 */
	private int integrated;

	/**
	 * 是否被删除
	 */
	@TableLogic
	private String delFlag;
}
