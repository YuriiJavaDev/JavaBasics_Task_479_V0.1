# Digital Catalog: Multi-Criteria Sorting (JavaBasics_Task_479_V0.1)

## 📖 Description
In sophisticated systems, a single sorting criterion is often insufficient. This project demonstrates **Natural Ordering** in Java by implementing the `Comparable<Book>` interface. We simulate a library catalog that prioritizes chronological order (`publicationYear`). To handle books from the same year, a secondary alphabetical sort (`bookTitle`) is applied. This "chained" comparison logic ensures a deterministic and predictable order in any collection, which is essential for professional data management.

## 📋 Requirements Compliance
- **Natural Ordering**: Implemented `Comparable<Book>` interface directly within the model class.
- **Primary Sorting**: Developed logic to compare books by `publicationYear` in ascending order.
- **Secondary Sorting**: Implemented alphabetical fallback for books with identical years.
- **Automated Sorting**: Used `Collections.sort()` to verify that the natural order is correctly interpreted by Java's utility classes.

## 🚀 Architectural Stack
- Java 8+ (Interfaces, Collections Framework)

## 🏗️ Implementation Details
- **Book**: Model class defining its own comparison rules.
- **LibraryCatalogApp**: Application to test and display the sorted results.

## 📋 Expected result
```text
Sorting Catalog...
1. Compilers (1990)
2. Algorithms (2000)
3. Data Structures (2000)
```

## 💻 Code Example

Project Structure:

    JavaBasics_Task_479/
    ├── src/
    │   └── com/yurii/pavlenko/
    │                 ├── app/
    │                 │   └── LibraryCatalogApp.java
    │                 └── library/
    │                     └── models/
    │                         └── Book.java
    ├── LICENSE
    ├── TASK.md
    ├── THEORY.md
    └── README.md

Code
```java
package com.yurii.pavlenko.app;

import com.yurii.pavlenko.library.models.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LibraryCatalogApp {

    public static void main(String[] args) {
        List<Book> catalog = new ArrayList<>();

        catalog.add(new Book("Compilers", 1990));
        catalog.add(new Book("Data Structures", 2000));
        catalog.add(new Book("Algorithms", 2000));

        System.out.println("Sorting Catalog...");

        Collections.sort(catalog);

        int position = 1;
        for (Book book : catalog) {
            System.out.println(position++ + ". " + book);
        }
    }
}
```
```java
package com.yurii.pavlenko.library.models;

public class Book implements Comparable<Book> {
    private final String bookTitle;
    private final int publicationYear;

    public Book(String bookTitle, int publicationYear) {
        this.bookTitle = bookTitle;
        this.publicationYear = publicationYear;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    @Override
    public int compareTo(Book other) {

        int yearComparison = Integer.compare(this.publicationYear, other.publicationYear);

        if (yearComparison != 0) {
            return yearComparison;
        } else {
            return this.bookTitle.compareTo(other.bookTitle);
        }
    }

    @Override
    public String toString() {
        return bookTitle + " (" + publicationYear + ")";
    }
}
```

## ⚖️ License
This project is licensed under the **MIT License**.

Copyright (c) 2026 Yurii Pavlenko

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files...

License: [MIT](LICENSE)
