package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.request.SearchTodoRequest;
import org.example.expert.domain.todo.dto.response.SearchTodoResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TodoRepositoryQuery {
    Optional<Todo> findByIdWithUser(Long todoId);

    Page<SearchTodoResponse> findByRequest(Pageable pageable, SearchTodoRequest request);
}
