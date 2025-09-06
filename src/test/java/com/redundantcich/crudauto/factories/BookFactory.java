package com.redundantcich.crudauto.factories;


import com.redundantcich.crudauto.model.Book;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BookFactory {

    private static final String COUNTER_FILE = "book-counter.txt";
    private static AtomicInteger counter;

    static {
        counter = new AtomicInteger(readCounterFromFile());
    }

    public static Book createSimpleBook() {
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
