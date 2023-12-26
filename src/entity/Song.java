package entity;

public class Song {
    private final long id;
    private String name;
    private String genre;

    private String path;



    public Song(String name, String path, String genre) {
        this.genre = genre;
        id = System.nanoTime();
        this.name = name;
        this.path=path;
    }
    public Song(String name, String path, String genre, Long id) {
        this.genre = genre;
        this.id = id;
        this.name = name;
        this.path=path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return name +" ("+genre+")";
    }
}
