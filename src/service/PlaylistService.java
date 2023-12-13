package service;

import entity.Playlist;
import entity.Song;
import repository.IPlaylistRepository;

import java.sql.SQLException;
import java.util.Collections;

public class PlaylistService {
    IPlaylistRepository playlistRepository;
    Playlist openedPlaylist;

    public PlaylistService(IPlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public void openPlaylist(long id) throws SQLException {
        openedPlaylist = playlistRepository.getById(id);
    }

    public Song openSongFromPlaylist(long id){
        return openedPlaylist.getSongs().stream().filter(s -> s.getId()==id).findFirst().get();
    }

    public void closePlaylist() {
        openedPlaylist = null;
    }

    public Playlist getPlaylistForAddingSong(long idOfPlaylist) throws SQLException {
        return playlistRepository.getById(idOfPlaylist);
    }

    public void removeSongFromPlaylist(Song song) {
        openedPlaylist.getSongs().remove(song);
    }

    public void shufflePlaylist() {
        Collections.shuffle(openedPlaylist.getSongs());
    }

    public void createPlaylist(Playlist playlist) throws SQLException {
        playlistRepository.add(playlist);
    }

    public void deletePlaylist() throws SQLException {
        playlistRepository.delete(openedPlaylist.getId());
        openedPlaylist = null;
    }


    public Playlist getOpenedPlaylist() {
        return openedPlaylist;
    }

    public void addSongToPlaylist(long idOfPlaylist, long idOfSong) throws SQLException {
        playlistRepository.addSongToPlaylist(idOfPlaylist, idOfSong);
    }
}
