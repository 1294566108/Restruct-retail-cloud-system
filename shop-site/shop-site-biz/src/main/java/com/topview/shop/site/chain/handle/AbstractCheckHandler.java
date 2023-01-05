package com.topview.shop.site.chain.handle;

import com.topview.shop.common.core.util.CommonResult;
import com.topview.shop.site.api.dto.PlaintDto;
import com.topview.shop.site.chain.config.ComplaintHandlerConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Akai
 */
@Component
public abstract class AbstractCheckHandler {
	/**
	 * 当前处理器持有下一个处理器的引用
	 */
	@Getter
	@Setter
	protected AbstractCheckHandler nextHandler;


	/**
	 * 处理器配置
	 */
	@Setter
	@Getter
	protected ComplaintHandlerConfig config;

	/**
	 * 处理器执行方法
	 */
	public abstract CommonResult handle(PlaintDto plaintDto);

	/**
	 * 链路传递
	 * @param plaintDto
	 */
	protected CommonResult next(PlaintDto plaintDto) {
		//下一个链路没有处理器了，直接返回
		if (Objects.isNull(nextHandler)) {
			return CommonResult.ok();
		}

		//执行下一个处理器
		return nextHandler.handle(plaintDto);
	}

}
