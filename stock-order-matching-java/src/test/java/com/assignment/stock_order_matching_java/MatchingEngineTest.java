package com.assignment.stock_order_matching_java;

import com.assignment.stock_order_matching_java.engine.MatchingEngine;
import com.assignment.stock_order_matching_java.model.Side;
import com.assignment.stock_order_matching_java.model.Trade;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MatchingEngineTest {

    @Test
    void testExactOrderMatch() {
        MatchingEngine eng = new MatchingEngine();
        var buy = eng.place(Side.BUY, "AAPL", 10_000, 1_000);  // $100.00
        var sell = eng.place(Side.SELL, "AAPL", 10_000, 1_000);
        List<Trade> trades = eng.trades();
        assertFalse(trades.isEmpty(), "Trades should exist");
        assertEquals(0, buy.getRemaining());
        assertEquals(0, sell.getRemaining());
    }

    @Test
    void testPartialOrderMatch() {
        MatchingEngine eng = new MatchingEngine();
        var buy = eng.place(Side.BUY, "AAPL", 10_000, 1_000);
        var sell = eng.place(Side.SELL, "AAPL", 10_000, 600);
        assertEquals(400, buy.getRemaining(), "BUY remaining should be 400");
        assertEquals(0, sell.getRemaining(), "SELL should be fully filled");
    }

    @Test
    void testPriceTimePriority() throws InterruptedException {
        MatchingEngine eng = new MatchingEngine();
        // two sells at same price; the earlier one should fill first
        var sell1 = eng.place(Side.SELL, "AAPL", 10_000, 300);
        Thread.sleep(1); // ensure timestamp ordering
        var sell2 = eng.place(Side.SELL, "AAPL", 10_000, 300);
        var buy = eng.place(Side.BUY, "AAPL", 10_000, 400);
        // after matching, sell1 should be fully filled (300), sell2 partially (100 left)
        assertEquals(0, sell1.getRemaining());
        assertEquals(200, sell2.getRemaining());
        assertEquals(0, buy.getRemaining());
    }

    @Test
    void testBulkOrdering10000Pairs() {
        MatchingEngine eng = new MatchingEngine();
        int N = 10_000;
        for (int i = 0; i < N; i++) {
            eng.place(Side.BUY, "AAPL", 10_000, 100);
            eng.place(Side.SELL, "AAPL", 10_000, 100);
        }
        // each pair should result in one trade of 100
        assertTrue(eng.trades().size() >= N, "All orders should be matched in bulk ordering.");
    }
}
