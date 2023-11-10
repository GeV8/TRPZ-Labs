package entity;

import Iterator.PlaylistIterator;
import Iterator.SongIterator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Playlist {
    private final long id;
    private String name;
    private int duration;
    private double size;

    private ArrayList<Song> songs;

    public Playlist(String name) {
        this.id = System.nanoTime();
        this.name = name;
        this.duration = 0;
        this.size = 0;
        this.songs = new ArrayList<>();
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

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
}
