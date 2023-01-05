package com.topview.shop.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.topview.shop.admin.api.entity.SysRole;
import com.topview.shop.admin.api.vo.RoleExcelVO;
import com.topview.shop.common.core.util.CommonResult;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * 服务类
 *
 * @author admin
 */
public interface SysRoleService extends IService<SysRole> {

	/**
	 * 通过角色ID，删除角色
	 * @param id
	 * @return
	 */
	Boolean removeRoleById(Long id);

	/**
	 * 导入角色
	 * @param excelVOList 角色列表
	 * @param bindingResult 错误信息列表
	 * @return ok fail
	 */
	CommonResult importRole(List<RoleExcelVO> excelVOList, BindingResult bindingResult);

	/**
	 * 查询全部的角色
	 * @return list
	 */
	List<RoleExcelVO> listRole();

}
