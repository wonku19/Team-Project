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
    @SequenceGenerator(name = "categorySeq", sequenceName = "SEQ_MAIN_CATEGORY", allocationSize = 1)
    private int categoryNo;

    @Column(name = "category_name")
    private String categoryName;
}
