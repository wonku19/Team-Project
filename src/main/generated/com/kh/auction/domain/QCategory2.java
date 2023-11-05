package com.kh.auction.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategory2 is a Querydsl query type for Category2
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory2 extends EntityPathBase<Category2> {

    private static final long serialVersionUID = 1901924089L;

    public static final QCategory2 category2 = new QCategory2("category2");

    public final StringPath category2Name = createString("category2Name");

    public final NumberPath<Integer> category2No = createNumber("category2No", Integer.class);

    public QCategory2(String variable) {
        super(Category2.class, forVariable(variable));
    }

    public QCategory2(Path<? extends Category2> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory2(PathMetadata metadata) {
        super(Category2.class, metadata);
    }

}

