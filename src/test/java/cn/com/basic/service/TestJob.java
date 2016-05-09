package cn.com.basic.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * Created by zhaijiayi on 2016/5/9.
 */
public class TestJob implements   Job{//StatefulJob

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Integer investId = (Integer)jobExecutionContext.getJobDetail().getJobDataMap().get("JOB_DATA");
        JobKey job_key= jobExecutionContext.getJobDetail().getKey();
        System.out.println(investId);
        System.out.println(job_key);
    }
}
