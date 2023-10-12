package com.kh.auction.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuctionBoard is a Querydsl query type for AuctionBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuctionBoard extends EntityPathBase<AuctionBoard> {

    private static final long serialVersionUID = 200485950L;

    public static final QAuctionBoard auctionBoard = new QAuctionBoard("auctionBoard");

    public final DateTimePath<java.util.Date> auctionDate = createDateTime("auctionDate", java.util.Date.class);

    public final NumberPath<Integer> auctionEMoney = createNumber("auctionEMoney", Integer.class);

    public final DateTimePath<java.util.Date> auctionEndDate = createDateTime("auctionEndDate", java.util.Date.class);

    public final NumberPath<Integer> auctionGMoney = createNumber("auctionGMoney", Integer.class);

    public final StringPath auctionImg = createString("auctionImg");

    public final NumberPath<Integer> auctionNo = createNumber("auctionNo", Integer.class);

    public final ComparablePath<Character> auctionNowbuy = createComparable("auctionNowbuy", Character.class);

    public final StringPath auctionTitle = createString("auctionTitle");

    public final NumberPath<Integer> currentPrice = createNumber("currentPrice", Integer.class);

    public final StringPath itemDesc = createString("itemDesc");

    public final StringPath itemName = createString("itemName");

    public QAuctionBoard(String variable) {
        super(AuctionBoard.class, forVariable(variable));
    }

    public QAuctionBoard(Path<? extends AuctionBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuctionBoard(PathMetadata metadata) {
        super(AuctionBoard.class, metadata);
    }

}

