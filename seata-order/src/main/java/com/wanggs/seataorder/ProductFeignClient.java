package com.wanggs.seataorder;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 说明 因为没有引入注册中心 eureka,nacos...所以要指定 url = "http://localhost:8086"
 */
@FeignClient(name = "seata-product", url = "http://localhost:8086")
public interface ProductFeignClient {

    @GetMapping("/deduct")
    Boolean deduct(@RequestParam Long productId,
                   @RequestParam Integer count);
}
