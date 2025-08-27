package com.assignment.stock_order_matching_java.model;

public class Stock {
    private final String id;
    private final String name;

    public Stock(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getId() { return id; }
    public String getName() { return name; }
}

