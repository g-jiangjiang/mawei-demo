package com.jiangjiang.servicea.repository;

import com.jiangjiang.servicea.entity.CalcLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CalcLogRepository extends JpaRepository<CalcLog, Long> {

    boolean existsByParamAAndParamBAndCallTimeAfter(Integer paramA, Integer paramB, LocalDateTime time);
}