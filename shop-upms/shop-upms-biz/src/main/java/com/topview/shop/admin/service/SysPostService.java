package com.topview.shop.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.topview.shop.admin.api.entity.SysPost;
import com.topview.shop.admin.api.vo.PostExcelVO;
import com.topview.shop.common.core.util.CommonResult;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * 岗位管理 服务类
 *
 * @author admin
 */
public interface SysPostService extends IService<SysPost> {

	/**
	 * 导入岗位
	 * @param excelVOList 岗位列表
	 * @param bindingResult 错误信息列表
	 * @return ok fail
	 */
	CommonResult importPost(List<PostExcelVO> excelVOList, BindingResult bindingResult);

	/**
	 * 导出excel 表格
	 * @return
	 */
	List<PostExcelVO> listPost();

}
