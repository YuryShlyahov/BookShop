package service;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class ShelfTest {

    private Genre genre;
    private Book book;
    private Book otherBook;
    private AudioBook kingAudioBook;
    private EBook marsEBook;
    private Shelf<Book> shelf;
    ArrayList<Book> testList;

    @BeforeEach
    void setUp() {
        genre = Genre.HORROR;
        book = new PaperBook("Книга", "Автор", 100, genre);
        otherBook = new PaperBook("Книга", "Брат автора", 100, genre);
        kingAudioBook = new AudioBook("Долгий джонт", "С.Кинг", 200, genre, 200);
        marsEBook = new EBook("Марсианские хроники", "Р.Бредбери", 500, genre, 3);
        shelf = new Shelf<>(genre);
    }

    @Test
    void shouldAddBookToList() {
        int sizeBefore = shelf.getBooks().size();
        shelf.addBook(book);
        assertEquals(sizeBefore + 1, shelf.getBooks().size(), "Ошибка: размер списка не увеличился.");
        assertTrue(shelf.getBooks().contains(book), "Ошибка: книга отсутствует в списке.");
    }

    @Test
    void shouldIncreaseNumberOfReadBooks() {
        int sizeBefore = shelf.countReadBooks();
        book.markAsRead();
        shelf.addBook(book);
        assertEquals(sizeBefore + 1, shelf.countReadBooks(), "Ошибка: количество прочитанных книг не выросло.");
    }

    @Test
    void shouldReturnUnreadBook() {
        ArrayList<Book> unReadBooks = new ArrayList<>();
        unReadBooks.add(book);
        shelf.addBook(book);
        shelf.addBook(kingAudioBook);
        kingAudioBook.markAsRead();
        assertEquals(unReadBooks, shelf.findUnreadBooks(), "Ошибка: метод возвращает неверный список непрочитанных книг");
    }

    @Test
    void shouldCountBooksPrice() {
        assertEquals(200, book.getPrice(), "Стоимость бумажных книг считается некорректно");
        assertEquals(300, kingAudioBook.getPrice(), "Стоимость аудио книг считается некорректно");
        assertEquals(30, marsEBook.getPrice(), "Стоимость электронных книг считается некорректно");
        shelf.addBook(book);
        shelf.addBook(kingAudioBook);
        shelf.addBook(marsEBook);
        assertEquals(shelf.getTotalPrice(), book.getPrice() + kingAudioBook.getPrice() + marsEBook.getPrice(), "Общая стоимость книг считается некорректно.");
    }

    @Test
    void shouldFindBooksIgnoreCase() {
        shelf.addBook(book);
        assertEquals(book, shelf.findBook("Книга"), "Ошибка: книга не находится по названию");
        assertEquals(book, shelf.findBook("КНИГА"), "Ошибка: книга не находится по названию без учета регистра");
        assertEquals(book, shelf.findBook("книга"), "Ошибка: книга не находится по названию без учета регистра");
        shelf.addBook(marsEBook);
        assertEquals(marsEBook, shelf.findBook("Марсианские хроники"), "Ошибка: книга не находится по названию");
        assertEquals(marsEBook, shelf.findBook("МАРСИАНСКИЕ ХРОНИКИ"), "Ошибка: книга не находится по названию без учета регистра");
        assertEquals(marsEBook, shelf.findBook("марсианские хроники"), "Ошибка: книга не находится по названию без учета регистра");
        shelf.addBook(kingAudioBook);
        assertEquals(kingAudioBook, shelf.findBook("Долгий джонт"), "Ошибка: книга не находится по названию");
        assertEquals(kingAudioBook, shelf.findBook("ДОЛГИЙ ДЖОНТ"), "Ошибка: книга не находится по названию без учета регистра");
        assertEquals(kingAudioBook, shelf.findBook("долгий джонт"), "Ошибка: книга не находится по названию без учета регистра");
    }

    @Test
    void shouldFindBookByDate() {
        shelf.addBook(book);
        testList = new ArrayList<>(List.of(book));
        assertEquals(testList, shelf.findBooksByDate(LocalDate.now()));
    }

    @Test
    void shouldFindBookByAuthor() {
        testList = new ArrayList<>(List.of(book));
        shelf.addBook(book);
        assertEquals(testList, shelf.findAuthor("Автор"));
        assertEquals(testList, shelf.findAuthor("автор"));
        assertEquals(testList, shelf.findAuthor("АВТОР"));
        assertEquals(testList, shelf.findAuthor("АвТоР"));
    }

    @Test
    void shouldFindAllAuthors() {
        Book wine = new AudioBook("Вино из одуванчиков", "Р.Бредбери", 200, genre, 20);
        shelf.addBook(book);
        shelf.addBook(kingAudioBook);
        shelf.addBook(wine);
        shelf.addBook(marsEBook);
        List<String> authorsList = new ArrayList<>(List.of("Автор", "С.Кинг", "Р.Бредбери"));
        assertEquals(authorsList, shelf.findAllAuthors(), "Список книг на полке выводится неверно");
    }

    @Test
    void shouldSortBooks() {
        shelf.addBook(book);
        shelf.addBook(kingAudioBook);
        shelf.addBook(otherBook);
        shelf.addBook(marsEBook);
        shelf.sortByTitle();
        testList = new ArrayList<Book>(List.of(kingAudioBook, book, otherBook, marsEBook));
        assertEquals(testList, shelf.getBooks());
    }

    @Test
    void shouldRemoveBook() {
        shelf.addBook(book);
        shelf.addBook(kingAudioBook);
        shelf.addBook(otherBook);
        shelf.addBook(marsEBook);
        int index = shelf.getBooks().size();
        shelf.removeBook("Марсианские хроники");
        assertEquals(index - 1, shelf.getBooks().size(), "Ошибка: при удалении элемента список не уменьшается");
        assertFalse(shelf.getBooks().contains(marsEBook));
    }

    @Test
    void shouldFindPurchasedBooks() {
        shelf.addBook(book);
        shelf.addBook(kingAudioBook);
        shelf.addBook(otherBook);
        shelf.addBook(marsEBook);
        book.buy();
        marsEBook.buy();
        testList = new ArrayList<>(List.of(book, marsEBook));
        assertEquals(testList, shelf.findPurchasedBooks(), "Ошибка: неправильно работает вывод купленных книг");
    }

    @Test
    void shouldFindEbook() {
        shelf.addBook(book);
        shelf.addBook(kingAudioBook);
        shelf.addBook(otherBook);
        shelf.addBook(marsEBook);
        testList = new ArrayList<>(List.of(marsEBook));
        assertEquals(testList, shelf.findEbooks());
    }

    @Test
    void shouldFindAudiobook() {
        shelf.addBook(book);
        shelf.addBook(kingAudioBook);
        shelf.addBook(otherBook);
        shelf.addBook(marsEBook);
        testList = new ArrayList<>(List.of(kingAudioBook));
        assertEquals(testList, shelf.findAudioBooks());
    }

    @Test
    void shouldCountAllAudiobooksDuration() {
        AudioBook otherKingAudioBook = new AudioBook("Оставшийся в живых", "С.Кинг", 600, genre, 150);
        shelf.addBook(kingAudioBook);
        shelf.addBook(otherKingAudioBook);
        assertEquals(kingAudioBook.getDuration() + otherKingAudioBook.getDuration(), shelf.countAudioBooksDuration());
    }

    @Test
    void shouldCountAllEbooksSize() {
        EBook wine = new EBook("Вино из одуванчиков", "Р.Бредбери", 200, genre, 20);
        shelf.addBook(wine);
        shelf.addBook(marsEBook);
        assertEquals(wine.getFileSize() + marsEBook.getFileSize(), shelf.countEbooksSize());
    }
}