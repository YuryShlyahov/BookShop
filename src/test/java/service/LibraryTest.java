package service;

import exception.BookNotFoundException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    private Book paperBookTest;
    private Book otherPaperBookTest;
    private AudioBook audioBookByKing;
    private EBook eBookByBradbury;
    private Shelf<Book> adventureShelf;
    private Shelf<Book> horrorShelf;
    ArrayList<Book> testList;
    Library library;

    @BeforeEach
    public void setUp() {
        library = new Library();
        testList = new ArrayList<Book>();
        adventureShelf = new Shelf<>(Genre.ADVENTURE);
        paperBookTest = new PaperBook("Книга", "Автор", 100, Genre.ADVENTURE);
        otherPaperBookTest = new PaperBook("Книга", "Брат автора", 100, Genre.ADVENTURE);
        audioBookByKing = new AudioBook("Долгий джонт", "С.Кинг", 200, Genre.ADVENTURE, 200);
        eBookByBradbury = new EBook("Марсианские хроники", "Р.Бредбери", 500, Genre.ADVENTURE, 3);
    }

    @Test
    public void shouldAddShelfToLibrary() {
        int index = library.getShelves().size();
        library.addShelf(adventureShelf);
        assertEquals(library.getShelves().get(adventureShelf.getGenre()), adventureShelf);
        assertEquals(index + 1, library.getShelves().size());
    }

    @Test
    public void shouldNotAddNull() {
        assertThrows(IllegalArgumentException.class, () -> library.addShelf(null), "Ошибка: не выбрасывается ошибка при добавлении полки null в библиотеку.");
    }

    @Test
    public void shouldNotAddDuplicateShelf() {
        library.addShelf(adventureShelf);
        assertThrows(IllegalStateException.class, () -> library.addShelf(adventureShelf), "Ошибка: не выбрасывается ошибка при добавлении дубликата полки в библиотеку.");
    }

    @Test
    public void shouldGetAddedShelf() {
        assertFalse(library.getShelves().containsKey(adventureShelf.getGenre()), "Ошибка: библиотека содержит полку, которая не была добавлена.");
        library.addShelf(adventureShelf);
        assertTrue(library.getShelves().containsKey(adventureShelf.getGenre()), "Ошибка: библиотека не содержит полку, которая была добавлена.");
    }

    @Test
    public void shouldGetCreatedShelf() {
        assertFalse(library.getShelves().containsKey(Genre.ADVENTURE), "Ошибка: библиотека содержит полку, которая не была добавлена.");
        int index = library.getShelves().size();
        assertEquals(Genre.ADVENTURE, library.getOrCreateShelf(Genre.ADVENTURE).getGenre(), "Ошибка: библиотека не создает полку по жанру.");
        assertEquals(index + 1, library.getShelves().size(), "Ошибка: размер библиотеки не вырос при создании новой полки.");
    }

    @Test
    public void shouldNotGetOrCreateShelfWhenGenreIsNull() {
        assertThrows(IllegalArgumentException.class, () -> library.getOrCreateShelf(null), "Ошибка: Создается полка с null жанром.");
    }

    @Test
    public void shouldAddBookToLibrary() {
        Shelf<Book> shelf = new Shelf<>(Genre.ADVENTURE);
        library.addShelf(shelf);
        int index = shelf.getBooks().size();
        library.addBook(paperBookTest);
        assertTrue(shelf.getBooks().contains(paperBookTest), "Ошибка: книга не добавляется на полку.");
        assertEquals(index + 1, library.getOrCreateShelf(Genre.ADVENTURE).getBooks().size(), "Ошибка: размер полки при добавлении книги не увеличивается.");
    }

    @Test
    public void shouldFindBook(){
        library.addBook(paperBookTest);
        assertEquals(paperBookTest, library.findBook(paperBookTest.getTitle()), "Ошибка: не находится добавленная книга по названию.");
        assertEquals(paperBookTest, library.findBook(paperBookTest.getTitle().toLowerCase()), "Ошибка: не находится книга по названию с измененным регистром.");
        assertEquals(paperBookTest, library.findBook(paperBookTest.getTitle().toUpperCase()), "Ошибка: не находится книга по названию с измененным регистром.");
    }

    @Test
    public void shouldNotFindBookWithWrongTitle(){
        assertThrows(BookNotFoundException.class, () -> library.findBook("Отсутствующее название"), "Ошибка: не выбрасывается ошибка при поиске книги по не верному названию.");
    }

    @Test
    public void shouldNotFindBookWithNullTitle(){
        assertThrows(IllegalArgumentException.class, () -> library.findBook(null), "Ошибка: не выбрасывается ошибка при поиске книги по null названию.");
    }

    @Test
    public void shouldNotFindBookWithNullAddedDate(){
        assertThrows(IllegalArgumentException.class, () -> library.findBookByDate(null), "Ошибка: не выбрасывается ошибка при поиске книги по null дате.");
    }

    @Test
    public void shouldNotFindBookWithWrongDate(){
        LocalDate testDate = LocalDate.of(2026, 1, 1);
        paperBookTest.setAddedDate(testDate);
        library.addBook(paperBookTest);
        LocalDate otherTestDate = LocalDate.of(2026, 1, 2);
        assertThrows(BookNotFoundException.class, () -> library.findBookByDate(otherTestDate));
    }

    @Test
    public void shouldFindBookByDate(){
        LocalDate testDate = LocalDate.of(2026, 1, 1);
        paperBookTest.setAddedDate(testDate);
        library.addBook(paperBookTest);
        testList.add(paperBookTest);
        assertEquals(testList, library.findBookByDate(testDate));
    }

}
