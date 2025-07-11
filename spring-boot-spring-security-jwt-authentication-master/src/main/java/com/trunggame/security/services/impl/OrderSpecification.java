package com.trunggame.security.services.impl;

import com.trunggame.models.Order;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {

    public static Specification<Order> serverEqual(String server) {
        return StringUtils.isEmpty(server) ? null : (root, query, builder) -> builder.equal(root.get("serverName"), server);
    }

    public static Specification<Order> customerId(Long id) {
        return id>0 ? null : (root, query, builder) -> builder.equal(root.get("customerId"), id);
    }

    public static Specification<Order> loginTypeEqual(String loginType) {
        return StringUtils.isEmpty(loginType) ? null : (root, query, builder) -> builder.equal(root.get("loginType"), loginType);
    }

    public static Specification<Order> codeLike(String code) {
        return StringUtils.isEmpty(code) ? null : (root, query, builder) -> builder.like(root.get("code"), "%" + code + "%");
    }

    public static Specification<Order> orderCodeEqual(String orderCode) {
        return StringUtils.isEmpty(orderCode) ? null : (root, query, builder) -> builder.equal(root.get("code"), orderCode);
    }
}
