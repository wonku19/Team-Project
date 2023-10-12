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
public class Comments {

    @Id
    @Column(name = "comment_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "commentSequence")
    @SequenceGenerator(name = "commentSequence", sequenceName = "SEQ_COMMENT", allocationSize = 1)
    private int commentNo;

    @Column(name="comment_content")
    private String content;

    @Column(name = "comment_date")
    private Date commentDate;

    @ManyToOne
    @JoinColumn(name="auction_no")
    private AuctionBoard auctionNo;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member memberId;

}
