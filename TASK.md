### As a diligent librarian, you're creating an advanced digital catalog for your extensive collection. You need a sophisticated way to sort books: first by their publication year, and then, for books published in the same year, by their title in alphabetical order.

#### - Your first step is to define a Book class. Each Book object should encapsulate its bookTitle (a string value) and publicationYear (an integer). To incorporate this multi-criteria sorting directly into the Book class, you must enable it by implementing the Comparable<Book> interface. Inside the compareTo method, implement your sorting logic: first, sort the books by publicationYear in ascending order (oldest first). If two books have the same publicationYear, then only then compare their bookTitles in alphabetical order.

#### - Now let's populate your catalog for testing. In your main method, create a dynamic list capable of storing Book objects. Add three different Book records to this list. It's important that two of these books have the same publicationYear but different bookTitles so you can test your secondary sorting rule. Once your list is ready, apply your sorting magic. Finally, iterate through your perfectly ordered list and display the bookTitle of each book, one by one, confirming the impeccable organization of your catalog.

```java

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LibraryCatalogApp {
    public static void main(String[] args) {
        // Create a dynamic list to store books
        List<Book> catalog = new ArrayList<>();

        // Add three books; Two books have the same publication year but different titles.
        catalog.add(new Book("Compilers", 1990));
        catalog.add(new Book("Algorithms", 2000));
        catalog.add(new Book("Data Structures", 2000));

        // Sort the list according to the compareTo logic in the Book class.
        Collections.sort(catalog);

        // Print the book titles in order after sorting.
        for (Book book : catalog) {
            System.out.println(book.getBookTitle());
        }
    }
}
```
