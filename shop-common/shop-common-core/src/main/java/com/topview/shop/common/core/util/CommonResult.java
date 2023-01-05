package com.topview.shop.common.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.topview.shop.common.core.constant.CommonConstants;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static com.topview.shop.common.core.constant.CommonConstants.*;

/**
 * 响应信息主体
 *
 * @author admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code;

	private String message;

	private boolean isSuccess;

	private T data;

	public CommonResult(int code, String message, boolean isSuccess) {
		this.code = code;
		this.message = message;
		this.isSuccess = isSuccess;
	}

	public CommonResult(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public static <P> CommonResult<P> operateSuccess() {
		return UnmodifiableCommonResult.SUCCESS;
	}

	public static <P> CommonResult<P> operateSuccess(P p) {
		return new CommonResult<P>(SUCCESS_CODE, OPERATE_SUCCESS_MESSAGE, true, p);
	}

	public static <P> CommonResult<P> operateFail(P p) {
		return new CommonResult<P>(FAIL_CODE, OPERATE_FAIL_MESSAGE, false, p);
	}

	public static <P> CommonResult<P> operateFailWithMessage(String message) {
		return new CommonResult<P>(FAIL_CODE, message, false);
	}

	public static <P> CommonResult<P> operateForbidden(P p) {
		return new CommonResult<P>(FORBIDDEN_CODE, OPERATE_FORBIDDEN_MESSAGE, false, p);
	}

	@SuppressWarnings("unchecked")
	public static <P> CommonResult<P> autoResult(boolean isSuccess) {
		if (isSuccess) {
			return UnmodifiableCommonResult.SUCCESS;
		}
		else {
			return UnmodifiableCommonResult.FAILED;
		}
	}

	public static <P> CommonResult<P> autoResult(boolean isSuccess, P data) {
		if (isSuccess) {
			return CommonResult.operateSuccess(data);
		}
		else {
			return CommonResult.operateFail(data);
		}
	}

	@SuppressWarnings("unchecked")
	public CommonResult<T> operateFail() {
		return UnmodifiableCommonResult.FAILED;
	}

	@SuppressWarnings("unchecked")
	public CommonResult<T> operateNoPower() {
		return UnmodifiableCommonResult.FORBIDDEN;
	}

	/**
	 * 内部类-不可修改类
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@SuppressWarnings("rawtypes")
	private static class UnmodifiableCommonResult<P> extends CommonResult<P> {

		protected static final UnmodifiableCommonResult SUCCESS = new UnmodifiableCommonResult<>(SUCCESS_CODE,
				OPERATE_SUCCESS_MESSAGE, true);

		protected static final UnmodifiableCommonResult FAILED = new UnmodifiableCommonResult<>(FAIL_CODE,
				OPERATE_FAIL_MESSAGE, false);

		protected static final UnmodifiableCommonResult FORBIDDEN = new UnmodifiableCommonResult<>(FORBIDDEN_CODE,
				OPERATE_FORBIDDEN_MESSAGE, false);

		public UnmodifiableCommonResult(int code, String message, boolean isSuccess) {
			super.setCode(code);
			super.setMessage(message);
			super.setSuccess(isSuccess);
		}

		@Override
		public CommonResult<P> setData(P data) {
			throw new UnsupportedOperationException("常量返回结果不允许被修改，如果需要修改结果请创建新的返回结果对象！");
		}

		@Override
		public CommonResult<P> setSuccess(boolean isSuccess) {
			throw new UnsupportedOperationException("常量返回结果不允许被修改，如果需要修改结果请创建新的返回结果对象！");
		}

		@Override
		public CommonResult<P> setMessage(String message) {
			throw new UnsupportedOperationException("常量返回结果不允许被修改，如果需要修改结果请创建新的返回结果对象！");
		}

		@Override
		public CommonResult<P> setCode(int code) {
			throw new UnsupportedOperationException("常量返回结果不允许被修改，如果需要修改结果请创建新的返回结果对象！");
		}

	}

	public static <T> CommonResult<T> ok() {
		return restResult(null, CommonConstants.SUCCESS, null);
	}

	public static <T> CommonResult<T> ok(T data) {
		return restResult(data, CommonConstants.SUCCESS, null);
	}

	public static <T> CommonResult<T> ok(T data, String msg) {
		return restResult(data, CommonConstants.SUCCESS, msg);
	}

	public static <T> CommonResult<T> failed() {
		return restResult(null, CommonConstants.FAIL, null);
	}

	public static <T> CommonResult<T> failed(String msg) {
		return restResult(null, CommonConstants.FAIL, msg);
	}

	public static <T> CommonResult<T> failed(T data) {
		return restResult(data, CommonConstants.FAIL, null);
	}

	public static <T> CommonResult<T> failed(T data, String msg) {
		return restResult(data, CommonConstants.FAIL, msg);
	}

	public static <T> CommonResult<T> restResult(T data, int code, String message) {
		CommonResult<T> apiResult = new CommonResult<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMessage(message);
		return apiResult;
	}

}
