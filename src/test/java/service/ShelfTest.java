package service;

import model.Book;
import model.Genre;
import model.PaperBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShelfTest{

    private Genre genre;
    private Book book;
    private Shelf<Book> shelf;

    @BeforeEach
    void setUp() {
        genre = Genre.DRAMA;
        book = new PaperBook("Книга", "Автор", 100, genre);
        shelf = new Shelf<>(genre);
    }

    @Test
    void addBook_shouldAddBookToList(){
        int sizeBefore = shelf.getBooks().size();
        shelf.addBook(book);
        assertEquals(sizeBefore + 1, shelf.getBooks().size());
        assertTrue(shelf.getBooks().contains(book));
    }

    /*
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







    @Test
    @DisplayName("Добавление песни увеличивает список на 1")
    void addSong_shouldAddSongToList() {
        // Arrange
        int sizeBefore = playlist.getSongs().size();

        // Act
        playlist.addSong(song);

        // Assert
        assertEquals(sizeBefore + 1, playlist.getSongs().size());
        assertTrue(playlist.getSongs().contains(song));
    }

    @Test
    @DisplayName("Добавление null бросает исключение")
    void addSong_shouldThrowException_whenSongIsNull() {
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> playlist.addSong(null));
        assertEquals(0, playlist.getSongs().size());
    }

    @Test
    @DisplayName("Добавление дубликата бросает исключение")
    void addSong_shouldThrowException_whenSongIsDuplicate() {
        // Arrange
        playlist.addSong(song);

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> playlist.addSong(song));
        assertEquals(1, playlist.getSongs().size());
    }

    @Test
    @DisplayName("Добавление нескольких разных песен работает")
    void addSong_shouldAddMultipleDifferentSongs() {
        // Arrange
        Song another = new Song("Yesterday", "The Beatles");

        // Act
        playlist.addSong(song);
        playlist.addSong(another);

        // Assert
        assertEquals(2, playlist.getSongs().size());
        assertTrue(playlist.getSongs().contains(song));
        assertTrue(playlist.getSongs().contains(another));

     */

}