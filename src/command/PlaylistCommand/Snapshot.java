package command.PlaylistCommand;

import entity.Playlist;
import entity.Song;

import java.util.ArrayList;
import java.util.LinkedList;

public class Snapshot {
    private Playlist playlist;

    private LinkedList<Song> songs;

    public Snapshot(Playlist playlist, LinkedList<Song> songs) {
        this.playlist = playlist;
        this.songs = new LinkedList<>(songs);
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public LinkedList<Song> getSongs() {
        return songs;
    }

    public void setSongs(LinkedList<Song> songs) {
        this.songs = songs;
    }

    public void restore(){
        playlist.setSongs(songs);
    }
}
