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
@Table(name = "POINT")
public class Point {

    @Id
    @Column(name = "point_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pointSql") // GenerationType을 SEQUENCE로 변경
    @SequenceGenerator(name = "pointSql", sequenceName = "SEQ_POINT", allocationSize = 1) // sequenceName을 "AUCTION_NO_SEQ"로 변경
    private int pointno; // 포인트 리스트 번호

    @Column(name = "add_point")
    private int addPoint; // 추가된 포인트

    @Column(name = "add_Data")
    private Date addData; // 결제 날짜

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member memberId;

}
