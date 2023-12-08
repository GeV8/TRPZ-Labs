package Facade;

import entity.Equalizer;
import entity.Playlist;
import entity.Song;
import repository.IPlaylistRepository;
import repository.MySQL.EqualizerRepository;
import repository.MySQL.PlaylistRepository;
import repository.MySQL.SongRepository;
import service.EqualizerService;
import service.PlaylistService;
import service.SongService;

import java.sql.Connection;
import java.sql.SQLException;

public class MediaPlayerFacade implements IMediaPlayerFacade {

    private final PlaylistService playlistService;
    private final SongService songService;
    private final EqualizerService equalizerService;


    public MediaPlayerFacade(Connection connection) throws SQLException {
        this.playlistService = new PlaylistService(new PlaylistRepository(connection));
        this.songService = new SongService(new SongRepository(connection));
        this.equalizerService = new EqualizerService(new EqualizerRepository(connection));
    }

    @Override
    public void createPlaylist(String name) throws SQLException {
        playlistService.createPlaylist(new Playlist(name));
    }

    @Override
    public void addSong(String name, String path, String genre) throws SQLException {
        songService.addSong(new Song(name, path, genre));
    }

    @Override
    public void createEqualizer(String name, int volume, int bassBooster) throws SQLException {
        equalizerService.addNewEqualizer(new Equalizer(name, volume, bassBooster));
    }

    @Override
    public void addSongToPlaylist(long idOfSong, long idOfPlaylist) throws SQLException {
        playlistService.addSongToPlaylist(idOfPlaylist, idOfSong);
    }
}
