package com.example.polls.repository;

import com.example.polls.model.GoalChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GoalChartRepository extends JpaRepository<GoalChart, UUID> {
    @Query("SELECT g FROM GoalChart g where goal_id = :#{#id}")
    GoalChart findByGoalChartByGoalId(UUID id);
}
