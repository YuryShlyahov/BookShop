package service;

import exception.BookNotFoundException;
import model.Book;
import model.Genre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Shelf {
    private List<Book> books; // — список книг на полке (пустой при создании)
    private final Genre genre;

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public Shelf(Genre genre) {
        this.genre = genre;
        this.books = new ArrayList<>();
    }

    public Genre getGenre() {
        return genre;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void printAllBooks(){
        for (Book book : books){
            System.out.println(book.getDescription());
        }
    }

    public int countReadBooks() {
        int counter = 0;
        for (Book book : books) {
            if (book.isRead()) {
                counter++;
            }
        }
        return counter;
    }

    public List<Book> findUnreadBooks() {
        List<Book>unreadBooks = new ArrayList<>();
        for (Book book : books) {
            if (!book.isRead()) {
                unreadBooks.add(book);
            }
        }
        return unreadBooks;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (Book book : books) {
            totalPrice += book.getPrice();
        }
        return totalPrice;
    }

    public Book findBook(String title) {
        for (Book book : books) {
            if(book.getTitle().equalsIgnoreCase(title)){
                return book;
            }
        }
        throw new BookNotFoundException("Книга '" + title + "' не найдена");
    }

    public List<Book> findBooksByDate(LocalDate addedDate) {
        ArrayList<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if(book.getAddedDate().equals(addedDate)){
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public List<Book> findAuthor(String author) {
        List<Book> authorBooks = new ArrayList<>();
        for (Book book : books) {
            if(book.getAuthor().equalsIgnoreCase(author)){
                authorBooks.add(book);
            }
        }
        return authorBooks;
    }
    public List<String> findAllAuthors(){
        List<String> authors = new ArrayList<>();
        for (Book book : books){
            if (!authors.contains(book.getAuthor())){
                authors.add(book.getAuthor());
            }
        }
        return authors;
    }
    public void sortByTitle(){
        books.sort(Comparator
            .comparing(Book::getTitle)
            .thenComparing(Book::getAuthor)
        );
    }

    public void sortByDate(){
        books.sort(Comparator
                .comparing(Book::getAddedDate)
                .thenComparing(Book::getTitle)
                .thenComparing(Book::getAuthor)
        );
    }

    public List<Book> findPurchasedBooks(){
        List<Book> purchasedBooks = new ArrayList<>();
        for (Book book : books){
            if(book.isPurchased()){
                purchasedBooks.add(book);
            }
        }
        return purchasedBooks;
    }

    public boolean removeBook(String title) {
        return books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }
}
