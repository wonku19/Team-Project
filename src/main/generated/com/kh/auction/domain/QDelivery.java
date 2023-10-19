package com.kh.auction.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDelivery is a Querydsl query type for Delivery
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDelivery extends EntityPathBase<Delivery> {

    private static final long serialVersionUID = 1527044943L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDelivery delivery = new QDelivery("delivery");

    public final QAuctionBoard auctionNo;

    public final DateTimePath<java.util.Date> completeDate = createDateTime("completeDate", java.util.Date.class);

    public final StringPath deliveryCompany = createString("deliveryCompany");

    public final NumberPath<Integer> deliveryNo = createNumber("deliveryNo", Integer.class);

    public final QMember memberId;

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public QDelivery(String variable) {
        this(Delivery.class, forVariable(variable), INITS);
    }

    public QDelivery(Path<? extends Delivery> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDelivery(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDelivery(PathMetadata metadata, PathInits inits) {
        this(Delivery.class, metadata, inits);
    }

    public QDelivery(Class<? extends Delivery> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auctionNo = inits.isInitialized("auctionNo") ? new QAuctionBoard(forProperty("auctionNo"), inits.get("auctionNo")) : null;
        this.memberId = inits.isInitialized("memberId") ? new QMember(forProperty("memberId")) : null;
    }

}

