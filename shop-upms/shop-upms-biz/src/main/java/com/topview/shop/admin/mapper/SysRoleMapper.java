package com.topview.shop.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.topview.shop.admin.api.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author admin
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

	/**
	 * 通过用户ID，查询角色信息
	 * @param userId 用户id
	 */
	List<SysRole> listRolesByUserId(Long userId);

}
