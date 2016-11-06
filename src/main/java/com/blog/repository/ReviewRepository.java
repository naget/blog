package com.blog.repository;

import com.blog.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by t on 2016/10/31.
 */
public interface ReviewRepository extends JpaRepository<Review,Long>{
}
