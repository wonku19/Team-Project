package com.kh.auction.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionItemDetails{

    @Id
    @Column(name="auction_item_details")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "detailsSequence")
    @SequenceGenerator(name="detailsSequence", sequenceName = "SEQ_DETAILS", allocationSize = 1)
    private int itemNo;

    @Column(name="item_name")
    private String itemName;
    @Column(name="item_desc")
    private String itemDesc;
    @Column(name="starting_price")
    private int startingPrice;
    @Column(name="current_price")
    private int currentPrice;
    @Column(name="item_img")
    private String itemImg;

//    @ManyToOne
//    @JoinColumn(name="auction_no")
//    private AuctionBoard auctionNo;
}
