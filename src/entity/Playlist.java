package entity;

import Iterator.PlaylistIterator;
import Iterator.SongIterator;
import command.PlaylistCommand.Snapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class Playlist {
    private final long id;
    private String name;


    private LinkedList<Song> songs;

    public Playlist(String name) {
        this.id = System.nanoTime();
        this.name = name;
        this.songs = new LinkedList<>();
    }
    public Playlist(String name, long id, LinkedList<Song> list) {
        this.id = id;
        this.name = name;
        this.songs = list;
    }

    public Playlist(long id, String name) {
        this.id = id;
        this.name = name;
        this.songs=new LinkedList<>();
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

    public LinkedList<Song> getSongs() {
        return songs;
    }

    public void setSongs(LinkedList<Song> songs) {
        this.songs = songs;
    }

    public Snapshot createSnapshot() {
        return new Snapshot(this, this.songs);
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", name='" + name + "}";
    }
}
