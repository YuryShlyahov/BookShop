package console;

import service.Library;

import java.util.Scanner;

public class ConsoleUI {
    public void printMenu() {
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
        System.out.println("0 - Выход");
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
            while (true) {
                printMenu();
                String command = scanner.nextLine();
                switch (command) {
                    case "1":

                        break;
                    case "2":

                        break;
                    case "3":
                        System.out.println("Выход из программы"); // добавил сообщение о выходе
                        return;
                    default:
                        System.out.println("Введена некорректная команда. Повторите ввод.");
                }
            }
        }
    }


