package com.jiangjiang.servicea.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "calc_log")
public class CalcLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer paramA;

    @Column(nullable = false)
    private Integer paramB;

    @Column(nullable = false)
    private Integer resultC;

    @Column(nullable = false)
    private LocalDateTime callTime;
}