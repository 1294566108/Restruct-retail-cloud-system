package com.alibaba.csp.sentinel.dashboard.domain.vo.gateway.api;

/**
 * Value Object for add or update gateway api.
 *
 * @author admin
 */
public class ApiPredicateItemVo {

	/**
	 * The pattern for matching url.
	 */
	private String pattern;

	/**
	 * The matching Strategy in url. Constants are defined in class
	 * SentinelGatewayConstants.\
	 *
	 * <ul>
	 * <li>0(URL_MATCH_STRATEGY_EXACT): exact match mode</li>
	 * <li>1(URL_MATCH_STRATEGY_PREFIX): prefix match mode</li>
	 * <li>2(URL_MATCH_STRATEGY_REGEX): regex match mode</li>
	 * </ul>
	 */
	private Integer matchStrategy;

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Integer getMatchStrategy() {
		return matchStrategy;
	}

	public void setMatchStrategy(Integer matchStrategy) {
		this.matchStrategy = matchStrategy;
	}

}
