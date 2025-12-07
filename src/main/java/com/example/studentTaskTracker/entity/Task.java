package com.example.studentTaskTracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userOwner;

    @ManyToOne(optional = true)
    @JoinColumn(name = "group_id")
    private TaskGroup group;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus taskStatus = TaskStatus.NEW;

    @Column
    private LocalDateTime deadLine;

    public Task () {

    }

    public Task (String name) {
        this.name = name;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public TaskStatus getTaskStatus() {
//        return taskStatus;
//    }
//
//    public void setTaskStatus(TaskStatus status) {
//        this.taskStatus = status;
//    }
//
//    public LocalDateTime getDeadLine() {
//        return deadLine;
//    }
//
//    public void setDeadLine(LocalDateTime deadline) {
//        this.deadLine = deadline;
//    }
//
//    public User getUserOwner() {
//        return userOwner;
//    }
//
//    public void setUserOwner(User userOwner) {
//        this.userOwner = userOwner;
//    }
//
//    public TaskGroup getGroup() {
//        return group;
//    }
//
//    public void setGroup(TaskGroup group) {
//        this.group = group;
//    }
}
