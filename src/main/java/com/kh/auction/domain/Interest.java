package com.kh.auction.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Interest {

    @Id
    @Column(name = "post_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "postSequence")
    @SequenceGenerator(name = "postSequence", sequenceName = "SEQ_POST", allocationSize = 1)
    private int postNo;

    // 외래키
    // Member -> id
    @ManyToOne
    @JoinColumn(name = "id")
    private Member id;


    // Auction -> auctionNo
    @ManyToOne
    @JoinColumn(name = "auction_no")
    private AuctionBoard auctionNo;

}
