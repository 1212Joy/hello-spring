package cn.com.basic;

import cn.com.basic.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaijiayi on 2016/4/29.
 */
@RestController
@EnableAutoConfiguration
@Import(ApplicationConfiguration.class)
public class ApplicationServer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationServer.class, args);
    }
}