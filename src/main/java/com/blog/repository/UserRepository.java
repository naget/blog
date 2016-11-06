package com.blog.repository;

import com.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wang0 on 2016/10/31 0031.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByNames(String names);
}

