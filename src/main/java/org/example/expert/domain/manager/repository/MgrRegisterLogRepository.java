package org.example.expert.domain.manager.repository;

import org.example.expert.domain.manager.entity.MgrRegisterReqLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MgrRegisterLogRepository extends JpaRepository<MgrRegisterReqLog, Long> {
}
