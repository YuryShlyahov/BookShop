import model.AudioBook;
import model.EBook;
import model.Genre;
import model.PaperBook;
import service.Library;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new PaperBook("451 Градус по Фаренгейту", "Р.Брэдберри", 500, Genre.SCIENCE_FICTION));
        library.addBook(new PaperBook("Остров сокровищ", "Р.Л.Стивенсон", 700, Genre.ADVENTURE));
        library.addBook(new PaperBook("Каменный город", "Д.Мартин", 1200, Genre.SCIENCE_FICTION));
        library.addBook(new PaperBook("Долгий джонт", "С.Кинг", 120, Genre.SCIENCE_FICTION));
        library.addBook(new PaperBook("Мизери", "С.Кинг", 220, Genre.HORROR));
        library.addBook(new PaperBook("1963", "С.Кинг", 520, Genre.ADVENTURE));
        library.addBook(new PaperBook("Вера наших отцов", "Ф.К.Дик", 890, Genre.SCIENCE_FICTION));
        library.addBook(new EBook("Эрикс", "Р.Шекли", 845, Genre.SCIENCE_FICTION, 5.1));
        library.addBook(new EBook("Эротофобия", "Х.Элисон", 250, Genre.SCIENCE_FICTION, 1.0));
        library.addBook(new AudioBook("Принц Госплана", "В.Пелевин", 620, Genre.SCIENCE_FICTION, 120));
        library.addBook(new AudioBook("Срок Авансом", "У.Тенн", 350, Genre.SCIENCE_FICTION, 100));
        library.addBook(new AudioBook("Унесенные ветром", "М.Митчелл", 1350, Genre.DRAMA, 100));
        library.addBook(new PaperBook("Мастер и Маргарита", "М.Булгаков", 900, Genre.MYSTERY));
        library.addBook(new PaperBook("Дневник Памяти", "Н.Спаркс", 400, Genre.ROMANCE));

        library.printAllBooks();
        library.sortByAddedDate();
        library.printAllBooks();
    }
}
