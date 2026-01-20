package com.jiangjiang.serviceb.client;

import com.jiangjiang.common.api.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "service-a", url = "${remote.service-a.url}", fallback = ServiceAFallback.class)
public interface ServiceAClient {

    @GetMapping("/api/add")
    Result<Integer> add(@RequestParam("a") Integer a, @RequestParam("b") Integer b);
}