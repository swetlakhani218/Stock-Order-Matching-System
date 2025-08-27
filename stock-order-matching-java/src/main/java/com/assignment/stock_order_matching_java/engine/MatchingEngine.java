package com.assignment.stock_order_matching_java.engine;

import com.assignment.stock_order_matching_java.model.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MatchingEngine {
    private final Map<String, OrderBook> books = new ConcurrentHashMap<>();
    private final List<Trade> tradeLog = Collections.synchronizedList(new ArrayList<>());

    public OrderBook bookOf(String stockId) {
//        if (!books.containsKey(stockId)) {
//            books.put(stockId, new OrderBook(stockId));
//        }
//        return books.get(stockId);
        return books.computeIfAbsent(stockId, OrderBook::new);
    }

    public Order place(Side side, String stockId, long priceCents, long qty) {
        Order o = new Order(side, stockId, priceCents, qty);
        OrderBook ob = bookOf(stockId);
        ob.place(o);
        // append new trades from that book to global log
        synchronized (tradeLog) {
            tradeLog.addAll(ob.getTrades().subList(tradeLog.size(), ob.getTrades().size()));
        }
        return o;
    }

    public List<Trade> trades() { return List.copyOf(tradeLog); }
}
