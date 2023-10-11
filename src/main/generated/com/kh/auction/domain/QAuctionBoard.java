package com.kh.auction.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuctionBoard is a Querydsl query type for AuctionBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuctionBoard extends EntityPathBase<AuctionBoard> {

    private static final long serialVersionUID = 200485950L;

    private static final PathInits INITS = PathInits.DIRECT2;

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

    public final QMember member;

    public QAuctionBoard(String variable) {
        this(AuctionBoard.class, forVariable(variable), INITS);
    }

    public QAuctionBoard(Path<? extends AuctionBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuctionBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuctionBoard(PathMetadata metadata, PathInits inits) {
        this(AuctionBoard.class, metadata, inits);
    }

    public QAuctionBoard(Class<? extends AuctionBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

