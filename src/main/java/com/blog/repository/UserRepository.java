package com.blog.repository;

import com.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wang0 on 2016/10/31 0031.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByNames(String names);
    User findById(Long id);
//    @Query("select u from User u where u.hotIndex>1")
//    Page<User> findHotUser(Pageable pageable);
    Page<User> findAll(Pageable pageable);
//    @Query("select u.name from User u where u.id=?1")
//    String findNameById(Long id);
}

