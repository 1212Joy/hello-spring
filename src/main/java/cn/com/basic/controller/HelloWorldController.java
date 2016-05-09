package cn.com.basic.controller;

import cn.com.basic.dto.HelloWorldDto;
import cn.com.basic.service.DynamicJob;
import cn.com.basic.service.DynamicJobService;
import cn.com.basic.service.HelloWorldService;
import org.quartz.DateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by zhaijiayi on 2016/5/3.
 */
@RestController
@RequestMapping("/hi")
public class HelloWorldController {
    @Autowired
    private HelloWorldService helloWorldService;
    @Autowired
    private DynamicJobService dynamicJobService;

    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public String helloWorld1(HelloWorldDto helloWorldDto)  {
        helloWorldService.add(helloWorldDto);
        return helloWorldDto.getName();
    }
    @RequestMapping(value = "/second", method = RequestMethod.GET)
    public void  helloWorld2(Integer id)  throws Exception {
        Date bizExpiryTime= DateBuilder.futureDate(10, DateBuilder.IntervalUnit.SECOND);//5 means 5 min
        dynamicJobService.runJob(DynamicJob.class,"test",bizExpiryTime,1);
    }
}
