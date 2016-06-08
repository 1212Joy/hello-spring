package cn.com.basic.service;

import cn.com.basic.dao.HelloWorldDao;
import cn.com.basic.dto.HelloWorldDto;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhaijiayi on 2016/5/9.
 */
@Component
public class DynamicJob  implements Job {
    @Autowired
    public  HelloWorldDao helloWorldDao;

    public HelloWorldDao getHelloWorldDao() {
        return helloWorldDao;
    }

    public void setHelloWorldDao(HelloWorldDao helloWorldDao) {
        this.helloWorldDao = helloWorldDao;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        HelloWorldDao helloWorldDao1 = (HelloWorldDao)jobExecutionContext.getJobDetail().getJobDataMap().get("JOB_DATA");
        JobKey job_key= jobExecutionContext.getJobDetail().getKey();
       // System.out.println(investId);
        System.out.println(job_key);
        HelloWorldDto helloWorldDto = helloWorldDao1.query(1);
System.out.print("sss"+helloWorldDto.getName());
    }
}
