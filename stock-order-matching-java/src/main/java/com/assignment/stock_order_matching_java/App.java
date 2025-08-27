package com.assignment.stock_order_matching_java;

import com.assignment.stock_order_matching_java.db.InMemoryStore;
import com.assignment.stock_order_matching_java.engine.MatchingEngine;
import com.assignment.stock_order_matching_java.model.Side;
import com.assignment.stock_order_matching_java.model.Trade;
import com.assignment.stock_order_matching_java.search.StockSearch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
//        InMemoryStore store = new InMemoryStore();
//        // demo: 100k stocks (increase to 1,000,000 as needed)
//        store.generateStocks(100_000);
//        StockSearch search = store.search();
//        MatchingEngine engine = new MatchingEngine();
//
//        System.out.println("Stock Order Matching CLI (type 'help')");
//        Scanner sc = new Scanner(System.in);
//        while (true) {
//            System.out.print("> ");
//            String line = sc.nextLine().trim();
//            if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) break;
//            if (line.equalsIgnoreCase("help")) {
//                System.out.println("commands:");
//                System.out.println("  search <prefix>                 - prefix search stocks");
//                System.out.println("  buy <stockId> <priceCents> <qty>");
//                System.out.println("  sell <stockId> <priceCents> <qty>");
//                System.out.println("  trades                          - list trades");
//                System.out.println("  exit");
//                continue;
//            }
//            try {
//                if (line.startsWith("search ")) {
//                    String prefix = line.substring(7).trim();
//                    search.prefixByName(prefix, 10).forEach(s ->
//                            System.out.println(s.getId() + " :: " + s.getName()));
//                } else if (line.startsWith("buy ")) {
//                    String[] tok = line.split("\\s+");
//                    engine.place(Side.BUY, tok[1], Long.parseLong(tok[2]), Long.parseLong(tok[3]));
//                    System.out.println("OK");
//                } else if (line.startsWith("sell ")) {
//                    String[] tok = line.split("\\s+");
//                    engine.place(Side.SELL, tok[1], Long.parseLong(tok[2]), Long.parseLong(tok[3]));
//                    System.out.println("OK");
//                } else if (line.equals("trades")) {
//                    List<Trade> t = engine.trades();
//                    t.forEach(System.out::println);
//                } else {
//                    System.out.println("Unknown. Type 'help'.");
//                }
//            } catch (Exception e) {
//                System.out.println("ERR: " + e.getMessage());
//            }
//        }
//        System.out.println("bye.");
    }
}
