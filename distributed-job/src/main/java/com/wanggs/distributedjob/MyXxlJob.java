package com.wanggs.distributedjob;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.util.ShardingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MyXxlJob {

    @XxlJob("myXxlJobHandler")
    public ReturnT<String> execute(String param) {
        log.info("myXxlJobHandler execute...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //轮询 流量均摊，推荐
        //故障转移 流量到第一台，查日志方便

        //从数据库里查询所有用户，为每一个用户生成结单
        //10w用户，2台服务器，每台运算5w用户数据，耗时减半
        ShardingUtil.ShardingVO shardingVo = ShardingUtil.getShardingVo();

        //分片，必须掌握
//        List<Integer> list = Arrays.asList(1, 2, 3, 4);
//        for (Integer i : list) {
//            if (i % shardingVo.getTotal() == shardingVo.getIndex()) {
//                log.info("myXxlJobHandler execute...user={}. shardingVo={}",
//                        i, new Gson().toJson(shardingVo));
//            }
//        }

        XxlJobLogger.log("myXxlJobHandler execute...");
        return ReturnT.SUCCESS;
    }


    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public ReturnT<String> demoJobHandler(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");
        System.err.println("XXL-JOB, Hello World.--->> param: " + param);

        for (int i = 0; i < 5; i++) {
            XxlJobLogger.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }
        return ReturnT.SUCCESS;
    }


    /**
     * 2、分片广播任务
     */
    @XxlJob("shardingJobHandler")
    public ReturnT<String> shardingJobHandler(String param) throws Exception {

        // 分片参数
        ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
        XxlJobLogger.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardingVO.getIndex(), shardingVO.getTotal());

        // 业务逻辑
        for (int i = 0; i < shardingVO.getTotal(); i++) {
            if (i == shardingVO.getIndex()) {
                System.err.println("第 " + i + " 片, 命中分片开始处理");
                XxlJobLogger.log("第 {} 片, 命中分片开始处理", i);
            } else {
                System.err.println("第 " + i + " 片, 忽略");
                XxlJobLogger.log("第 {} 片, 忽略", i);
            }
        }

        return ReturnT.SUCCESS;
    }


}
