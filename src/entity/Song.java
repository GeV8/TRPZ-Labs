package entity;

public class Song {
    private final long id;
    private String name;
    private String author;
    private String genre;
    private int duration;
    private double size;

    public Song(String name, String author, String genre, int duration, double size) {
        this.genre = genre;
        id = System.nanoTime();
        this.name = name;
        this.author = author;
        this.duration = duration;
        this.size = size;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
