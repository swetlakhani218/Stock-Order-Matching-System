package com.assignment.stock_order_matching_java.model;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public class Order {
    private static final AtomicLong ID_SEQ = new AtomicLong(1);

    private final long id;
    private final Side side;
    private final String stockId;
    private final long price;      // store as integer cents for precision
    private final long quantity;   // original quantity
    private long remaining;        // remaining to fill
    private final long timestampNanos; // for strict time priority (monotonic-ish)

    public Order(Side side, String stockId, long price, long quantity) {
        this.id = ID_SEQ.getAndIncrement();
        this.side = side;
        this.stockId = stockId;
        this.price = price;
        this.quantity = quantity;
        this.remaining = quantity;
        // combine epoch seconds + nano offset to preserve ordering
        this.timestampNanos = Instant.now().getEpochSecond() * 1_000_000_000L + System.nanoTime()%1_000_000_000L;
    }

    public long getId() { return id; }
    public Side getSide() { return side; }
    public String getStockId() { return stockId; }
    public long getPrice() { return price; }
    public long getQuantity() { return quantity; }
    public long getRemaining() { return remaining; }
    public long getTimestampNanos() { return timestampNanos; }

    public void fill(long qty) {
        if (qty < 0 || qty > remaining) throw new IllegalArgumentException("Bad fill qty");
        remaining -= qty;
    }

    public boolean isFilled() { return remaining == 0; }

    @Override
    public String toString() {
        return "Order{id=" + id + ", " + side + " " + stockId + " @" + price +
                " qty=" + quantity + " rem=" + remaining + "}";
    }
}
