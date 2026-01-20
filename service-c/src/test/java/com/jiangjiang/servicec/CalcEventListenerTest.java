package com.jiangjiang.servicec;

import com.jiangjiang.common.dto.CalcMsgDTO;
import com.jiangjiang.servicec.listener.CalcEventListener;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class CalcEventListenerTest {

    @Test
    public void testHandleMessage() {
        CalcEventListener listener = new CalcEventListener();

        String traceId = UUID.randomUUID().toString();
        CalcMsgDTO msg = new CalcMsgDTO(traceId, 1, 2, 3, LocalDateTime.now());

        System.out.println("=== 开始测试第一次消费 (应该打印日志) ===");
        listener.handleMessage(msg);

        System.out.println("=== 开始测试重复消费 (应该打印'检测到重复消息') ===");
        listener.handleMessage(msg);
    }
}