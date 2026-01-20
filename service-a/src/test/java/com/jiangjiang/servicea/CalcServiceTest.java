package com.jiangjiang.servicea;

import com.github.benmanes.caffeine.cache.Cache;
import com.jiangjiang.servicea.config.RabbitMQConfig;
import com.jiangjiang.servicea.entity.CalcLog;
import com.jiangjiang.servicea.repository.CalcLogRepository;
import com.jiangjiang.servicea.service.CalcService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalcServiceTest {

    @InjectMocks
    private CalcService calcService;

    @Mock
    private CalcLogRepository repository;

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private Cache<Integer, Integer> caffeineCache;

    @Test
    public void testAdd_Normal() {

        Integer a = 10;
        Integer b = 20;

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        when(repository.existsByParamAAndParamBAndCallTimeAfter(eq(a), eq(b), any(LocalDateTime.class)))
                .thenReturn(false);

        Integer result = calcService.add(a, b);

        Assertions.assertEquals(30, result);

        verify(repository, times(1)).save(any(CalcLog.class));

        verify(rabbitTemplate, times(1)).convertAndSend(eq(RabbitMQConfig.EXCHANGE_NAME), eq(RabbitMQConfig.ROUTING_KEY), any(Object.class));
    }

    @Test
    public void testAdd_Idempotent() {
        Integer a = 10;
        Integer b = 20;

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        when(repository.existsByParamAAndParamBAndCallTimeAfter(eq(a), eq(b), any(LocalDateTime.class)))
                .thenReturn(true);

        Integer result = calcService.add(a, b);

        Assertions.assertEquals(30, result);

        verify(repository, times(0)).save(any(CalcLog.class));
    }
}