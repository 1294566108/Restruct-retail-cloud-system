package com.topview.shop.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.topview.shop.admin.api.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户角色表 Mapper 接口
 *
 * @author admin
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

	/**
	 * 根据用户Id删除该用户的角色关系
	 * @param userId 用户ID
	 * @return boolean
	 */
	Boolean deleteByUserId(@Param("userId") Long userId);

}
