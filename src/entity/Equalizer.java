package entity;

public class Equalizer {
    private final long id;
    private String name;
    private int volume;



    public Equalizer(String name, int volume) {
        this.id = System.nanoTime();
        this.name = name;
        this.volume = volume;

    }

    public Equalizer(long id, String name, int volume) {
        this.id = id;
        this.name = name;
        this.volume = volume;

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





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
