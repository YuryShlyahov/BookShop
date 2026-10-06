package service;

import exception.BookNotFoundException;
import model.Book;
import model.Genre;

import java.time.LocalDate;
import java.util.*;

public class Library {
    private HashMap<Genre, Shelf<Book>> shelves;

    public Library() {
        shelves = new HashMap<>();
    }

    public void addShelf(Shelf<Book> shelf) {
        if (shelf == null) {
            throw new IllegalArgumentException("Полка не может быть null");
        }
        if (shelves.containsKey(shelf.getGenre())) {
            throw new IllegalStateException(
                    "Полка для жанра " + shelf.getGenre() + " уже существует");
        }
        shelves.put(shelf.getGenre(), shelf);
    }

    public Map<Genre, Shelf<Book>> getShelves() {
        return Collections.unmodifiableMap(shelves);
    }

    public Shelf<Book> getOrCreateShelf(Genre genre) {
        Shelf<Book> shelf = shelves.get(genre);
        if (shelf == null) {
            shelf = new Shelf<>(genre);
            addShelf(shelf);
        }
        return shelf;
    }

    public void addBook(Book book) {
        getOrCreateShelf(book.getGenre()).addBook(book);
    }

    public void printAllBooks() {
        for (Shelf<Book> shelf : shelves.values()) {
            System.out.println("Книги жанра - " + shelf.getGenre().getName());
            shelf.printAllBooks();
            System.out.println("***************************");
        }
    }

    public Book findBook(String title) throws BookNotFoundException {
        for (Shelf<Book> shelf : shelves.values()) {
            try {
                return shelf.findBook(title);
            } catch (BookNotFoundException e) {
                // не нашли на этой полке — идём дальше
            }
        }
        throw new BookNotFoundException("Книга '" + title + "' не найдена в библиотеке");
    }

    public List<Book> findBookByDate(LocalDate addedDate) throws BookNotFoundException {
        ArrayList<Book> foundBooks = new ArrayList<>();
        for (Shelf<Book> shelf : shelves.values()) {
            try {
                foundBooks.addAll(shelf.findBooksByDate(addedDate));
            } catch (BookNotFoundException e) {
                // не нашли на этой полке — идём дальше
            }
        }
        if (foundBooks.isEmpty()) {
            throw new BookNotFoundException("Книги, добавленные " + addedDate.toString() + " не найдены в библиотеке");
        }
        return foundBooks;
    }

    public List<Book> findAuthor(String author) {
        List<Book> authorBooks = new ArrayList<>();
        for (Shelf<Book> shelf : shelves.values()) {
            authorBooks.addAll(shelf.findAuthor(author));
        }
        return authorBooks;
    }

    public List<String> findAllAuthors() {
        List<String> authors = new ArrayList<>();
        for (Shelf<Book> shelf : shelves.values()) {
            for (String author : shelf.findAllAuthors()) {
                if (!authors.contains(author)) {
                    authors.add(author);
                }
            }
        }
        return authors;
    }

    public void printAllAuthors() {
        List<String> authors = findAllAuthors();
        Collections.sort(authors);
        if (authors.isEmpty()) {
            System.out.println("Авторы не найдены");
        } else {
            System.out.println("Найденные авторы: ");
            for (String author : authors) {
                System.out.println(author);
            }
        }
    }

    public void printBooksByAuthor(String author) {
        List<Book> authorBooks = findAuthor(author);
        if (authorBooks.isEmpty()) {
            System.out.println("Не найдены книги автора : " + author);
        } else {
            System.out.println("Найденные книги автора : " + author);
            for (Book book : authorBooks) {
                System.out.println(book.getDescription());
            }
        }
    }

    public int countReadBooks() {
        int sum = 0;
        for (Shelf<Book> shelf : shelves.values()) {
            sum += shelf.countReadBooks();
        }
        return sum;
    }


    public void printUnreadBooks() {
        List<Book> unreadBooks = findUnreadBooks();
        if (!unreadBooks.isEmpty()) {
            System.out.println("Список непрочитанных книг : ");
            for (Book book : unreadBooks) {
                System.out.println(book.getDescription());
            }
        } else {
            System.out.println("Не найдено непрочитанных книг");
        }
    }

    public void markBookAsRead(String title) throws BookNotFoundException {
        findBook(title).markAsRead();
    }

    public void buy(String title) throws BookNotFoundException {
        findBook(title).buy();
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (Shelf<Book> shelf : shelves.values()) {
            totalPrice += shelf.getTotalPrice();
        }
        return totalPrice;
    }

    public void printTotalPrice() {
        System.out.println("Общая цена за книги в библиотеке: " + getTotalPrice());
    }

    public List<Book> findPurchasedBooks() {
        List<Book> purchasedBooks = new ArrayList<>();
        for (Shelf<Book> shelf : shelves.values()) {
            purchasedBooks.addAll(shelf.findPurchasedBooks());
        }
        return purchasedBooks;
    }

    public void printPurchasedBooks() {
        List<Book> purchasedBooks = findPurchasedBooks();
        if (!purchasedBooks.isEmpty()) {
            System.out.println("Купленные книги: ");
            for (Book book : purchasedBooks) {
                System.out.println(book.getDescription());
            }
        } else {
            System.out.println("Купленных книг не найдено");
        }
    }

    public void removeBook(String title) throws BookNotFoundException {
        boolean removed = false;
        Iterator<Shelf<Book>> iterator = shelves.values().iterator();
        while (iterator.hasNext()) {
            Shelf<Book> shelf = iterator.next();
            if (shelf.removeBook(title)) {
                removed = true;
                if (shelf.getBooks().isEmpty()) {
                    iterator.remove();
                }
            }
        }
        if (!removed) {
            throw new BookNotFoundException("Невозможно удалить книгу, книга не найдена в библиотеке.");
        }
    }

    public void sortByAddedDate() {
        for (Shelf<Book> shelf : shelves.values()) {
            shelf.sortByDate();
        }
    }

    public List<Book> findUnreadBooks() {
        List<Book> unreadBooks = new ArrayList<>();
        for (Shelf<Book> shelf : shelves.values()) {
            unreadBooks.addAll(shelf.findUnreadBooks());
        }
        return unreadBooks;
    }

    public double findEbooksSize() {
        double eBookSize = 0;
        for (Shelf<Book> shelf : shelves.values()) {
            eBookSize += shelf.countEbooksSize();
        }
        return eBookSize;
    }

    public void printEbooksSize() {
        System.out.println("Размер всех электронных книг в библиотеке : " + findEbooksSize());
    }

    public int findAudioBooksDuration() {
        int duration = 0;
        for (Shelf<Book> shelf : shelves.values()) {
            duration += shelf.countAudioBooksDuration();
        }
        return duration;
    }

    public void printAudioBooksDuration() {
        System.out.println("Продолжительность всех аудиокниг в библиотеке : " + findAudioBooksDuration());
    }
}



