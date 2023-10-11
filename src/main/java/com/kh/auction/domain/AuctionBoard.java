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
public class AuctionBoard {

    @Id
    @Column(name = "auction_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "auctionNoSeq")
    @SequenceGenerator(name = "auctionNoSeq", sequenceName = "SEQ_AUCTION", allocationSize = 1)
    private int auctionNo;

    @Column(name = "auction_title")
    private String auctionTitle;
    @Column(name = "auction_img")
    private String auctionImg;
    @Column(name = "auction_date")
    private Date auctionDate;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "item_desc")
    private String itemDesc;
    @Column(name = "current_price")
    private int currentPrice;
    @Column(name = "auction_emoney")
    private int auctionEMoney; // 경매 최소 입찰가
    @Column(name = "auction_gmoney")
    private int auctionGMoney; // 경매 즉시 구매가
    @Column(name = "auction_nowbuy_y_n")
    private char auctionNowbuy; // 경매 즉시 구매 여부
    @Column(name = "auction_end_date")
    private Date auctionEndDate;

}
