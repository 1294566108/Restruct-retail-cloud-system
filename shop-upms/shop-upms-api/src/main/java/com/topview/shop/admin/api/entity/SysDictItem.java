package com.topview.shop.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.topview.shop.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典项
 *
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 所属字典类id
	 */
	private Long dictId;

	/**
	 * 所属字典类id
	 */
	private String dictKey;

	/**
	 * 数据值
	 */
	private String value;

	/**
	 * 标签名
	 */
	private String label;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 排序（升序）
	 */
	private Integer sortOrder;

	/**
	 * 备注信息
	 */
	private String remark;

	/**
	 * 删除标记
	 */
	@TableLogic
	private String delFlag;

}
