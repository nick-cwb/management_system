package com.isoft.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isoft.system.common.BaseController;
import com.isoft.system.common.BaseJob;
import com.isoft.system.entity.JobAndTrigger;
import com.isoft.system.entity.JobInfo;
import com.isoft.system.service.IJobAndTriggerService;
import com.isoft.system.utils.DateUnit;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static org.quartz.DateBuilder.futureDate;

@RestController
@RequestMapping(value = "job")
public class JobController extends BaseController {

    @Resource
    private IJobAndTriggerService iJobAndTriggerService;

    //加入Qulifier注解，通过名称注入bean
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;
    @Autowired
    private DateUnit dateUnit;

    private static Logger log = LoggerFactory.getLogger(JobController.class);


    /**
     * 添加任务
     *
     * @param jobInfo
     * @throws Exception
     */
    @PostMapping(value = "/addjob")
    public Object addjob(@RequestBody JobInfo jobInfo) throws Exception {
        if ("".equals(jobInfo.getJobClassName()) || "".equals(jobInfo.getJobGroupName()) || "".equals(jobInfo.getCronExpression())) {
            return renderError();
        }
        if (jobInfo.getTimeType() == null) {
            addCronJob(jobInfo);
            return renderSuccess();
        }
        addSimpleJob(jobInfo);
        return renderSuccess();
    }

    //CronTrigger
    public void addCronJob(JobInfo jobInfo) throws Exception {

        // 启动调度器
        scheduler.start();

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobInfo.getJobClassName()).getClass()).
                withIdentity(jobInfo.getJobClassName(), jobInfo.getJobGroupName()).withDescription(jobInfo.getDescription())
                .build();
        

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getCronExpression());
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().
                withIdentity(jobInfo.getJobClassName(), jobInfo.getJobGroupName())
                .withSchedule(scheduleBuilder)
                .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
    }

    //Simple Trigger
    public void addSimpleJob(JobInfo jobInfo) throws Exception {
        // 启动调度器
        scheduler.start();

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobInfo.getJobClassName()).getClass())
                .withIdentity(jobInfo.getJobClassName(), jobInfo.getJobGroupName()).withDescription(jobInfo.getDescription())
                .build();

        DateBuilder.IntervalUnit verDate = dateUnit.verification(jobInfo.getTimeType());
        SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity(jobInfo.getJobClassName(), jobInfo.getJobGroupName())
                .startAt(futureDate(Integer.parseInt(jobInfo.getCronExpression()), verDate))
                .forJob(jobInfo.getJobClassName(), jobInfo.getJobGroupName())
                .build();

        try {
            scheduler.scheduleJob(jobDetail, simpleTrigger);

        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
    }

    /**
     * 暂停任务
     *
     * @param jobInfo
     * @throws Exception
     */
    @PostMapping(value = "/pausejob")
    public Object pausejob(@RequestBody JobInfo jobInfo) throws Exception {
        jobPause(jobInfo.getJobClassName(), jobInfo.getJobGroupName());
        return renderSuccess();
    }

    public void jobPause(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * 恢复任务
     *
     * @param jobInfo
     * @throws Exception
     */
    @PostMapping(value = "/resumejob")
    public Object resumejob(@RequestBody JobInfo jobInfo) throws Exception {
        jobresume(jobInfo.getJobClassName(), jobInfo.getJobGroupName());
        return renderSuccess();
    }

    public void jobresume(String jobClassName, String jobGroupName) throws Exception {
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * 更新任务
     *
     * @param jobInfo
     * @throws Exception
     */
    @PostMapping(value = "/reschedulejob")
    public Object rescheduleJob(@RequestBody JobInfo jobInfo) throws Exception {
        jobreschedule(jobInfo.getJobClassName(), jobInfo.getJobGroupName(), jobInfo.getCronExpression(),jobInfo.getDescription());
        return renderSuccess();
    }

    public void jobreschedule(String jobClassName, String jobGroupName, String cronExpression,String description) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).withDescription(description).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
            throw new Exception("更新定时任务失败");
        }
    }

    /**
     * 删除任务
     * 删除操作前应该暂停该任务的触发器，并且停止该任务的执行
     *
     * @param jobInfo
     * @throws Exception
     */
    @PostMapping(value = "/deletejob")
    public Object deletejob(@RequestBody JobInfo jobInfo) throws Exception {
        jobdelete(jobInfo.getJobClassName(), jobInfo.getJobGroupName());
        return renderSuccess();
    }

    public void jobdelete(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * 查询任务
     *
     * @param current
     * @param size
     * @return
     */
    @GetMapping(value = "/queryjob")
    public Object queryjob(@RequestParam(value = "current", defaultValue = "1") Integer current, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        IPage<JobAndTrigger> jobAndTrigger = iJobAndTriggerService.getJobAndTriggerDetails(current, size);
        return renderSuccess(jobAndTrigger);
    }

    /**
     * 根据类名称，通过反射得到该类，然后创建一个BaseJob的实例。
     * 由于NewJob和HelloJob都实现了BaseJob，
     * 所以这里不需要我们手动去判断。这里涉及到了一些java多态调用的机制
     *
     * @param classname
     * @return
     * @throws Exception
     */
    public static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob) class1.newInstance();
    }

}
