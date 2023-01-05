package com.topview.shop.site;

import com.topview.shop.common.feign.annotation.EnableMyFeignClients;
import com.topview.shop.common.security.annotation.EnableMyResourceServer;
import com.topview.shop.common.swagger.annotation.EnableDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Akai
 * 用户统一管理系统
 */
@EnableDoc
@EnableMyResourceServer
@EnableMyFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceSiteApplication.class, args);
	}

}
