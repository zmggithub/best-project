package com.consumer.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.consumer.common.utils.R;
/**
 * TODO
 *
 * @Author zmgab@foxmail.com
 * @Date 2022/9/9 9:07
 */
@FeignClient(value = "provider",path = "/order")
public interface FeignService {

    @RequestMapping("/findOrderByUserId/{userId}")
    public R findOrderByUserId(@PathVariable("userId") Integer userId);



}
