package com.assignment.stock_order_matching_java;

import com.assignment.stock_order_matching_java.db.InMemoryStore;
import com.assignment.stock_order_matching_java.engine.MatchingEngine;
import org.springframework.stereotype.Component;

@Component
public class ExchangeService {
    private final MatchingEngine engine = new MatchingEngine();
    private final InMemoryStore store = new InMemoryStore();

    public ExchangeService() {
        store.generateStocks(100_000); // demo, scale to 1M if needed
    }

    public MatchingEngine getEngine() { return engine; }
    public InMemoryStore getStore() { return store; }
}
