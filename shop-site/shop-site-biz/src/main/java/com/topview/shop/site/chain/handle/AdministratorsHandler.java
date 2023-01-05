package com.topview.shop.site.chain.handle;

import com.topview.shop.common.core.util.CommonResult;
import com.topview.shop.site.api.dto.PlaintDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * 管理员投诉处理情况反馈逻辑
 * @author Akai
 */
public class AdministratorsHandler extends AbstractCheckHandler{
	@Override
	public CommonResult handle(PlaintDto plaintDto) {
		Assert.isTrue(Objects.nonNull(plaintDto.getContent()),"投诉内容不能为空");
		Assert.isTrue(Objects.nonNull(plaintDto.getComplainant()),"举报来源为空");
		Assert.isTrue(Objects.nonNull(plaintDto.getComplainantTime()),"投诉时间不能为空");
		Assert.isTrue(StringUtils.isNotEmpty(String.valueOf(plaintDto.getAdministrator())),"投诉表单创建者为空");
		Assert.isTrue(StringUtils.isNotEmpty(String.valueOf(plaintDto.getSiteId())),"投诉站点为空");
		return super.next(plaintDto);
	}
}
