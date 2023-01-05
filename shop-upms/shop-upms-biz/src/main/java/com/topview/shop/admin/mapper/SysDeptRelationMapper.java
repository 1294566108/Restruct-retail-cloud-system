package com.topview.shop.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.topview.shop.admin.api.entity.SysDeptRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper 接口
 *
 * @author admin
 */
@Mapper
public interface SysDeptRelationMapper extends BaseMapper<SysDeptRelation> {

	/**
	 * 删除部门节点关系
	 * @param deptRelation 待删除的某一个部门节点
	 */
	void deleteDeptRelations(SysDeptRelation deptRelation);

	/**
	 * 删除部门节点关系,同时删除所有关联此部门子节点的部门关系
	 * @param id 待删除的部门节点ID
	 */
	void deleteDeptRelationsById(Long id);

	/**
	 * 新增部门节点关系
	 * @param deptRelation 待新增的部门节点关系
	 */
	void insertDeptRelations(SysDeptRelation deptRelation);

}
