package com.example.studentTaskTracker.repository;

import com.example.studentTaskTracker.entity.TaskGroup;
import com.example.studentTaskTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {
    List<TaskGroup> findAllByUserOwner(User user);
}
