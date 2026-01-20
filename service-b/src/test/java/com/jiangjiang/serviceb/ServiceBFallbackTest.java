package com.jiangjiang.serviceb;

import com.jiangjiang.common.api.Result;
import com.jiangjiang.common.api.ResultCode;
import com.jiangjiang.serviceb.client.ServiceAFallback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServiceBFallbackTest {

    @Test
    public void testFallback() {

        ServiceAFallback fallback = new ServiceAFallback();


        Result<Integer> result = fallback.add(1, 2);


        Assertions.assertEquals(ResultCode.SERVICE_UNAVAILABLE.getCode(), result.getCode());
        Assertions.assertEquals("计算服务暂时不可用，请稍后重试", result.getMsg());
        Assertions.assertNull(result.getData());
    }
}