package com.wanggs.distributedjob;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component

public class MyJob {

    @Autowired
    private Process process;

    // initialDelay延时启动;
    // fixedRate 时间间隔是前次任务和下次任务的开始
    // fixedDelay 固定延时,时间间隔是前次任务的结束到下次任务的开始
//    @Scheduled(fixedDelayString = "3000",initialDelay = 5000)
//    public void process() throws InterruptedException {
//        log.info("process...start");
//        Thread.sleep(2000);
//        log.info("process...stop");
//    }


    //crontab  https://cron.qqe2.com/
//    @Scheduled(cron = "0/2 * * * * ?")
//    public void process1() throws InterruptedException {
//        log.info("process1....start");
//    }


//    @Scheduled(fixedRate = 3000)
//    public void process2() throws InterruptedException {
//        log.info("process2...start");
//        Thread.sleep(5000);
//        log.info("process2...stop");
//    }

    @Scheduled(fixedRate = 3000)
    public void process3() throws InterruptedException {
        process.doProcess1();
    }


}
