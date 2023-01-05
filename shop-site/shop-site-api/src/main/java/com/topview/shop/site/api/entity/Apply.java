package com.topview.shop.site.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.topview.shop.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 商铺申请
 * @author Akai
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Apply extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "apply_id", type = IdType.ASSIGN_ID)
	private Long applyId;

	/**
	 * 申请商铺
	 */
	private Long site;

	/**
	 * 申请类型
	 */
	private int applyType;

	/**
	 * 申请原因
	 */
	private String reason;

	/**
	 * 申请时间
	 */
	private LocalDateTime applyTime;

	/**
	 * 申请状态("未处理"、"处理中"、"已批准"、"已拒绝")
	 */
	private int status;

	/**
	 * 处理人
	 */
	private int handler;

	/**
	 * 是否被删除
	 */
	@TableLogic
	private String delFlag;
}
