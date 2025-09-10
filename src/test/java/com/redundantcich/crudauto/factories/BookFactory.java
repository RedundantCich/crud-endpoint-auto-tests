package com.redundantcich.crudauto.factories;


import com.redundantcich.crudauto.model.Book;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class BookFactory {

    private static final String COUNTER_FILE = "book-counter.txt";
    private static AtomicInteger counter;

    static {
        counter = new AtomicInteger(readCounterFromFile());
    }

    public static Book createRandomBook() {
        int id = counter.getAndIncrement();
        writeCounterToFile(counter.get());
        String name = "Book" + id;
        String author = "Author" + id;
        String publication = "Penguin" + id;
        String category = "Programming" + id;
        int pages = 100 + id;
        float price = (float) (10.99 + id);
        return new Book(null, name, author, publication, category, pages, price);
    }

    public static Book createCustomBook(
            String name, String author, String publication, String category, int pages, float price) {
        return new Book(null, name, author, publication, category, pages, price);
    }

    public static Book createCustomBookFromStep(
            String name, String author, String publication, String category, String price, String pages) {
        return new Book(null, name, author, publication, category, Integer.parseInt(pages), Float.parseFloat(price));
    }

    public static Map<String, Object> createCustomInvalidPagesBookFromStep(
            String pages) {
        Map<String, Object> invalidBook = new HashMap<>();
        invalidBook.put("name", "Fail Page Book");
        invalidBook.put("author", "Webby");
        invalidBook.put("publication", "Netty");
        invalidBook.put("category", "Online");
        invalidBook.put("price", 7.99f);
        invalidBook.put("pages", pages);
        return invalidBook;
    }

    public static Map<String, Object> createCustomInvalidPriceBookFromStep(
            String price) {
        Map<String, Object> invalidBook = new HashMap<>();
        invalidBook.put("name", "Fail Price Book");
        invalidBook.put("author", "Anonymous");
        invalidBook.put("publication", "Freebies");
        invalidBook.put("category", "Offline");
        invalidBook.put("price", price);
        invalidBook.put("pages", 200);
        return invalidBook;
    }

    private static int readCounterFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(COUNTER_FILE))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            return 1;
        }
    }

    private static void writeCounterToFile(int value) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COUNTER_FILE))) {
            writer.write(String.valueOf(value));
        } catch (IOException e) {
            throw new RuntimeException("Failed to persist book counter", e);
        }
    }
}
