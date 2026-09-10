package service;

import exception.BookNotFoundException;
import model.Book;
import model.Genre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class Shelf<T extends Book> {
    private List<T> books; // — список книг на полке (пустой при создании)
    private final Genre genre;

    public List<T> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public Shelf(Genre genre) {
        this.genre = genre;
        this.books = new ArrayList<>();
    }

    public Genre getGenre() {
        return genre;
    }

    public void addBook(T book) {
        books.add(book);
    }

    public void printAllBooks() {
        for (T book : books) {
            System.out.println(book.getDescription());
        }
    }

    public int countReadBooks() {
        int counter = 0;
        for (T book : books) {
            if (book.isRead()) {
                counter++;
            }
        }
        return counter;
    }

    public List<T> findUnreadBooks() {
        List<T> unreadBooks = new ArrayList<>();
        for (T book : books) {
            if (!book.isRead()) {
                unreadBooks.add(book);
            }
        }
        return unreadBooks;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (T book : books) {
            totalPrice += book.getPrice();
        }
        return totalPrice;
    }

    public T findBook(String title) {
        for (T book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        throw new BookNotFoundException("Книга '" + title + "' не найдена");
    }

    public List<T> findBooksByDate(LocalDate addedDate) {
        ArrayList<T> foundBooks = new ArrayList<>();
        for (T book : books) {
            if (book.getAddedDate().equals(addedDate)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public List<T> findAuthor(String author) {
        List<T> authorBooks = new ArrayList<>();
        for (T book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                authorBooks.add(book);
            }
        }
        return authorBooks;
    }

    public List<String> findAllAuthors() {
        List<String> authors = new ArrayList<>();
        for (T book : books) {
            if (!authors.contains(book.getAuthor())) {
                authors.add(book.getAuthor());
            }
        }
        return authors;
    }

    public void sortByTitle() {
        books.sort(Comparator
                .comparing(T::getTitle)
                .thenComparing(T::getAuthor)
        );
    }

    public void sortByDate() {
        books.sort(Comparator
                .comparing(Book::getAddedDate)
                .thenComparing(Book::getTitle)
                .thenComparing(Book::getAuthor)
        );
    }

    public List<T> findPurchasedBooks() {
        List<T> purchasedBooks = new ArrayList<>();
        for (T book : books) {
            if (book.isPurchased()) {
                purchasedBooks.add(book);
            }
        }
        return purchasedBooks;
    }

    public boolean removeBook(String title) {
        return books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    public List<T> filterBooks(List<T> books, Predicate<T> predicate) {
        List<T> filteredBooks = new ArrayList<>();
        for (T book : books)
            if (predicate.test(book)) {
                filteredBooks.add(book);
            }
        return filteredBooks;
    }
}
