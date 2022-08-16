package com.pofol.shop.repository;

import com.pofol.shop.domain.dto.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.item.id = :id ")
    List<Comment> findCommentsByItemId(@Param("id") Long id);

}
