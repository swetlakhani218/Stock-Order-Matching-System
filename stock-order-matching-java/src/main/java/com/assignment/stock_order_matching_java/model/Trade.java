package com.assignment.stock_order_matching_java.model;

import java.time.Instant;

public record Trade(
        long buyOrderId,
        long sellOrderId,
        String stockId,
        long price,       // execution price (aggressor meets book price)
        long quantity,
        Instant ts
) {}
