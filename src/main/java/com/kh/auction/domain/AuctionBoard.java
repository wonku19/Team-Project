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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auctionSeq") // GenerationType을 SEQUENCE로 변경
    @SequenceGenerator(name = "auctionSeq", sequenceName = "SEQ_AUCTION", allocationSize = 1) // sequenceName을 "AUCTION_NO_SEQ"로 변경
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
    @Column(name = "auction_smoney")
    private int auctionSMoney; // 경매 시작가
    @Column(name = "auction_emoney")
    private int auctionEMoney; // 경매 최소 입찰가
    @Column(name = "auction_gmoney")
    private int auctionGMoney; // 경매 즉시 구매가
    @Column(name = "auction_nowbuy_y_n")
    private char auctionNowbuy; // 경매 즉시 구매 여부
    @Column(name = "auction_end_date")
    private Date auctionEndDate;

    @Column(name = "auction_check_no")
    private int auctionCheckNo; // 조회수

    @Column(name = "auction_attend_no")
    private int auctionAttendNo; // 입찰 참여 인원수

    @ManyToOne
    @JoinColumn(name="category_no")
    private Category category;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member memberId;

}
