package com.kh.auction.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionBoard {

    @Id
    @Column(name = "auction_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "auctionNoSeq")
    @SequenceGenerator(name = "auctionNoSeq", sequenceName = "AUCTION_NO_SEQ", allocationSize = 1)
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
    @Column(name = "auction_now_buy")
    private char auctionNowbuy; // 경매 즉시 구매 여부
    @Column(name = "auction_end_date")
    private Date auctionEndDate;

    @ManyToOne //  Channel 엔티티와 Member 엔티티를 다대일 관계로 설정
    @JoinColumn(name = "member_no") // 외래키 생성 or Member 엔티티의 기본키와 매핑
    private Member member;

    @ManyToOne // AuctionBoard와 Category 사이의 다대일 관계를 표현
    @JoinColumn(name = "category_id") // 카테고리 ID를 외래 키로 설정
    private Category category;

    @OneToMany(mappedBy = "auctionBoard", cascade = CascadeType.ALL) // AuctionBoard와 Comments 사이의 일대다 관계를 표현
    private List<Comments> comments;
}
