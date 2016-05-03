package cn.com.basic.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zhaijiayi on 2016/5/3.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"cn.com.basic"})
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {


}
