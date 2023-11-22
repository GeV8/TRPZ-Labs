package command.PlaylistCommand;

import entity.Playlist;
import entity.Song;

import java.util.ArrayList;

public class Snapshot {
    private Playlist playlist;

    private ArrayList<Song> songs;

    public Snapshot(Playlist playlist, ArrayList<Song> songs) {
        this.playlist = playlist;
        this.songs = new ArrayList<>(songs);
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public void restore(){
        playlist.setSongs(songs);
    }
}
