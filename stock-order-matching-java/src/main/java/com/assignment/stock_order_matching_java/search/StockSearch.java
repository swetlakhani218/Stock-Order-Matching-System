package com.assignment.stock_order_matching_java.search;

import com.assignment.stock_order_matching_java.model.Stock;

import java.util.*;

public class StockSearch {
    private final Map<String, Stock> byId = new HashMap<>();
    private final Map<String, String> nameToId = new HashMap<>();
    private final Trie trie = new Trie();

    public void add(Stock s) {
        byId.put(s.getId(), s);
        nameToId.put(s.getName().toLowerCase(), s.getId());
        trie.insert(s.getName(), s.getId());
    }

    public Stock getById(String id) { return byId.get(id); }

    public Optional<Stock> getByExactName(String name) {
        String id = nameToId.get(name.toLowerCase());
        return id == null ? Optional.empty() : Optional.ofNullable(byId.get(id));
    }

    public List<Stock> prefixByName(String prefix, int limit) {
        List<String> ids = trie.prefix(prefix, limit);
        List<Stock> out = new ArrayList<>(ids.size());
        for (String id : ids) {
            Stock s = byId.get(id);
            if (s != null) out.add(s);
        }
        return out;
    }

    public int size() { return byId.size(); }
}

