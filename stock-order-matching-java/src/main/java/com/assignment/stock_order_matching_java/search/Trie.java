package com.assignment.stock_order_matching_java.search;

import java.util.*;

public class Trie {
    static class Node {
        Map<Character, Node> next = new HashMap<>();
        List<String> payload = new ArrayList<>(); // store stock IDs for names that pass through/end here
    }

    private final Node root = new Node();

    public void insert(String text, String stockId) {
        Node cur = root;
        String s = text.toLowerCase();
        for (char c : s.toCharArray()) {
            cur = cur.next.computeIfAbsent(c, k -> new Node());
            cur.payload.add(stockId); // for prefix matches collect IDs
        }
    }

    public List<String> prefix(String prefix, int limit) {
        Node cur = root;
        String s = prefix.toLowerCase();
        for (char c : s.toCharArray()) {
            cur = cur.next.get(c);
            if (cur == null) return List.of();
        }
        if (cur.payload.size() <= limit) return new ArrayList<>(cur.payload);
        return new ArrayList<>(cur.payload.subList(0, limit));
    }
}

