package com.blog.repository;

import com.blog.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tianfeng on 2017/5/20.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    Page<Notification> findByAuthorId(Long autherId, Pageable pageable);
}
