package com.springdemo.project.Repositories;

import com.springdemo.project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    User findByUsername(String username);

    void deleteByUsername(String username);

}
