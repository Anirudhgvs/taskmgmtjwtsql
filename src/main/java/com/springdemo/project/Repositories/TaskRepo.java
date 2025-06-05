package com.springdemo.project.Repositories;

import com.springdemo.project.Entity.Task;
import com.springdemo.project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, String> {

    void deleteById(String id);
}
