package cn.com.basic.service;

import cn.com.basic.dao.HelloWorldDao;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by zhaijiayi on 2016/5/9.
 */
@Service
public class DynamicJobService extends BaseService{
    private SchedulerFactory sf = null;
    private Scheduler scheduler = null;

    /***
     * 初始化任务对象
     */
    public void initAndStartScheduler() throws Exception{
        if(sf == null) {
            sf = new StdSchedulerFactory();
            scheduler = sf.getScheduler();
            // 启动定时任务,在任务业务操作类处理完成后结束定时操作（Job实现类中结束）。
            scheduler.start();
        }
    }

    /***
     * 启动一个定时任务
     * @param jobTaskKey   定时任务的Key值
     * @param startTime 定时任务的启动时间，根据业务动态设置。
     * @param jobData   传递给定时任务的业务数据。
     */

    public void runJob(DynamicJob obj, String jobTaskKey, Date startTime, Object jobData) throws Exception {
        logger.info("===============================定时器启动===============================");
        HelloWorldDao helloWorldDao = obj.getHelloWorldDao() ;
        Class<?extends Job> jobclass = (Class<? extends Job>)obj.getClass();
        initAndStartScheduler(); //定时任务初始化
        //====================================================================================
        String job_key  = String.format("job_%s",jobTaskKey);
        String job_group_key = String.format("group_%s",jobTaskKey);
        String trriger_key   = String.format("trigger_%s",jobTaskKey);
        JobDetail job   = newJob(jobclass).withIdentity(job_key, job_group_key).build();
     /*   // 设置执行任务时所需要的业务数据
        Field[] fields = jobclass.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible( true ); // 设置些属性是可以访问的
                HelloWorldDao helloWorldDao =(HelloWorldDao) field.get(obj);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }*/
        job.getJobDataMap().put("JOB_DATA",helloWorldDao); //给job实例增加属性或配置


        Trigger trigger = newTrigger().withIdentity(trriger_key, job_group_key).startNow().build();  //startAt(startTime) startNow
        // 设置定时任务
        scheduler.scheduleJob(job, trigger);

        SimpleDateFormat sfmDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String atTime = sfmDateFormat.format(startTime);
        logger.info("=====================任务已经启动，预计在"+atTime+"执行====================");

    }
}
