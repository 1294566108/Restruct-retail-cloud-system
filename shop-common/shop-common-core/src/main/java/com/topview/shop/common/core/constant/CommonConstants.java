package com.topview.shop.common.core.constant;

/**
 * @author Akai
 */
public interface CommonConstants {

	/**
	 * 操作成功消息体
	 */
	String OPERATE_SUCCESS_MESSAGE = "Operate Success";

	/**
	 * 操作失败消息体
	 */
	String OPERATE_FAIL_MESSAGE = "Operate Fail";

	/**
	 * 操作无权限消息体
	 */
	String OPERATE_FORBIDDEN_MESSAGE = "Permission Denied";

	/**
	 * 成功校验码
	 */
	int SUCCESS_CODE = 200;

	/**
	 * 失败校验码
	 */
	int FAIL_CODE = 400;

	/**
	 * 无权限校验码
	 */
	int FORBIDDEN_CODE = 403;

	/**
	 * 删除
	 */
	String STATUS_DEL = "1";

	/**
	 * 正常
	 */
	String STATUS_NORMAL = "0";

	/**
	 * 锁定
	 */
	String STATUS_LOCK = "9";

	/**
	 * 菜单树根节点
	 */
	Long MENU_TREE_ROOT_ID = -1L;

	/**
	 * 菜单
	 */
	String MENU = "0";

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * JSON 资源
	 */
	String CONTENT_TYPE = "application/json; charset=utf-8";

	/**
	 * 前端工程名
	 */
	String FRONT_END_PROJECT = "topview-ui";

	/**
	 * 后端工程名
	 */
	String BACK_END_PROJECT = "topview";

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 0;

	/**
	 * 失败标记
	 */
	Integer FAIL = 1;

	/**
	 * 当前页
	 */
	String CURRENT = "current";

	/**
	 * size
	 */
	String SIZE = "size";

	/**
	 * 请求开始时间
	 */
	String REQUEST_START_TIME = "REQUEST-START-TIME";

}
