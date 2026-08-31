import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Library {
    private List<Shelf> shelves;

    public Library() {
    shelves = new ArrayList<>();
    }

    public void addBook(Book book){
        for(Shelf shelf : shelves){
            if(shelf.getGenre().equals(book.getGenre())){
                shelf.addBook(book);
                return;
            }
        }
        Shelf shelf = new Shelf(book.getGenre());
        shelf.addBook(book);
        shelves.add(shelf);
    }

    public void printAllBooks(){
        for (Shelf shelf : shelves){
            System.out.println("Книги жанра - " + shelf.getGenre().getName());
            shelf.printAllBooks();
            System.out.println("***************************");
        }
    }

    public Book findBook(String title) throws BookNotFoundException {
        for (Shelf shelf : shelves) {
            try {
                return shelf.findBook(title);
            } catch (BookNotFoundException e) {
                // не нашли на этой полке — идём дальше
            }
        }
        throw new BookNotFoundException("Книга '" + title + "' не найдена в библиотеке");
    }

    public ArrayList<Book> findBookByDate(LocalDate addedDate) throws BookNotFoundException {
        ArrayList<Book> foundBooks = new ArrayList<>();
        for (Shelf shelf : shelves) {
            try {
                foundBooks.addAll(shelf.findBooksByDate(addedDate));
            } catch (BookNotFoundException e) {
                // не нашли на этой полке — идём дальше
            }
        }
        throw new BookNotFoundException("Книги, добавленные " + addedDate.toString() + " не найдены в библиотеке");


    }

    public List<Book> findAuthor(String author) {
        List<Book>authorBooks = new ArrayList<>();
        for (Shelf shelf : shelves) {
           authorBooks.addAll(shelf.findAuthor(author));
        }
        return authorBooks;
    }

    public List<String> findAllAuthors(){
        List<String> authors = new ArrayList<>();
        for (Shelf shelf : shelves){
            for(String author : shelf.findAllAuthors()) {
                if(!authors.contains(author)) {
                    authors.add(author);
                }
            }
        }
        return authors;
    }

    public void printAllAuthors() {
        List<String> authors = findAllAuthors();
        Collections.sort(authors);
        if(authors.isEmpty()){
            System.out.println("Авторы не найдены");
        } else {
            System.out.println("Найденные авторы: ");
            for (String author : authors){
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
        for (Shelf shelf : shelves) {
            sum += shelf.countReadBooks();
        }
        return sum;
    }

    public List<Book> findUnreadBooks() {
        List<Book>unreadBooks = new ArrayList<>();
        for (Shelf shelf : shelves) {
            unreadBooks.addAll(shelf.findUnreadBooks());
        }
        return unreadBooks;
    }
    public void printUnreadBooks() {
        List<Book>unreadBooks = findUnreadBooks();
        if(!unreadBooks.isEmpty()){
            System.out.println("Список непрочитанных книг : ");
            for (Book book : unreadBooks){
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
        for (Shelf shelf : shelves) {
            totalPrice += shelf.getTotalPrice();
        }
        return totalPrice;
    }

    public void printTotalPrice(){
        System.out.println("Общая цена за книги в библиотеке: " + getTotalPrice());
    }

    public List<Book> findPurchasedBooks(){
        List<Book> purchasedBooks = new ArrayList<>();
        for (Shelf shelf : shelves){
            purchasedBooks.addAll(shelf.findPurchasedBooks());
        }
        return purchasedBooks;
    }
    public void printPurchasedBooks(){
        List<Book> purchasedBooks = findPurchasedBooks();
        if (!purchasedBooks.isEmpty()){
            System.out.println("Купленные книги: ");
            for (Book book : purchasedBooks) {
                System.out.println(book.getDescription());
            }
        } else {
            System.out.println("Купленных книг не найдено");
        }
    }
    public void removeBook(String title) {
        Iterator<Shelf> iterator = shelves.iterator();
        while (iterator.hasNext()) {
            Shelf shelf = iterator.next();
            if (shelf.removeBook(title) && shelf.getBooks().isEmpty()) {
                iterator.remove();
            }
        }
    }

    public void sortByAddedDate(){
        for (Shelf shelf : shelves) {
            shelf.sortByDate();
        }
    }



}



