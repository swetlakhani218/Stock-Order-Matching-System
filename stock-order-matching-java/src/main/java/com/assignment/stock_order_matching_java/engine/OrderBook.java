package com.assignment.stock_order_matching_java.engine;

import com.assignment.stock_order_matching_java.model.*;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class OrderBook {
    private final String stockId;
    private final PriorityQueue<Order> bids; // highest price first, then earliest time
    private final PriorityQueue<Order> asks; // lowest price first, then earliest time
    private final List<Trade> trades = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    public OrderBook(String stockId) {
        this.stockId = stockId;
        Comparator<Order> bidCmp = Comparator
                .comparingLong(Order::getPrice).reversed()
                .thenComparingLong(Order::getTimestampNanos);
        Comparator<Order> askCmp = Comparator
                .comparingLong(Order::getPrice)
                .thenComparingLong(Order::getTimestampNanos);
        bids = new PriorityQueue<>(bidCmp);
        asks = new PriorityQueue<>(askCmp);
    }

    public Order place(Order order) {
        lock.lock();
        try {
            if (order.getSide() == Side.BUY) bids.add(order);
            else asks.add(order);
            match();
            return order;
        } finally {
            lock.unlock();
        }
    }

    private void match() {
        while (!bids.isEmpty() && !asks.isEmpty()) {
            Order buy = bids.peek();
            Order sell = asks.peek();
            if (buy.getPrice() < sell.getPrice()) break;

            long qty = Math.min(buy.getRemaining(), sell.getRemaining());

//            // trade executes at the price of the resting (book) order
//            long execPrice;
//            if (buy.getTimestampNanos() < sell.getTimestampNanos()) {
//                // BUY order was resting, SELL came later
//                execPrice = buy.getPrice();
//            } else {
//                // SELL order was resting, BUY came later
//                execPrice = sell.getPrice();
//            }

            long execPrice = sell.getTimestampNanos() <= buy.getTimestampNanos()
                    ? sell.getPrice() : sell.getPrice(); // price-time: here we execute at book price (sell)
            buy.fill(qty);
            sell.fill(qty);
            trades.add(new Trade(buy.getId(), sell.getId(), stockId, execPrice, qty, Instant.now()));

            if (buy.isFilled()) bids.poll();
            if (sell.isFilled()) asks.poll();
        }
    }

    public List<Trade> getTrades() { return Collections.unmodifiableList(trades); }

    public List<Order> topOfBook(int depth) {
        lock.lock();
        try {
            List<Order> out = new ArrayList<>();
            // snapshot by iterating PQ (O(n)), fine for debug/UI
            bids.stream().sorted(((PriorityQueue<Order>)bids).comparator()).limit(depth).forEach(out::add);
            asks.stream().sorted(((PriorityQueue<Order>)asks).comparator()).limit(depth).forEach(out::add);
            return out;
        } finally {
            lock.unlock();
        }
    }

    public int bidCount() { return bids.size(); }
    public int askCount() { return asks.size(); }
}

