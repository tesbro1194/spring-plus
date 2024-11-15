package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.request.SearchTodoRequest;
import org.example.expert.domain.todo.dto.response.SearchTodoResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryQuery {

    @Query( "SELECT t " +
            "FROM Todo t LEFT JOIN FETCH t.user " +
            "WHERE t.weather = :weather " +
            "ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByWeatherOrderByModifiedAtDesc(Pageable pageable, @Param("weather") String weather);

    @Query( "SELECT t FROM Todo t LEFT JOIN FETCH t.user " +
            "WHERE t.modifiedAt BETWEEN :startOfPeriod and :endOfPeriod " +
            "ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByPeriodOrderByModifiedAtDesc(
            Pageable pageable,
            @Param("startOfPeriod") LocalDateTime  startOfPeriod,
            @Param("endOfPeriod") LocalDateTime  endOfPeriod
    );

    @Query( "SELECT t " +
            "FROM Todo t LEFT JOIN FETCH t.user " +
            "WHERE t.modifiedAt >= :date " +
            "ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByDateOrderByModifiedAtDesc(Pageable pageable, LocalDateTime date);

    @Query( "SELECT t " +
            "FROM Todo t LEFT JOIN FETCH t.user u " +
            "ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

/*    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);*/
}
