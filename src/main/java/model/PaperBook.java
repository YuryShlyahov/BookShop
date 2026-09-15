package model;

public class PaperBook extends Book {
    private static final double PRICE_FOR_PAGE = 2;

    public PaperBook(String title, String author, int pages, Genre genre) {
        super(title, author, pages, genre);
    }

    @Override
    public double getPrice() {
        return getPages() * PRICE_FOR_PAGE;
    }
}
