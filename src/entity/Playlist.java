package entity;


import command.PlaylistCommand.Snapshot;

import java.io.Serializable;

import java.util.LinkedList;

public class Playlist implements Serializable {
    private final long id;
    private String name;
    private static final long serialVersionUID = 1L;


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
        this.songs = new LinkedList<>();
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
        return name;
    }
}
