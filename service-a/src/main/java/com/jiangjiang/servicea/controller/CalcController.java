package com.jiangjiang.servicea.controller;

import com.jiangjiang.common.api.Result;
import com.jiangjiang.servicea.service.CalcService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalcController {

    private final CalcService calcService;

    @GetMapping("/add")
    public Result<Integer> add(@RequestParam("a") Integer a, @RequestParam("b") Integer b) {
        Integer result = calcService.add(a, b);
        return Result.success(result);
    }
}