package com.topview.shop.common.security.annotation;

import com.topview.shop.common.security.component.ResourceServerAutoConfiguration;
import com.topview.shop.common.security.component.ResourceServerConfiguration;
import com.topview.shop.common.security.feign.FeignClientConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;

/**
 * @author admin 资源服务注解
 */
@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ ResourceServerAutoConfiguration.class, ResourceServerConfiguration.class, FeignClientConfiguration.class })
public @interface EnableMyResourceServer {

}
