package service;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibraryTest {
    private Genre genre;
    private  Genre otherGenre;
    private Book paperBookTest;
    private Book otherPaperBookTest;
    private AudioBook audioBookByKing;
    private EBook eBookByBradbury;
    private Shelf<Book> shelf;
    ArrayList<Book> testList;
    Library library;

    @BeforeEach
    void setUp() {
        library = new Library();

        genre = Genre.HORROR;
        otherGenre = Genre.ADVENTURE;
        paperBookTest = new PaperBook("Книга", "Автор", 100, genre);
        otherPaperBookTest = new PaperBook("Книга", "Брат автора", 100, genre);
        audioBookByKing = new AudioBook("Долгий джонт", "С.Кинг", 200, genre, 200);
        eBookByBradbury = new EBook("Марсианские хроники", "Р.Бредбери", 500, genre, 3);

        shelf = new Shelf<>(genre);
    }

    @Test
    void shouldAddBookToList() {
        int sizeBefore = shelf.getBooks().size();
        shelf.addBook(paperBookTest);
        assertEquals(sizeBefore + 1, shelf.getBooks().size(), "Ошибка: размер списка не увеличился.");
        assertTrue(shelf.getBooks().contains(paperBookTest), "Ошибка: книга отсутствует в списке.");
    }


}
