package com.jiangjiang.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalcMsgDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String traceId;
    private Integer paramA;
    private Integer paramB;
    private Integer resultC;
    private LocalDateTime createTime;
}