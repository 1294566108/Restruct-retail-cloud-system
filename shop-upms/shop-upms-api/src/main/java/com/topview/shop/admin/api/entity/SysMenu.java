package com.topview.shop.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.topview.shop.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 菜单权限表
 *
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@TableId(value = "menu_id", type = IdType.ASSIGN_ID)
	private Long menuId;

	/**
	 * 菜单名称
	 */
	@NotBlank(message = "菜单名称不能为空")
	private String name;

	/**
	 * 菜单权限标识
	 */
	private String permission;

	/**
	 * 父菜单ID
	 */
	@NotNull(message = "菜单父ID不能为空")
	private Long parentId;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 前端URL
	 */
	private String path;

	/**
	 * 排序值
	 */
	private Integer sortOrder;

	/**
	 * 菜单类型 （0菜单 1按钮）
	 */
	@NotNull(message = "菜单类型不能为空")
	private String type;

	/**
	 * 路由缓冲
	 */
	private String keepAlive;

	/**
	 * 0--正常 1--删除
	 */
	@TableLogic
	private String delFlag;

}
