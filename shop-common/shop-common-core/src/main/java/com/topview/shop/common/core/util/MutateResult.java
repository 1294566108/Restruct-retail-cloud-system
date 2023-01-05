package com.topview.shop.common.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static com.topview.shop.common.core.constant.CommonConstants.*;
import static com.topview.shop.common.core.constant.CommonConstants.OPERATE_FAIL_MESSAGE;
import static com.topview.shop.common.core.util.CommonResult.autoResult;

/**
 * @author Akai
 */
public class MutateResult {

	private static Type buildType(Type... types) {
		ParameterizedTypeImpl beforeType = null;
		if (types != null && types.length > 0) {
			for (int i = types.length - 1; i > 0; i--) {
				beforeType = new ParameterizedTypeImpl(new Type[] { beforeType == null ? types[i] : beforeType }, null,
						types[i - 1]);
			}
		}
		return beforeType;
	}

	public static <T> CommonResult<List<T>> parseListResult(String json, Class<T> clazz) {
		return JSONObject.parseObject(json, buildType(CommonResult.class, List.class, clazz));
	}

	public static <T> CommonResult<Page<T>> parsePageResult(String json, Class<T> clazz) {
		return JSONObject.parseObject(json, buildType(CommonResult.class, Page.class, clazz));
	}

	public static <P> CommonResult<P> covertJsonToCommonResult(String result) {
		CommonResult<P> commonResult = JSON.parseObject(result, CommonResult.class);
		Assert.state(commonResult.isSuccess(), commonResult.getMessage());
		return commonResult;
	}

	public static <P> CommonResult<?> covertJsonToCommonResult(String result, P p) {
		CommonResult<P> commonResult = JSON.parseObject(result, CommonResult.class);
		Assert.state(commonResult.isSuccess(), commonResult.getMessage());
		return commonResult;
	}

	public static <P> CommonResult<P> covertJsonToCommonResult(String result, Class<P> p) {
		CommonResult commonResult = JSON.parseObject(result, CommonResult.class);
		Assert.state(commonResult.isSuccess(), commonResult.getMessage());
		return operateSuccess(JSONObject
				.parseObject(Optional.ofNullable(commonResult.getData()).map(Object::toString).orElse(null), p));
	}

	public static <P> CommonResult<P> covertJsonToAutoResult(String result) {
		CommonResult commonResult = JSON.parseObject(result, CommonResult.class);
		return autoResult(commonResult.isSuccess());
	}

	public static <P> CommonResult<P> operateSuccess(P p) {
		return new CommonResult<P>(SUCCESS_CODE, OPERATE_SUCCESS_MESSAGE, true, p);
	}

	public static <P> CommonResult<P> operateFail(P p) {
		return new CommonResult<P>(FAIL_CODE, OPERATE_FAIL_MESSAGE, false, p);
	}

}
