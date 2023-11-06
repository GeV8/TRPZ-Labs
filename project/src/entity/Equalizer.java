package entity;

public class Equalizer {
    private final long id;
    private int volume;
    private int bassBooster;
    private int soundBooster;

    public Equalizer(long id, int volume, int bassBooster, int soundBooster) {
        this.id = System.nanoTime();
        this.volume = volume;
        this.bassBooster = bassBooster;
        this.soundBooster = soundBooster;
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

    public int getSoundBooster() {
        return soundBooster;
    }

    public void setSoundBooster(int soundBooster) {
        this.soundBooster = soundBooster;
    }
}
