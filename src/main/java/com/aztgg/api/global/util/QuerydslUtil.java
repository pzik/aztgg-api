package com.aztgg.api.global.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Sort;

public class QuerydslUtil {

    public static void addOrderBy(Class<?> classType, EntityPathBase<?> qEntity, JPAQuery<?> query, Sort sort) {
        for (Sort.Order order : sort) {
            PathBuilder pathBuilder = new PathBuilder(classType, qEntity.getMetadata().getName());
            query.orderBy(new OrderSpecifier<>(
                    order.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(order.getProperty())
            ));
        }
    }
}
