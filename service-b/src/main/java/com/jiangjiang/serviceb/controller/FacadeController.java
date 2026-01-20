package com.jiangjiang.serviceb.controller;

import com.jiangjiang.common.api.Result;
import com.jiangjiang.serviceb.client.ServiceAClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FacadeController {

    private final ServiceAClient serviceAClient;

    @GetMapping("/call-add")
    public Result<Integer> callAdd(@RequestParam("a") Integer a, @RequestParam("b") Integer b) {

        return serviceAClient.add(a, b);
    }
}