package com.topview.shop.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.topview.shop.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 部门管理
 *
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDept extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId(value = "dept_id", type = IdType.ASSIGN_ID)
	private Long deptId;

	/**
	 * 部门名称
	 */
	@NotBlank(message = "部门名称不能为空")
	private String name;

	/**
	 * 排序
	 */
	@NotNull(message = "部门排序值不能为空")
	private Integer sortOrder;

	/**
	 * 父级部门id
	 */
	private Long parentId;

	/**
	 * 是否删除 -1：已删除 0：正常
	 */
	@TableLogic
	private String delFlag;

}
