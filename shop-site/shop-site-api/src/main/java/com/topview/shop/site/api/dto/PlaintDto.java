package com.topview.shop.site.api.dto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 投诉信息实体
 * @author Akai
 */
@Data
@Accessors(chain = true)
public class PlaintDto {

	/**
	 * 生成投诉信息的人员(商务处)
	 */
	private int administrator;

	/**
	 * 投诉的站点(管理员指派)
	 */
	private int siteId;

	/**
	 * 举报来源(举报人、匿名、微信群、Q群、后勤意见箱、学生权益部)
	 */
	private String complainant;

	/**
	 * 投诉内容
	 */
	private String content;

	/**
	 * 投诉时间
	 */
	private LocalDateTime complainantTime;

	/**
	 * 投诉状态("未处理"、"处理中"、"已处理")
	 */
	private int status;

	/**
	 * 处理人
	 */
	private String handler;

	/**
	 * 管理员处理情况反馈
	 */
	private String administratorFeedback;

	/**
	 * 商铺处理情况反馈，方便后续回访和评估
	 */
	private String siteFeedback;

	/**
	 * 商务科最终处理意见
	 */
	private String finalOpinion;

	/**
	 * 是否被删除
	 */
	@TableLogic
	private String delFlag;
}
