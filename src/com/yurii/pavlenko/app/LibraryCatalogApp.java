package com.yurii.pavlenko.app;

import com.yurii.pavlenko.library.models.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Orchestrates the sorting and display of the digital catalog.
 */
public class LibraryCatalogApp {

    public static void main(String[] args) {
        List<Book> catalog = new ArrayList<>();

        // Adding books according to task requirements
        catalog.add(new Book("Compilers", 1990));
        catalog.add(new Book("Data Structures", 2000));
        catalog.add(new Book("Algorithms", 2000));

        System.out.println("Sorting Catalog...");

        // Magic happens here: Collections.sort calls Book.compareTo internally
        Collections.sort(catalog);

        // Display results with index and context
        int position = 1;
        for (Book book : catalog) {
            System.out.println(position++ + ". " + book);
        }
    }
}