package com.kh.auction.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    @Id
    @Column(name = "category_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "categorySeq")
    @SequenceGenerator(name = "categorySeq", sequenceName = "CATEGORY_SEQ", allocationSize = 1)
    private int categoryNo;

    @Column(name = "category_name")
    private String categoryName;
//    @ManyToOne //  Channel 엔티티와 Member 엔티티를 다대일 관계로 설정
//    @JoinColumn(name="auction_no") // 외래키 생성 or Member 엔티티의 기본키와 매핑
//    private Auction auction;
}
