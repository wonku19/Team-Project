package com.kh.auction.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuctionItemDetails is a Querydsl query type for AuctionItemDetails
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuctionItemDetails extends EntityPathBase<AuctionItemDetails> {

    private static final long serialVersionUID = -1557995769L;

    public static final QAuctionItemDetails auctionItemDetails = new QAuctionItemDetails("auctionItemDetails");

    public final NumberPath<Integer> currentPrice = createNumber("currentPrice", Integer.class);

    public final StringPath itemDesc = createString("itemDesc");

    public final StringPath itemImg = createString("itemImg");

    public final StringPath itemName = createString("itemName");

    public final NumberPath<Integer> itemNo = createNumber("itemNo", Integer.class);

    public final NumberPath<Integer> startingPrice = createNumber("startingPrice", Integer.class);

    public QAuctionItemDetails(String variable) {
        super(AuctionItemDetails.class, forVariable(variable));
    }

    public QAuctionItemDetails(Path<? extends AuctionItemDetails> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuctionItemDetails(PathMetadata metadata) {
        super(AuctionItemDetails.class, metadata);
    }

}

