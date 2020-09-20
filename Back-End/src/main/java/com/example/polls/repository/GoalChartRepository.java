package com.example.polls.repository;

import com.example.polls.model.GoalChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoalChartRepository extends JpaRepository<GoalChart, UUID> {
  @Query("SELECT g FROM GoalChart g where goal_id = :#{#id}")
  GoalChart findByGoalChartByGoalId(UUID id);

  @Query("SELECT g FROM GoalChart g where goal_id = :#{#id} AND created_by = :user_id")
  Optional<GoalChart> findByGoalIdAndUserId(UUID id, Long user_id);

  @Query("SELECT g FROM GoalChart g where id = :#{#id}")
  List<GoalChart> findAllById(UUID id);

  @Query("SELECT g FROM GoalChart g where goal_id = :#{#id}")
  List<GoalChart> findAllByGoalId(UUID id);

  @Query("SELECT g FROM GoalChart g where created_by IN :ids")
  List<GoalChart> getGoalsWithProfilesAndGraphs(List<Long> ids);
}
