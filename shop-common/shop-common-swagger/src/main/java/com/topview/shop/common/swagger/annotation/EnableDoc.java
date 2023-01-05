package com.topview.shop.common.swagger.annotation;

import com.topview.shop.common.swagger.config.SwaggerAutoConfiguration;
import com.topview.shop.common.swagger.support.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启 topview spring doc
 *
 * @author admin
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({ SwaggerAutoConfiguration.class })
public @interface EnableDoc {

}
