package com.example.polls.repository;

import com.example.polls.model.Goal;
import com.example.polls.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GoalsRepository extends JpaRepository<Goal, UUID> {
    @Query("SELECT g FROM Goal g WHERE g.title = :#{#goalTitle}")
    Goal findOne(@Param("goalTitle") String goalTitle);

    @Query("SELECT g FROM Goal g WHERE created_by = :#{#id}")
    List<Goal> findAllWhereUserID(@Param("id") long id);

    @Query("SELECT g FROM Goal g WHERE created_by = :#{#id} AND g.title LIKE CONCAT('%',:#{#q},'%')")
    Page<Goal> findWhereUserIdWithFilter(@Param("id") long id, @Param("q") String q, Pageable pageable);

    @Query("SELECT g FROM Goal g WHERE (is_private = 0 OR created_by = :#{#id}) AND g.title LIKE CONCAT('%',:#{#q},'%')")
    Page<Goal> findWithFilter(@Param("id") long id, @Param("q") String q, Pageable pageable);

    Goal findOneById(UUID id);

    @Query("SELECT g.attendees FROM Goal g WHERE g.id = :#{#id}")
    List<User> getParticipants(@Param("id") UUID id);

    @Query("SELECT g FROM Goal g WHERE created_by = :#{#id} AND is_private = 0")
    List<Goal> getGoalsFromProfile(@Param("id") long id);

    @Query("SELECT g FROM Goal g WHERE created_by = :#{#id}")
    List<Goal> getGoalsFromMyProfile(@Param("id") long id);
}
