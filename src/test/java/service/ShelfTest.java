package service;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ShelfTest {

    private Genre genre;
    private Book book;
    private Book audioBook;
    private Book eBook;
    private Shelf<Book> shelf;

    @BeforeEach
    void setUp() {
        genre = Genre.HORROR;
        book = new PaperBook("Книга", "Автор", 100, genre);
        audioBook = new AudioBook("Долгий джонт", "С.Кинг", 200, genre, 200);
        eBook = new EBook("Марсианские хроники", "Р.Бредбери", 500, genre, 3);
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
        shelf.addBook(audioBook);
        audioBook.markAsRead();
        assertEquals(unReadBooks, shelf.findUnreadBooks(), "Ошибка: метод возвращает неверный список непрочитанных книг");
    }

    @Test
    void shouldCountBooksPrice(){
        assertEquals(200, book.getPrice(), "Стоимость бумажных книг считается некорректно");
        assertEquals(300, audioBook.getPrice(), "Стоимость аудио книг считается некорректно");
        assertEquals(30, eBook.getPrice(), "Стоимость электронных книг считается некорректно");
        shelf.addBook(book);
        shelf.addBook(audioBook);
        shelf.addBook(eBook);
        assertEquals(shelf.getTotalPrice(), book.getPrice() + audioBook.getPrice() + eBook.getPrice(), "Общая стоимость книг считается некорректно.");
    }

    @Test
    void shouldFindBooksIgnoreCase() {
        shelf.addBook(book);
        assertEquals(book, shelf.findBook("Книга"), "Ошибка: книга не находится по названию");
        assertEquals(book, shelf.findBook("КНИГА"), "Ошибка: книга не находится по названию без учета регистра");
        assertEquals(book, shelf.findBook("книга"), "Ошибка: книга не находится по названию без учета регистра");
        shelf.addBook(eBook);
        assertEquals(eBook, shelf.findBook("Марсианские хроники"), "Ошибка: книга не находится по названию");
        assertEquals(eBook, shelf.findBook("МАРСИАНСКИЕ ХРОНИКИ"), "Ошибка: книга не находится по названию без учета регистра");
        assertEquals(eBook, shelf.findBook("марсианские хроники"), "Ошибка: книга не находится по названию без учета регистра");
        shelf.addBook(audioBook);
        assertEquals(audioBook, shelf.findBook("Долгий джонт"), "Ошибка: книга не находится по названию");
        assertEquals(audioBook, shelf.findBook("ДОЛГИЙ ДЖОНТ"), "Ошибка: книга не находится по названию без учета регистра");
        assertEquals(audioBook, shelf.findBook("долгий джонт"), "Ошибка: книга не находится по названию без учета регистра");
    }
}