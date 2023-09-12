package com.kh.auction.repo;

import com.kh.auction.domain.Comments;
import com.kh.auction.domain.Replies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepliesDAO extends JpaRepository<Replies, Integer> {

    // 댓글 1개의 대댓글 전체 조회 - SELECT * FROM comments WHERE comment_id = ?
    @Query(value = "SELECT * FROM comments WHERE comment_id = :id", nativeQuery = true)
    List<Comments> findByRepliesList(int id);

}
