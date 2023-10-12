package com.kh.auction.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainCategory {
    @Id
    @Column(name = "category_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "MainCategorySequence")
    @SequenceGenerator(name = "MainCategorySequence", sequenceName = "SEQ_MAIN_CATEGORY", allocationSize = 1)
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

}
