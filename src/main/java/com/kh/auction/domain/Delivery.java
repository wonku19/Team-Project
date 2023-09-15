package com.kh.auction.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @Column(name = "delivery_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "deliverySequence")
    @SequenceGenerator(name = "deliverySequence", sequenceName = "SEQ_DELIVERY", allocationSize = 1)
    private int deliveryNo;

    @Column(name = "delivery_company")
    private String deliveryCompany;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "complete_date")
    private Date completeDate;


    @ManyToOne
    @JoinColumn(name="auction_no")
    private AuctionBoard auctionNo;


    @ManyToOne
    @JoinColumn(name="MEMBER_NO")
    private Member memberId;
}
