package com.jiangjiang.serviceb.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfig {

    /**
     * 配置 Feign 重试机制
     * period: 初始重试间隔 100ms
     * maxPeriod: 最大重试间隔 1s
     * maxAttempts: 最大重试次数 (包含第1次调用)，要求重试2次，所以这里设为 3 (1次正常+2次重试)
     */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 3);
    }
}