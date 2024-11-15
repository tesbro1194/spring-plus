package org.example.expert.domain.todo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SearchTodoRequest {
    private String title;
    private LocalDateTime startOfPeriod;
    private LocalDateTime  endOfPeriod;
    private String nickname;
}
