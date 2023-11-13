package service;

import Iterator.SongIterator;
import entity.Playlist;
import entity.Song;
import repository.PlaylistRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PlaylistService {
    PlaylistRepository playlistRepository;
    Playlist openedPlaylist;


    public void openPlaylist(long id) {
        openedPlaylist = playlistRepository.getById(id);
    }

    public void closePlaylist() {
        openedPlaylist = null;
    }

    public void addSongToPlaylist(long idOfPlaylist, Song song) {
        int duration = 0;
        Playlist playlist = playlistRepository.getById(idOfPlaylist);
        playlist.getSongs().add(song);
        countDurationAndSizeOfPlaylist(playlist);


    }

    public void removeSongFromPlaylist(Song song) {
        openedPlaylist.getSongs().remove(song);
        countDurationAndSizeOfPlaylist(openedPlaylist);
    }

    public void shufflePlaylist() {
        Collections.shuffle(openedPlaylist.getSongs());
    }

    public void createPlaylist(String name) {
        Playlist newPlaylist = new Playlist(name);
        playlistRepository.add(newPlaylist);
    }

    public void deletePlaylist() {
        playlistRepository.delete(openedPlaylist.getId());
        openedPlaylist = null;
    }

    private void countDurationAndSizeOfPlaylist(Playlist playlist) {
        int duration = 0;
        double size=0;
        Song songToIterate;
        SongIterator songIterator = new SongIterator(playlist.getSongs());
        while (songIterator.hasNext()) {
            songToIterate= songIterator.getNext();
            duration+=songToIterate.getDuration();
            size+=songToIterate.getSize();
        }
        playlist.setDuration(duration);
        playlist.setSize(size);
    }
}
