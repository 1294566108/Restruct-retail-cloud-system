package com.topview.shop.admin.api.dto;

import com.topview.shop.admin.api.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {

	/**
	 * 角色ID
	 */
	private List<Long> role;

	private Long deptId;

	/**
	 * 岗位ID
	 */
	private List<Long> post;

	/**
	 * 新密码
	 */
	private String newpassword1;

	/**
	 * 验证码
	 */
	private String code;

}
