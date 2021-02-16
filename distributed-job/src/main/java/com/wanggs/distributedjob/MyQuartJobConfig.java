package com.wanggs.distributedjob;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyQuartJobConfig {

    /**
     * 任务详情
     * @return
     */
    @Bean
    public JobDetail jobDetail() {
        JobDetail detail = JobBuilder.newJob(MyQuartzJob.class)
                .withIdentity("job1", "group1")
                .storeDurably()
                .build();
        return detail;
    }

    /**
     * 触发器
     * @return
     */
    @Bean
    public Trigger trigger() {
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0,5 * * * * ?"))
                .build();
        return trigger;
    }
}
