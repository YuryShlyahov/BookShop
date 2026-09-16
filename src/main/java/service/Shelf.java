package service;

import exception.BookNotFoundException;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class Shelf<T extends Book> {
    private final List<T> books; // — список книг на полке (пустой при создании)
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
        if (book == null) {
            throw new IllegalArgumentException("Книга не может быть null");
        }
        if (books.contains(book)) {
            throw new IllegalStateException("Такая книга уже есть на полке");
        }
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
        return filterBooks(books, book -> !book.isRead());
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
        return filterBooks(books, book -> book.getAddedDate().equals(addedDate));
    }

    public List<T> findAuthor(String author) {
        return filterBooks(books, book -> book.getAuthor().equalsIgnoreCase(author));
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
        return filterBooks(books, Book::isPurchased);
    }

    public boolean removeBook(String title) {
        return books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    public List<T> filterBooks(List<T> books, Predicate<T> predicate) {
        List<T> filteredBooks = new ArrayList<>();
        for (T book : books) {
            if (predicate.test(book)) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

    public List<EBook> findEbooks(){
        List<EBook> eBooks = new ArrayList<>();
        for(T book : books){
            if(book instanceof EBook){
                eBooks.add((EBook) book);
            }
        }
        return eBooks;
    }

    public double findEbooksSize(){
        double size = 0;
        for (EBook eBook : findEbooks()){
            size += eBook.getFileSize();
        }
        return size;
    }

    public List<AudioBook> findAudioBooks(){
        List<AudioBook> audioBooks = new ArrayList<>();
        for(T book : books){
            if(book instanceof AudioBook){
                audioBooks.add((AudioBook) book);
            }
        }
        return audioBooks;
    }

    public int findAudioBooksDuration(){
        int duration = 0;
        for (AudioBook audioBook : findAudioBooks()){
            duration += audioBook.getDuration();
        }
        return duration;
    }

    public List<PaperBook> findPaperBooks(){
        List<PaperBook> paperBooks = new ArrayList<>();
        for(T book : books){
            if(book instanceof PaperBook){
                paperBooks.add((PaperBook) book);
            }
        }
        return paperBooks;
    }
}
