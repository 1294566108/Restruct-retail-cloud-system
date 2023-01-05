package com.topview.shop.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.topview.shop.admin.api.entity.SysPublicParam;
import com.topview.shop.common.core.util.CommonResult;

/**
 * 公共参数配置
 *
 * @author Lucky
 * @date 2019-04-29
 */
public interface SysPublicParamService extends IService<SysPublicParam> {

	/**
	 * 通过key查询公共参数指定值
	 * @param publicKey
	 * @return
	 */
	String getSysPublicParamKeyToValue(String publicKey);

	/**
	 * 更新参数
	 * @param sysPublicParam
	 * @return
	 */
	CommonResult updateParam(SysPublicParam sysPublicParam);

	/**
	 * 删除参数
	 * @param publicId
	 * @return
	 */
	CommonResult removeParam(Long publicId);

	/**
	 * 同步缓存
	 * @return R
	 */
	CommonResult syncParamCache();

}
