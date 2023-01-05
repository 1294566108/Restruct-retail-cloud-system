package com.topview.shop.common.xss.core;

import lombok.Getter;

import java.io.IOException;

/**
 * xss jackson 异常
 *
 * @author admin
 */
@Getter
public class JacksonXssException extends IOException implements XssException {

	private final String input;

	public JacksonXssException(String input, String message) {
		super(message);
		this.input = input;
	}

}
