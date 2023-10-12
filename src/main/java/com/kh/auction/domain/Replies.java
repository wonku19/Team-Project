package com.kh.auction.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Replies {

    @Id
    @Column(name = "reply_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "replySequence")
    @SequenceGenerator(name = "replySequence", sequenceName = "SEQ_REPLY", allocationSize = 1)
    private int replyNo;

    @Column(name = "reply_content")
    private  String replyContent;

    @Column(name = "reply_date")
    private Date replyDate;

    @ManyToOne
    @JoinColumn(name="auction_no")
    private AuctionBoard auctionNo;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member memberId;

    @ManyToOne
    @JoinColumn(name="comment_no")
    private Comments commentNo;

}
