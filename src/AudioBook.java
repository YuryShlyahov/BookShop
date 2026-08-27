public class AudioBook extends Book implements Discountable{
    private int duration;
    private static final double PRICE_FOR_MINUTE = 1.5;

    public AudioBook(String title, String author, int pages, Genre genre, int duration) {
        super(title, author, pages, genre);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Аудиокнига, " + getDuration() + " мин.";
    }

    @Override
    public String getType() {
        return "AudioBook";
    }

    @Override
    public double getPrice() {
        return getDuration() * PRICE_FOR_MINUTE * (100 - getDiscount()) / 100;
    }

    @Override
    public void applyDiscount(int percent) {
            setDiscount(percent);
    }
}
