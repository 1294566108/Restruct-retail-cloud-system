package com.topview.shop.admin.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author admin
 */
@Data
public class RoleVo {

	/**
	 * 角色id
	 */
	private Long roleId;

	/**
	 * 菜单列表
	 */
	private String menuIds;

}
