package console;

import exception.BookNotFoundException;
import model.*;
import service.Library;
import service.Shelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Library library;
    private final Scanner scanner;

    public ConsoleUI(Library library, Scanner scanner) {
        this.library = library;
        this.scanner = scanner;
    }

    // ... другие методы меню ...

    private void addBook() {
        System.out.println("=== Добавление новой книги ===");

        // 1. Ввод общих полей
        System.out.print("Введите название книги: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Название не может быть пустым.");
            return;
        }

        System.out.print("Введите автора: ");
        String author = scanner.nextLine().trim();
        if (author.isEmpty()) {
            System.out.println("Автор не может быть пустым.");
            return;
        }

        int pages = readPositiveInt("Введите количество страниц: ");

        // 2. Выбор жанра из списка
        Genre genre = selectGenre();

        // 3. Выбор типа книги
        System.out.println("Выберите тип книги:");
        System.out.println("1 - Бумажная");
        System.out.println("2 - Электронная");
        System.out.println("3 - Аудиокнига");
        int typeChoice = readIntInRange("Ваш выбор: ", 1, 3);

        // 4. Создание книги в зависимости от типа
        Book newBook = null;
        switch (typeChoice) {
            case 1:
                newBook = new PaperBook(title, author, pages, genre);
                break;
            case 2:
                double fileSize = readPositiveDouble("Введите размер файла (МБ): ");
                newBook = new EBook(title, author, pages, genre, fileSize);
                break;
            case 3:
                int duration = readPositiveInt("Введите длительность (мин.): ");
                newBook = new AudioBook(title, author, pages, genre, duration);
                break;
            default:
                System.out.println("Некорректный выбор типа.");
                return;
        }

        // 5. Добавление в библиотеку
        library.addBook(newBook);
        System.out.println("Книга успешно добавлена!");
    }

    // Вспомогательные методы для безопасного ввода

    private int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value > 0) return value;
                System.out.println("Число должно быть положительным.");
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    private double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine());
                if (value > 0) return value;
                System.out.println("Число должно быть положительным.");
            } catch (NumberFormatException e) {
                System.out.println("Введите число (можно с дробной частью).");
            }
        }
    }

    private int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) return value;
                System.out.println("Введите число от " + min + " до " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    private Genre selectGenre() {
        System.out.println("Доступные жанры:");
        Genre[] genres = Genre.values();
        for (int i = 0; i < genres.length; i++) {
            System.out.println((i + 1) + " - " + genres[i].getName());
        }
        int choice = readIntInRange("Выберите номер жанра: ", 1, genres.length);
        return genres[choice - 1];
    }

    private void findBookByTitle(String title) {
        if(title == null || title.trim().isEmpty()){
            System.err.println("Ошибка:  название не введено, повторите ввод");
            return;
        }
        try {
            System.out.println(library.findBook(title).getDescription());
        } catch (BookNotFoundException e) {
            System.err.println("Ошибка:  " + e.getMessage());
        }
    }

    private void findBookByAuthor(String author) {

        if(author == null || author.trim().isEmpty()){
            System.err.println("Ошибка:  имя автора не введено, повторите ввод");
            return;
        }
        List <Book> booksByAuthor = library.findAuthor(author);
        if (booksByAuthor.isEmpty()){
            System.err.println("Ошибка:  книг введенного автора не найдено");
            return;
        }
        System.out.println("Найденные книги автора '" + author + "':");
        for (Book book : booksByAuthor){
            System.out.println(book.getDescription());
        }
    }

    private void markBookAsRead(String title) {
        if(title == null || title.trim().isEmpty()){
            System.err.println("Ошибка:  название не введено, повторите ввод");
            return;
        }
        try {
            Book book = library.findBook(title);
            book.markAsRead();
            System.out.println("Книга '" + title + "' отмечена как прочитанная.");
        } catch (BookNotFoundException e) {
            System.err.println("Ошибка:  " + e.getMessage());
        }
    }

    private void buyBook(String title) {
        if(title == null || title.trim().isEmpty()){
            System.err.println("Ошибка:  название не введено, повторите ввод");
            return;
        }
        try {
            Book book = library.findBook(title);
            if (book.isPurchased()){
                System.out.println("Ошибка: книга уже была куплена ранее");
                return;
            }
            book.buy();
            System.out.println("Книга '" + title + "' куплена.");
        } catch (BookNotFoundException e) {
            System.err.println("Ошибка:  " + e.getMessage());
        }
    }

    private void deleteBook(String title) {
        if (title == null || title.trim().isEmpty()) {
            System.err.println("Ошибка: название не введено, повторите ввод");
            return;
        }

        try {
            library.removeBook(title);
            System.out.println("Книга '" + title + "' удалена.");
        } catch (BookNotFoundException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}