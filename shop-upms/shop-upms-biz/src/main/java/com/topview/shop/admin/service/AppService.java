package com.topview.shop.admin.service;

import com.topview.shop.admin.api.dto.AppSmsDTO;
import com.topview.shop.common.core.util.CommonResult;

/**
 * @author admin
 */
public interface AppService {

	/**
	 * 发送手机验证码
	 * @param sms phone
	 * @return code
	 */
	CommonResult<Boolean> sendSmsCode(AppSmsDTO sms);

	/**
	 * 校验验证码
	 * @param phone 手机号
	 * @param code 验证码
	 * @return
	 */
	boolean check(String phone, String code);

}
