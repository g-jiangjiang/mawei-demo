package com.jiangjiang.servicec.listener;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.jiangjiang.common.dto.CalcMsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CalcEventListener {


    private final Cache<String, Boolean> processedTraceIds = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(10000)
            .build();


    @RabbitListener(queuesToDeclare = @Queue("calc.queue"))
    public void handleMessage(CalcMsgDTO msg) {
        String traceId = msg.getTraceId();


        if (processedTraceIds.getIfPresent(traceId) != null) {
            log.warn("检测到重复消息，已忽略。traceId: {}", traceId);
            return;
        }


        String timeStr = msg.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.printf("[%s] 调用时间:%s | 入参:a=%d,b=%d | 结果:c=%d%n",
                traceId,
                timeStr,
                msg.getParamA(),
                msg.getParamB(),
                msg.getResultC());


        processedTraceIds.put(traceId, true);
    }
}