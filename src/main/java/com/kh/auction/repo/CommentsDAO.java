package com.kh.auction.repo;

import com.kh.auction.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentsDAO extends JpaRepository<Comments, Integer> {


}
