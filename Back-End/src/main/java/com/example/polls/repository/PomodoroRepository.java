package com.example.polls.repository;

import com.example.polls.model.Pomodoro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PomodoroRepository extends JpaRepository<Pomodoro, Long> {

  //    Page<Pomodoro> findWhereUser(Pageable pageable);

  Page<Pomodoro> findByCreatedBy(Long userId, Pageable pageable);

  @Query("SELECT p FROM Pomodoro p WHERE p.id = :#{#id}")
  Optional<Pomodoro> findOneById(@Param("id") long id);

  @Query("SELECT g FROM Pomodoro g WHERE created_by = :#{#id}")
  List<Pomodoro> findAllWhereUserID(@Param("id") long id);

  //    @Query("SELECT p FROM Pomodoro p WHERE p.id = :#{#id}")
  //    void deleteOneById(@Param("user") User user, @Param("id") long id);

}
