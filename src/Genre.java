public enum Genre {
    SCIENCE_FICTION("Научная фантастика"),
    MYSTERY("Мистика"),
    HORROR("Ужасы"),
    ROMANCE("Романтика"),
    ADVENTURE("Приключения"),
    DRAMA("Драма");

        final String name;

    Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
