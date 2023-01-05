package com.topview.shop.common.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * xxl-job配置
 *
 * @author Rye
 */
@Data
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

	@NestedConfigurationProperty
	private XxlAdminProperties admin = new XxlAdminProperties();

	@NestedConfigurationProperty
	private XxlExecutorProperties executor = new XxlExecutorProperties();

}
