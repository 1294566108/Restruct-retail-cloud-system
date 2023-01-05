package com.topview.shop.admin.api.dto;

import com.topview.shop.admin.api.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author admin 角色Dto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends SysRole {

	/**
	 * 角色部门Id
	 */
	private Long roleDeptId;

	/**
	 * 部门名称
	 */
	private String deptName;

}
