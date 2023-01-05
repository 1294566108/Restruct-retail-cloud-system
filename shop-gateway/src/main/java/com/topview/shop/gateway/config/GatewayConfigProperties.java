package com.topview.shop.gateway.config;

import com.topview.shop.gateway.filter.PasswordDecoderFilter;
import com.topview.shop.gateway.filter.ValidateCodeGatewayFilter;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * @author namelesssss 网关配置文件 参照nacos上的shop-gateway-dev.yml
 */
@Data
@RefreshScope
@ConfigurationProperties("gateway")
public class GatewayConfigProperties {

	/**
	 * 网关解密登录前端密码 秘钥 {@link PasswordDecoderFilter}
	 */
	private String encodeKey;

	/**
	 * 网关不需要校验验证码的客户端 {@link ValidateCodeGatewayFilter}
	 */
	private List<String> ignoreClients;

}
