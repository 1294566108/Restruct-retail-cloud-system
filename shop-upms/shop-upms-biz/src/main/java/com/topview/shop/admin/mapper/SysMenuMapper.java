package com.topview.shop.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.topview.shop.admin.api.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * 菜单权限表 Mapper 接口
 *
 * @author admin
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	/**
	 * 通过角色编号查询菜单
	 * @param roleId 角色ID
	 * @return
	 */
	Set<SysMenu> listMenusByRoleId(Long roleId);

}
