package com.wanggs.sharding;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DistributedShardingApplicationTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void insert() {
        for (int i = 0; i < 10; i++) {
            OrderEntity orderEntity = new OrderEntity();
//            orderEntity.setOrderId(System.currentTimeMillis());
            orderEntity.setUserId(new Random().nextInt(999));
            orderDao.save(orderEntity);
        }
    }

    @Test
    public void findByMasterOrderId() {
        //从主库查数据
        HintManager.getInstance().setPrimaryRouteOnly();
        OrderEntity byOrderId = orderDao.findByOrderId(20L);
        log.info("byOrderId={}", byOrderId);
    }

    @Test
    public void findByUserId() {
        List<OrderEntity> byUserId = orderDao.findByUserId(405);
        log.info("byUserId={}", JSON.toJSONString(byUserId,true));
    }

    @Test
    public void findByOrderId() {
        OrderEntity byUserId = orderDao.findByOrderId(568133336737230849L);
        log.info("byUserId={}", JSON.toJSONString(byUserId,true));
    }

    @Test
    public void update() {
        OrderEntity byOrderId = orderDao.findByOrderId(539507734186799104L);
        log.info("byOrderId={}", byOrderId);
        byOrderId.setUserId(999);
        orderDao.save(byOrderId);
    }

}
