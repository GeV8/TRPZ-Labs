package Facade;

import entity.Playlist;

import java.sql.SQLException;

public interface IMediaPlayerFacade {
    public void addSongToPlaylist(long idOfSong, long idOfPlaylist) throws SQLException;

    public void addSong(String name, String path, String genre) throws SQLException;

    public void createEqualizer(String name, int volume, int bassBooster) throws SQLException;

    public void createPlaylist(String name) throws SQLException;

    public void openPlaylist(long id) throws SQLException;
    public void openSong(long id);
    public void openEqualizer(long id) throws SQLException;


}
