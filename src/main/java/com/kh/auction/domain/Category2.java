package com.kh.auction.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="CATEGORY2")
public class Category2 {

    @Id
    @Column(name = "category2_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "category2Seq")
    @SequenceGenerator(name = "category2Seq", sequenceName = "SEQ_MAIN_CATEGORY2", allocationSize = 1)
    private Integer category2No;

    @Column(name = "category2_name")
    private String category2Name;
}
