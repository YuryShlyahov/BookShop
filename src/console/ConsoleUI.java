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
        library.printBooksByAuthor(author);
    }

    private void markBookAsRead(String title) {
        if(title == null || title.trim().isEmpty()){
            System.err.println("Ошибка:  название не введено, повторите ввод");
            return;
        }
        try {
            library.markBookAsRead(title);
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
            library.findBook(title);
            if (library.findBook(title).isPurchased()){
                System.out.println("Ошибка: книга уже была куплена ранее");
                return;
            }
            library.buy(title);
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

    private void printPurchasedBooks() {
        library.printPurchasedBooks();
    }

    private void printUnreadBooks() {
        System.out.println("Всего прочитанных книг: " + library.countReadBooks() + " шт.");
        library.printUnreadBooks();
    }

    private void printAllBooks() {
        System.out.println("Список всех книг:");
        library.printAllBooks();
    }

    private void printAllAuthors() {
        library.printAllAuthors();
    }

    private void printTotalPrice() {
        library.printTotalPrice();
    }
    public void start() {
            while (true) {
                printMenu();
                String command = scanner.nextLine();
                switch (command) {
                    case "1":
                        addBook();
                        break;
                    case "2":
                        System.out.println("Поиск книги по названию");
                        System.out.println("Введите название книги");
                        String title = scanner.nextLine();
                        findBookByTitle(title);
                        break;
                    case "3":
                        System.out.println("Поиск книги по автору");
                        System.out.println("Введите имя автора");
                        String author = scanner.nextLine();
                        findBookByAuthor(author);
                        break;
                    case "4":
                        // ПОКА НЕ РЕАЗИОВАНО
                        break;
                    case "5":
                        System.out.println("Отметить книгу как прочитанную");
                        System.out.println("Введите название книги");
                        title = scanner.nextLine();
                        markBookAsRead(title);
                        break;
                    case "6":
                        System.out.println("Покупка книги");
                        System.out.println("Введите название книги");
                        title = scanner.nextLine();
                        buyBook(title);
                        break;
                    case "7":
                        System.out.println("Удаление книги");
                        System.out.println("Введите название книги");
                        title = scanner.nextLine();
                        deleteBook(title);
                        break;
                    case "8":
                        printAllBooks();
                        break;
                    case "9":
                        printUnreadBooks();
                        break;
                    case "10":
                        printPurchasedBooks();
                        break;
                    case "11":
                        printTotalPrice();
                        break;
                    case "12":
                        printAllAuthors();
                        break;
                    case "0":
                        System.out.println("ВЫХОД ИЗ ПРОГРАММЫ");
                        return;
                    default:
                        System.out.println("Введена некорректная команда. Повторите ввод.");
                }
            }
        }

        private static void printMenu() {
            {
                System.out.println("Выберите команду:");
                System.out.println("1 - Добавить книгу");
                System.out.println("2 - Найти книгу по названию");
                System.out.println("3 - Найти книги по автору");
                System.out.println("4 - Найти книги по дате добавления");
                System.out.println("5 - Отметить книгу как прочитанную");
                System.out.println("6 - Купить книгу");
                System.out.println("7 - Удалить книгу");
                System.out.println("8 - Показать все книги");
                System.out.println("9 - Показать все непрочитанные книги");
                System.out.println("10 - Показать все купленные книги");
                System.out.println("11 - Показать общую стоимость всех книг");
                System.out.println("12 - Показать всех авторов");
                System.out.println("13 - ");
                System.out.println("14 - ");
                System.out.println("15 - ");
                System.out.println("16 - ");
                System.out.println("17 - ");
                System.out.println("18 - ");
                System.out.println("0 - Выйти из программы");
            }
    }
}