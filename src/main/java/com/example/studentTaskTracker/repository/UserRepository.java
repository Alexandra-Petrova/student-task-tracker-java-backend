package com.example.studentTaskTracker.repository;

import com.example.studentTaskTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    List<User> findByNameStartsWithIgnoreCase(String prefix);

    // запрос на чтение
//    @Query(value = "select a from User where a.name like :name", nativeQuery = true)
//    List<User> searchByName(@Param("name") String name);
}
