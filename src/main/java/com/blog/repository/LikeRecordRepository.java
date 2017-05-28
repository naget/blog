package com.blog.repository;

import com.blog.model.LikeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by t on 2017/3/16.
 */
@Repository
public interface LikeRecordRepository extends JpaRepository<LikeRecord,Long> {
    @Query("SELECT l from LikeRecord l where l.likerId=?1 and l.articleId=?2")
    List<LikeRecord> findByLikerIdAndArticleId(Long user_id, Long article_id);
}
