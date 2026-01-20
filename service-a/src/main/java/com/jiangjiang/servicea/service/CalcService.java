package com.jiangjiang.servicea.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.jiangjiang.common.dto.CalcMsgDTO;
import com.jiangjiang.servicea.config.RabbitMQConfig;
import com.jiangjiang.servicea.entity.CalcLog;
import com.jiangjiang.servicea.repository.CalcLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalcService {

    private final CalcLogRepository repository;
    private final StringRedisTemplate redisTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final Cache<Integer, Integer> caffeineCache;

    @Transactional(rollbackFor = Exception.class)
    public Integer add(Integer a, Integer b) {

        if (a == null || b == null || a <= 0 || b <= 0) {
            throw new IllegalArgumentException("参数 a 和 b 必须是正整数");
        }


        caffeineCache.get(a, k -> {
            log.info("参数 a={} 首次存入本地缓存", k);
            return k;
        });


        String redisKey = "{serviceA}:b:" + b;
        redisTemplate.opsForValue().set(redisKey, String.valueOf(b), Duration.ofMinutes(30));


        int result = a + b;
        LocalDateTime now = LocalDateTime.now();


        LocalDateTime fiveMinAgo = now.minusMinutes(5);
        boolean exists = repository.existsByParamAAndParamBAndCallTimeAfter(a, b, fiveMinAgo);

        if (!exists) {
            CalcLog logEntity = new CalcLog();
            logEntity.setParamA(a);
            logEntity.setParamB(b);
            logEntity.setResultC(result);
            logEntity.setCallTime(now);
            repository.save(logEntity);
            log.info("数据库落库成功: a={}, b={}", a, b);
        } else {
            log.info("幂等性拦截: 5分钟内已存在相同请求，跳过落库");
        }


        String traceId = UUID.randomUUID().toString().replace("-", "");
        CalcMsgDTO msg = new CalcMsgDTO(traceId, a, b, result, now);


        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, msg);
        log.info("消息已发送 MQ, traceId: {}", traceId);

        return result;
    }
}