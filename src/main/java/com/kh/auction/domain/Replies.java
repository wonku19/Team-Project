package com.kh.auction.domain;

import jakarta.persistence.*;

import java.util.Date;

public class Replies {

    @Id
    @Column(name = "reply_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "repliesSequence")
//    @SequenceGenerator(name = "repliesSequence", sequenceName = "SEQ_REPLIES", allocationSize = 1)
    private int replyNo;

    private String content;

    @Column(name = "reply_date")
    private Date replyDate;

    // 외래키

    // Member -> id
//    @ManyToOne
//    @JoinColumn(name = "id")
//    private Member id;

    // Comments -> commentId
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comments commentId;

}
