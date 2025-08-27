package com.assignment.stock_order_matching_java;//package com.assignment.stock_order_matching_java;

import com.assignment.stock_order_matching_java.engine.MatchingEngine;
import com.assignment.stock_order_matching_java.model.Order;
import com.assignment.stock_order_matching_java.model.Side;
import com.assignment.stock_order_matching_java.model.Trade;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ExchangeController {

    private final MatchingEngine engine = new MatchingEngine();

    // place new order
    @PostMapping("/orders")
    public Order place(@RequestBody Map<String, Object> req) {
        Side side = Side.valueOf(((String) req.get("side")).toUpperCase());
        String stockId = (String) req.get("stockId");
        long price = ((Number) req.get("price")).longValue();
        long qty = ((Number) req.get("quantity")).longValue();
        return engine.place(side, stockId, price, qty);
    }

    // get order book snapshot
    @GetMapping("/orderbook/{stockId}")
    public Map<String, List<Order>> orderBook(@PathVariable String stockId) {
        var book = engine.bookOf(stockId);
        return Map.of(
                "bids", book.topOfBook(10).stream().filter(o -> o.getSide()==Side.BUY).toList(),
                "asks", book.topOfBook(10).stream().filter(o -> o.getSide()==Side.SELL).toList()
        );
    }

    // get trade history
    @GetMapping("/trades/{stockId}")
    public List<Trade> trades(@PathVariable String stockId) {
        return engine.trades().stream()
                .filter(t -> t.stockId().equals(stockId))
                .toList();
    }
}
