package com.blog.repository;

import com.blog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by t on 2016/10/31.
 */
public interface CategoryRepository extends JpaRepository<Category,Long>{
    @Query("SELECT c from Category c where c.typeName=?1")
    Category findByType_name(String type_name);
    Category findById(Long id);

}
