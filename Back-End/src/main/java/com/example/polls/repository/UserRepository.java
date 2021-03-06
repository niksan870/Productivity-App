package com.example.polls.repository;

import com.example.polls.model.Goal;
import com.example.polls.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Optional<User> findByUsernameOrEmail(String username, String email);

  List<User> findByIdIn(List<Long> userIds);

  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  @Query("SELECT g FROM User g JOIN g.sub_goals u WHERE u.id = :id AND g.id != :user_id")
  Set<User> getParticipants(@Param("id") UUID id, @Param("user_id") long user_id);

  //    @Query("SELECT g FROM GoalChart g WHERE g.created_by IN (1,2)")
  //    List<User> getGoalsWithProfilesAndGraphs(@Param("ids") List<Long> ids);
}
