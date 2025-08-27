package com.assignment.stock_order_matching_java.db;

import com.assignment.stock_order_matching_java.model.Stock;
import com.assignment.stock_order_matching_java.search.StockSearch;

import java.util.Random;

public class InMemoryStore {
    private final StockSearch search = new StockSearch();

    public InMemoryStore() {}

    public StockSearch search() { return search; }

    // helper to generate N fake stocks (e.g., 1,000,000)
    public void generateStocks(int n) {
//        Stock s1 = new Stock("AAPL", "Apple Inc.");
//        Stock s2 = new Stock("AMZN", "Amazon");
//        Stock s3 = new Stock("TSLA", "Tesla");
//
//        search.add(s1);
//        search.add(s2);
//        search.add(s3);

        for (int i = 0; i < n; i++) {
            String id = "S" + i;
            String name = "Stock " + i;
            search.add(new Stock(id, name));
        }
    }
}
