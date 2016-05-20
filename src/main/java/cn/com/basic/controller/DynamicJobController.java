package cn.com.basic.controller;

import cn.com.basic.service.DynamicJob;
import cn.com.basic.service.DynamicJobService;
import org.quartz.DateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by zhaijiayi on 2016/5/10.
 */
@RestController
public class DynamicJobController {
    @Autowired
    private DynamicJobService dynamicJobService;
    @RequestMapping(value = "/dynamicJob", method = RequestMethod.GET)
    public void  dynamicJob()  throws Exception {
        Date bizExpiryTime= DateBuilder.futureDate(10, DateBuilder.IntervalUnit.SECOND);//5 means 5 min
        dynamicJobService.runJob(DynamicJob.class,"test",bizExpiryTime,1);
    }
}
