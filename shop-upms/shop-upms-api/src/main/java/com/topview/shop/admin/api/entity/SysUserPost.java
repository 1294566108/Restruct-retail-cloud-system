package com.topview.shop.admin.api.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户岗位表
 *
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserPost extends Model<SysUserPost> {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 岗位ID
	 */
	private Long postId;

}
