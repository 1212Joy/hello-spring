package cn.com.basic.service;

import org.junit.Test;
import org.quartz.DateBuilder;

import java.util.Date;

/**
 * Created by zhaijiayi on 2016/5/9.
 */
public class DynamicJobServiceTest {

    @Test
    public void testRunJob() throws Exception {
        DynamicJobService dynamicJobService = new DynamicJobService() ;
        Date bizExpiryTime= DateBuilder.futureDate(10, DateBuilder.IntervalUnit.SECOND);//5 means 5 min
        dynamicJobService.runJob(TestJob.class,"test",bizExpiryTime,1);
    }


}
