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
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "commentSequence")
    @SequenceGenerator(name = "commentSequence", sequenceName = "SEQ_COMMENTS", allocationSize = 1)
    private int commentId;

    @Column(name="comment_content")
    private String content;

    @Column(name = "comment_date")
    private Date commentDate;

    // 외래키
    // Member -> id
    @ManyToOne
    @JoinColumn(name = "id")
    private Member id;

    // Auction -> auction
    @ManyToOne
    @JoinColumn(name = "auction_no")
    private AuctionBoard auctionNo;

}
