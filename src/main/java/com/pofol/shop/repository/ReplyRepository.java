package com.pofol.shop.repository;

import com.pofol.shop.domain.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<ReplyComment, Long> {

}
