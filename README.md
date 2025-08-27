📈Problem Statement:

In stock markets, buyers and sellers place orders at different prices and quantities. The challenge is to match BUY and SELL orders efficiently while respecting rules like:
1. Best price first (highest BUY price vs lowest SELL price).
2. Timestamp priority (first-come-first-serve if prices are the same).
3. Partial matching (if full order can’t be matched, match what’s possible).
Our system also supports searching through 1 million stock records efficiently using optimized data structures.

💡Approach:

1. Order Matching: Implemented using priority queues where BUY orders are sorted by highest price (and earliest timestamp) and SELL orders by lowest price (and earliest timestamp).

2. Partial Matching: Remaining unmatched orders are stored back in the order book.

3. Stock Search: Implemented with Tries (for prefix search), Hash Maps (for direct access), and Trees (for price ordering).

4. Scalability: Designed to process thousands of orders per second.

⚙️ Setup Instructions:

1. Clone the repository:

    git clone https://github.com/swetlakhani218/Stock-Order-Matching-System.git

    cd Stock-Order-Matching-System

2. Open the project in IntelliJ IDEA / Eclipse / any Java IDE.

3. Build the project using Maven.

   mvn clean install

4. Run the application:

   mvn spring-boot:run

📋 Prerequisites:

1. Java 17+
2. Spring Boot 3+
3. Maven installed
4. IDE (IntelliJ/Eclipse/VS Code with Java plugin)

▶️ Steps to Run the Project

1. Start the Spring Boot server.

2. Use REST API endpoints to:

    Place BUY/SELL orders. 
    View order book.
    Search stocks by ID or name.
    Retrieve historical trades.

3. (Optional) Run unit tests:

    mvn test

🔍 Explanation of Complex Logic

1. Order Matching Algorithm

    BUY orders → Max-Heap (highest price first).
    SELL orders → Min-Heap (lowest price first).
    Match when BUY price ≥ SELL price.
    If quantities differ, partially match and keep the remainder in the book.

2. Stock Search

    Trie → Fast prefix search by stock name.
    HashMap → O(1) lookup by stock ID.
    Tree → Keep sorted stock prices for efficient range queries.
