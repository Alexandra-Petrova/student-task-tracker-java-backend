package com.example.studentTaskTracker.repository;

import com.example.studentTaskTracker.entity.Task;
import com.example.studentTaskTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserOwner(User user);

    List<Task> findAllByUserOwner_Id(Long userId);

    @Query("SELECT t.taskStatus AS status, COUNT(t) AS count FROM Task t GROUP BY t.taskStatus")
    List<Map<String, Object>> countTasksByStatus();

    @Query("SELECT g.id AS groupId, g.name AS groupName, COUNT(t) AS count " +
            "FROM Task t JOIN t.group g " +
            "GROUP BY g.id, g.name")
    List<Map<String, Object>> countTasksByGroup();
}
