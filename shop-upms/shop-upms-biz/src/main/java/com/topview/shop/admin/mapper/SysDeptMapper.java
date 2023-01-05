package com.topview.shop.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.topview.shop.admin.api.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 部门管理 Mapper 接口
 *
 * @author admin
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

	/**
	 * 关联dept——relation
	 * @return 数据列表
	 */
	List<SysDept> listDepts();

}
