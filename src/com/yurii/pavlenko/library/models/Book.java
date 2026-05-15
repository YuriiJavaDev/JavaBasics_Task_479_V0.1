package com.yurii.pavlenko.library.models;

/**
 * Represents a book with self-contained sorting logic (Natural Order).
 */
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

    /**
     * Compares this book with another for sorting.
     * Logic: Primary - Year, Secondary - Title.
     */
    @Override
    public int compareTo(Book other) {
        // Compare by year
        int yearComparison = Integer.compare(this.publicationYear, other.publicationYear);

        // If years are different, return result.
        // If years are same (result is 0), compare by title.
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