package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.request.SearchTodoRequest;
import org.example.expert.domain.todo.dto.response.QSearchTodoResponse;
import org.example.expert.domain.todo.dto.response.SearchTodoResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TodoRepositoryQueryImpl implements TodoRepositoryQuery {
    private final JPAQueryFactory queryFactory;
    QTodo todo = QTodo.todo;
    QManager manager = QManager.manager;
    QComment comment = QComment.comment;
    QUser user = QUser.user;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        Todo result = queryFactory.select(todo)
                .from(todo)
                .leftJoin(todo.user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<SearchTodoResponse> findByRequest(Pageable pageable, SearchTodoRequest request) {

        List<SearchTodoResponse> results = queryFactory
                .select(new QSearchTodoResponse(
                        todo.title,
                        JPAExpressions.select(manager.count()).from(manager).where(manager.todo.eq(todo)),
                        JPAExpressions.select(comment.count()).from(comment).where(comment.todo.eq(todo))
                ))
                .from(todo)
                .where(
                        titleContain(request.getTitle()),
                        createdDateBetween(request.getStartOfPeriod(), request.getEndOfPeriod()),
                        nicknameContain(request.getNickname())
                )
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(results, pageable,
                () -> queryFactory.select(todo.count())
                        .from(todo)
                        .where(
                                titleContain(request.getTitle()),
                                createdDateBetween(request.getStartOfPeriod(), request.getEndOfPeriod()),
                                nicknameContain(request.getNickname())
                        ).fetchOne()
        );
    }

    private BooleanExpression titleContain(String title) {
        return title != null ? todo.title.contains(title) : null;
    }

    private BooleanExpression createdDateBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null && end == null) return null;
        if (start == null) return todo.createdAt.loe(end);
        if (end == null) return todo.createdAt.goe(start);
        return todo.createdAt.between(start, end);
    }

    private BooleanExpression nicknameContain(String nickname) {
        return nickname != null ? user.nickname.contains(nickname) : null;
    }
}