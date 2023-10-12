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
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "interestSequence")
    @SequenceGenerator(name = "interestSequence", sequenceName = "SEQ_INTEREST", allocationSize = 1)
    private int postNo;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member memberId;

    @ManyToOne
    @JoinColumn(name="auction_no")
    private AuctionBoard auctionNo;

}
