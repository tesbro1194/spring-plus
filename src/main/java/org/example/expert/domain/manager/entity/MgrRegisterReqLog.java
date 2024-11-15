package org.example.expert.domain.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;

@Getter
@Entity
@Table(name = "log")
@NoArgsConstructor
public class MgrRegisterReqLog extends Timestamped {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String requestLog;

    public MgrRegisterReqLog(String requestLog) {
        this.requestLog = requestLog;
    }
}
