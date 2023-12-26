package service;

import entity.Playlist;
import entity.Song;
import repository.IPlaylistRepository;
import repository.MySQL.PlaylistRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PlaylistService {
    IPlaylistRepository playlistRepository;
    Playlist openedPlaylist;

    public PlaylistService(Connection connection) {
        this.playlistRepository = new PlaylistRepository(connection);
    }

    public void openPlaylist(long id)  {
        openedPlaylist = playlistRepository.getById(id);
    }

    public Song openSongFromPlaylist(long id){
        return openedPlaylist.getSongs().stream().filter(s -> s.getId()==id).findFirst().get();
    }

    public void removeSongFromPlaylist(long songId, long playlistId) {
        playlistRepository.deleteSongFromPlaylist(songId, playlistId);
    }

    public void shufflePlaylist() {
        Collections.shuffle(openedPlaylist.getSongs());
    }

    public void createPlaylist(Playlist playlist)  {
        playlistRepository.add(playlist);
    }

    public void deletePlaylist()  {
        playlistRepository.delete(openedPlaylist.getId());
        openedPlaylist = null;
    }


    public Playlist getOpenedPlaylist() {
        return openedPlaylist;
    }

    public void addSongToPlaylist(long idOfSong, long idOfPlaylist )  {
        playlistRepository.addSongToPlaylist(idOfSong,  idOfPlaylist);
    }
    public LinkedList<Playlist> getAllPlaylist()  {
        return playlistRepository.getAll();
    }
}
