package entity;

public class Equalizer {
    private final long id;
    private String name;
    private int volume;
    private int bassBooster;


    public Equalizer(String name, int volume, int bassBooster) {
        this.id = System.nanoTime();
        this.name = name;
        this.volume = volume;
        this.bassBooster = bassBooster;
    }

    public Equalizer(long id, String name, int volume, int bassBooster) {
        this.id = id;
        this.name = name;
        this.volume = volume;
        this.bassBooster = bassBooster;
    }

    public long getId() {
        return id;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getBassBooster() {
        return bassBooster;
    }

    public void setBassBooster(int bassBooster) {
        this.bassBooster = bassBooster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
