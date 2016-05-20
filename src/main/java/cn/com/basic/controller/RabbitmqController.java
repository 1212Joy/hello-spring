package cn.com.basic.controller;

import cn.com.basic.dto.HelloWorldDto;
import cn.com.basic.service.HelloWorldService;
import cn.com.basic.service.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaijiayi on 2016/5/17.
 */
@RestController
public class RabbitmqController {
    @Autowired
    private RabbitmqService rabbitmqService;

    @RequestMapping(value = "/mq", method = RequestMethod.GET)
    public String helloWorld1()  {

        return "";
    }
}
