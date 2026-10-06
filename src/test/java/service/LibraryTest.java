package service;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    private Genre genre;
    private  Genre otherGenre;
    private Book paperBookTest;
    private Book otherPaperBookTest;
    private AudioBook audioBookByKing;
    private EBook eBookByBradbury;
    private Shelf<Book> adventureShelf;
    private Shelf<Book> horrorShelf;
    ArrayList<Book> testList;
    Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
        adventureShelf = new Shelf<>(Genre.ADVENTURE);
        paperBookTest = new PaperBook("Книга", "Автор", 100, genre);
        otherPaperBookTest = new PaperBook("Книга", "Брат автора", 100, genre);
        audioBookByKing = new AudioBook("Долгий джонт", "С.Кинг", 200, genre, 200);
        eBookByBradbury = new EBook("Марсианские хроники", "Р.Бредбери", 500, genre, 3);
    }

    @Test
    public void shouldAddShelfToLibrary(){
        int index = library.getShelves().size();
        library.addShelf(adventureShelf);
        assertEquals(library.getShelves().get(adventureShelf.getGenre()), adventureShelf);
        assertEquals(index + 1, library.getShelves().size());
    }

    @Test
    public void shouldNotAddNull(){
        assertThrows(IllegalArgumentException.class, () -> library.addShelf(null), "Ошибка: не выбрасывается ошибка при добавлении null в библиотеку.");
    }

    @Test
    void shouldAddBookToLibrary() {
        Shelf<Book> shelf = new Shelf<>(genre);
        library.addShelf(shelf);
        int index = shelf.getBooks().size();
        library.addBook(paperBookTest);
        assertTrue(shelf.getBooks().contains(paperBookTest), "Ошибка: книга не добавляется на полку.");
        assertEquals(index + 1, library.getOrCreateShelf(genre).getBooks().size(), "Ошибка: размер полки при добавлении книги не увеличивается.");
    }





}
