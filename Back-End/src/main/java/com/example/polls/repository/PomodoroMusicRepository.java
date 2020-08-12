package com.example.polls.repository;

import com.example.polls.model.PomodoroMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PomodoroMusicRepository extends JpaRepository<PomodoroMusic, Long> {
//    @Query("select m from PomodoroMusic m where m.user = :#{#user}")
//    Page<PomodoroMusic> findWhereUserId(@Param("user") User user, Pageable pageable);

    @Query("SELECT m FROM PomodoroMusic m WHERE m.id = :#{#id}")
    PomodoroMusic findOneById(@Param("id") long id);

    @Query("SELECT g FROM PomodoroMusic g WHERE created_by = :#{#id}")
    List<PomodoroMusic> findAllWhereUserID(@Param("id") long id);
}

