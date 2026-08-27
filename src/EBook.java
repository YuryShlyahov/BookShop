public class EBook extends Book implements Discountable {
    private double fileSize;
    private static final double PRICE_FOR_MB = 10;

    public EBook(String title, String author, int pages, Genre genre, double fileSize) {
        super(title, author, pages, genre);
        this.fileSize = fileSize;
    }

    public double getFileSize() {
        return fileSize;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Электронная версия, " + getFileSize() + " МБ";
    }

    @Override
    public String getType() {
        return "EBook";
    }

    @Override
    public double getPrice() {
        return getFileSize() * PRICE_FOR_MB * (100 - getDiscount()) / 100;
    }

    @Override
    public void applyDiscount(int percent) {
        setDiscount(percent);
    }
}
