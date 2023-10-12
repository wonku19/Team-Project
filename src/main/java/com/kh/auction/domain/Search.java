package com.kh.auction.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    @Id
    @Column(name = "search_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "searchSeqGen")
    @SequenceGenerator(name = "searchSeqGen", sequenceName = "SEARCH_SEQ", allocationSize = 1)
    private int searchSeq;

    @Column(name = "category_seq")
    private int categorySeq;
}