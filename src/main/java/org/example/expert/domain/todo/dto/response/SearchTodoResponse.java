package org.example.expert.domain.todo.dto.response;

import com.querydsl.core.annotations.QueryProjection;

public class SearchTodoResponse {
    private String title;
    private Long managerCount;
    private Long commentCount;

    @QueryProjection
    public SearchTodoResponse(String title, Long managerCount, Long commentCount) {
        this.title = title;
        this.managerCount = managerCount;
        this.commentCount = commentCount;
    }
}
