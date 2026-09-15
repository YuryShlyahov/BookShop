package model;

import java.time.LocalDate;

public abstract class Book implements Purchasable {
    private String title; // название книги
    private String author; // автор
    private int pages; // количество страниц
    private boolean isRead;
    private boolean isPurchased;// прочитана ли книга (по умолчанию false)
    private int discount;
    private Genre genre;
    private LocalDate addedDate;

    public Book(String title, String author, int pages, Genre genre) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.genre = genre;
        this.isRead = false;
        this.isPurchased = false;
        this.discount = 0;
        this.addedDate = LocalDate.now();
    }
//комментарий
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setDiscount(int discount) {
        if (discount < 0) discount = 0;
        if (discount > 100) discount = 100;
        this.discount = discount;
    }

    public void markAsRead() {
        isRead = true;
    }

    public int getDiscount() {
        return discount;
    }

    public String getDescription() {
        String status = "Не прочитана";
        String purchaseStatus = "не куплена";
        if (isRead) {
            status = "Прочитана";
        }
        if (isPurchased) {
            purchaseStatus = "куплена";
        }

        return status + ". Жанр -> " + getGenre().getName() + " || Книга: \"" + getTitle() + "\" (" + getAuthor() + ", " + getPages() + " стр.), цена "
                + getPrice() + " руб., " + purchaseStatus + ". " + "Дата добавления  - " + getAddedDate().toString();
    }

    public Genre getGenre() {
        return genre;
    }

    public abstract double getPrice();

    @Override
    public void buy() {
        isPurchased = true;
    }


}
