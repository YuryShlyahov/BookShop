package service;

import exception.BookNotFoundException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class ShelfTest {

    private Genre genre;
    private Book paperBook;
    private Book otherPaperBook;
    private AudioBook kingAudioBook;
    private EBook marsEBook;
    private Shelf<Book> shelf;
    ArrayList<Book> testList;

    @BeforeEach
    void setUp() {
        genre = Genre.HORROR;
        paperBook = new PaperBook("Книга", "Автор", 100, genre);
        otherPaperBook = new PaperBook("Книга", "Брат автора", 100, genre);
        kingAudioBook = new AudioBook("Долгий джонт", "С.Кинг", 200, genre, 200);
        marsEBook = new EBook("Марсианские хроники", "Р.Бредбери", 500, genre, 3);
        shelf = new Shelf<>(genre);
    }

    @Test
    void shouldAddBookToList() {
        int sizeBefore = shelf.getBooks().size();
        shelf.addBook(paperBook);
        assertEquals(sizeBefore + 1, shelf.getBooks().size(), "Ошибка: размер списка не увеличился.");
        assertTrue(shelf.getBooks().contains(paperBook), "Ошибка: книга отсутствует в списке.");
    }

    @Test
    void shouldThrowExceptionWhenAddingNull() {
        assertThrows(IllegalArgumentException.class, () -> shelf.addBook(null), "Ошибка: не выбрасывается ошибка при добавлении null на полку.");
    }


    @Test
    void shouldNotChangeBookList(){
        assertThrows(UnsupportedOperationException.class, () -> shelf.getBooks().add(paperBook));
    }
    @Test
    void shouldThrowExceptionWhenAddingDuplicate() {
        shelf.addBook(paperBook);
        assertThrows(IllegalStateException.class, () -> shelf.addBook(paperBook), "Ошибка: не выбрасывается ошибка при добавлении дубликата книги на полку.");
    }

    @Test
    void shouldIncreaseNumberOfReadBooks() {
        int sizeBefore = shelf.countReadBooks();
        paperBook.markAsRead();
        shelf.addBook(paperBook);
        assertEquals(sizeBefore + 1, shelf.countReadBooks(), "Ошибка: количество прочитанных книг не выросло.");
    }

    @Test
    void shouldReturnUnreadBook() {
        ArrayList<Book> unReadBooks = new ArrayList<>();
        unReadBooks.add(paperBook);
        shelf.addBook(paperBook);
        shelf.addBook(kingAudioBook);
        kingAudioBook.markAsRead();
        assertEquals(unReadBooks, shelf.findUnreadBooks(), "Ошибка: метод возвращает неверный список непрочитанных книг");
    }

    @Test
    void shouldCountOneBookPrice() {
        assertEquals(200, paperBook.getPrice(), "Ошибка: Стоимость бумажной книги считается некорректно");
        assertEquals(300, kingAudioBook.getPrice(), "Ошибка: Стоимость аудио книги считается некорректно");
        assertEquals(30, marsEBook.getPrice(), "Ошибка: Стоимость электронной книги считается некорректно");
    }

    @Test
    void shouldCountBooksPrice() {
        assertEquals(0.0, shelf.getTotalPrice(), "Ошибка: Цена на пустой полке не равна нулю.");
        shelf.addBook(paperBook);
        shelf.addBook(kingAudioBook);
        shelf.addBook(marsEBook);
        assertEquals(paperBook.getPrice() + kingAudioBook.getPrice() + marsEBook.getPrice(), shelf.getTotalPrice(), "Ошибка: Общая стоимость книг считается некорректно.");
    }

    @Test
    void shouldFindNoPurchasedBooksShelf() {
        testList = new ArrayList<>();
        shelf.addBook(paperBook);
        assertEquals(testList, shelf.findPurchasedBooks(), "Ошибка: неверный результат при поиске купленных книг при их отсутствии на полке");
    }

    @Test
    void shouldFindBooksIgnoreCase() {
        shelf.addBook(paperBook);
        assertEquals(paperBook, shelf.findBook("Книга"), "Ошибка: книга не находится по названию");
        assertEquals(paperBook, shelf.findBook("КНИГА"), "Ошибка: книга не находится по названию без учета регистра");
        assertEquals(paperBook, shelf.findBook("книга"), "Ошибка: книга не находится по названию без учета регистра");
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
    void shouldThrowExceptionBookNotFound() {
        assertThrows(BookNotFoundException.class, () -> shelf.findBook("Доктор Живаго"));
    }

    @Test
    void shouldFindBookByDate() {
        shelf.addBook(paperBook);
        testList = new ArrayList<>(List.of(paperBook));
        assertEquals(testList, shelf.findBooksByDate(LocalDate.now()));
    }

    @Test
    void shouldNotFindBookByWrongDate() {
        paperBook.setAddedDate(LocalDate.of(2000, 11, 11));
        shelf.addBook(paperBook);
        testList = new ArrayList<>(List.of(paperBook));
        assertNotEquals(testList, shelf.findBooksByDate(LocalDate.now()));
    }

    @Test
    void shouldFindBookByAuthor() {
        testList = new ArrayList<>(List.of(paperBook));
        shelf.addBook(paperBook);
        assertNotEquals(testList, shelf.findAuthor("author"), "Ошибка, поиск находит книгу по неверному значению автора");
        assertNotEquals(testList, shelf.findAuthor(""), "Ошибка, поиск находит книгу по неверному значению автора");
        assertEquals(testList, shelf.findAuthor("Автор"), "Ошибка, неверно работает поиск по автору с корректным регистром");
        assertEquals(testList, shelf.findAuthor("автор"), "Ошибка, неверно работает поиск по автору с некорректным регистром");
        assertEquals(testList, shelf.findAuthor("АВТОР"), "Ошибка, неверно работает поиск по автору с некорректным регистром");
        assertEquals(testList, shelf.findAuthor("АвТоР"), "Ошибка, неверно работает поиск по автору с некорректным регистром");
    }

    @Test
    void shouldFindAllAuthors() {
        Book wine = new AudioBook("Вино из одуванчиков", "Р.Бредбери", 200, genre, 20);
        List<String> authorsList = new ArrayList<>();
        assertEquals(authorsList, shelf.findAllAuthors(), "Ошибка: неверно работает поиск авторов, когда список книг пуст. ");
        shelf.addBook(paperBook);
        shelf.addBook(kingAudioBook);
        shelf.addBook(wine);
        shelf.addBook(marsEBook);
        authorsList = List.of("Автор", "С.Кинг", "Р.Бредбери");
        assertEquals(authorsList, shelf.findAllAuthors(), "Ошибка: список авторов книг на полке выводится неверно");
    }

    @Test
    void shouldSortBooks() {
        shelf.addBook(paperBook);
        shelf.addBook(kingAudioBook);
        shelf.addBook(otherPaperBook);
        shelf.addBook(marsEBook);
        shelf.sortByTitle();
        testList = new ArrayList<>(List.of(kingAudioBook, paperBook, otherPaperBook, marsEBook));
        assertEquals(testList, shelf.getBooks());
    }

    @Test
    void shouldRemoveBook() {
        shelf.addBook(paperBook);
        shelf.addBook(kingAudioBook);
        shelf.addBook(otherPaperBook);
        shelf.addBook(marsEBook);
        int index = shelf.getBooks().size();
        shelf.removeBook("Марсианские хроники");
        assertEquals(index - 1, shelf.getBooks().size(), "Ошибка: при удалении элемента список не уменьшается");
        assertFalse(shelf.getBooks().contains(marsEBook), "Ошибка: при удалении элемента он остается на полке");
        assertFalse(shelf.removeBook("Куш"), "Ошибка при удалении несуществующей книги.");
    }

    @Test
    void shouldFindPurchasedBooks() {
        shelf.addBook(paperBook);
        shelf.addBook(kingAudioBook);
        shelf.addBook(otherPaperBook);
        shelf.addBook(marsEBook);
        paperBook.buy();
        marsEBook.buy();
        testList = new ArrayList<>(List.of(paperBook, marsEBook));
        assertEquals(testList, shelf.findPurchasedBooks(), "Ошибка: неправильно работает вывод купленных книг");
    }

    @Test
    void shouldFindEbooks() {
        shelf.addBook(paperBook);
        shelf.addBook(kingAudioBook);
        shelf.addBook(otherPaperBook);
        shelf.addBook(marsEBook);
        testList = new ArrayList<>(List.of(marsEBook));
        assertEquals(testList, shelf.findEbooks(), "Ошибка: неверно работает поиск электронных книг на полке");
    }

    @Test
    void shouldFindAudiobooks() {
        shelf.addBook(paperBook);
        shelf.addBook(kingAudioBook);
        shelf.addBook(otherPaperBook);
        shelf.addBook(marsEBook);
        testList = new ArrayList<>(List.of(kingAudioBook));
        assertEquals(testList, shelf.findAudioBooks(), "Ошибка: неверно работает поиск аудио книг на полке");
    }

    @Test
    void shouldFindPaperBooks() {
        shelf.addBook(paperBook);
        shelf.addBook(kingAudioBook);
        shelf.addBook(otherPaperBook);
        shelf.addBook(marsEBook);
        testList = new ArrayList<>(List.of(paperBook, otherPaperBook));
        assertEquals(testList, shelf.findPaperBooks(), "Ошибка: неверно работает поиск бумажных книг на полке");
    }

    @Test
    void shouldCountAllAudiobooksDuration() {
        AudioBook otherKingAudioBook = new AudioBook("Оставшийся в живых", "С.Кинг", 600, genre, 150);
        shelf.addBook(kingAudioBook);
        shelf.addBook(otherKingAudioBook);
        assertEquals(kingAudioBook.getDuration() + otherKingAudioBook.getDuration(), shelf.countAudioBooksDuration(), "Ошибка: неверно работает подсчет продолжительности аудио книг");
    }

    @Test
    void shouldCountAllEbooksSize() {
        EBook wine = new EBook("Вино из одуванчиков", "Р.Бредбери", 200, genre, 20);
        shelf.addBook(wine);
        shelf.addBook(marsEBook);
        assertEquals(wine.getFileSize() + marsEBook.getFileSize(), shelf.countEbooksSize(), "Ошибка: неверно работает подсчет размера электронных книг");
    }
}