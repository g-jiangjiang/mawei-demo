package com.jiangjiang.serviceb.client;

import com.jiangjiang.common.api.Result;
import com.jiangjiang.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServiceAFallback implements ServiceAClient {

    @Override
    public Result<Integer> add(Integer a, Integer b) {
        log.error("调用 Service A 失败，触发降级逻辑。参数: a={}, b={}", a, b);

        return Result.error(ResultCode.SERVICE_UNAVAILABLE.getCode(), "计算服务暂时不可用，请稍后重试");
    }
}