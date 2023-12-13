package Facade;

import entity.Equalizer;
import entity.Playlist;
import entity.Song;
import repository.MySQL.EqualizerRepository;
import repository.MySQL.PlaylistRepository;
import repository.MySQL.SongRepository;
import service.EqualizerService;
import service.PlaylistService;
import service.SongService;
import visitor.MediaPlayerVisitor;

import java.sql.Connection;
import java.sql.SQLException;

public class MediaPlayerFacade implements IMediaPlayerFacade {

    private final PlaylistService playlistService;
    private final SongService songService;
    private final EqualizerService equalizerService;
    private final MediaPlayerVisitor mediaPlayerVisitor;


    public MediaPlayerFacade(Connection connection) throws SQLException {
        this.playlistService = new PlaylistService(new PlaylistRepository(connection));
        this.songService = new SongService(new SongRepository(connection));
        this.equalizerService = new EqualizerService(new EqualizerRepository(connection));
        this.mediaPlayerVisitor = new MediaPlayerVisitor(equalizerService, playlistService, songService);
    }

    @Override
    public void createPlaylist(String name) throws SQLException {
        playlistService.createPlaylist(new Playlist(name));
    }

    @Override
    public void openPlaylist(long id) throws SQLException {
        playlistService.openPlaylist(id);
        mediaPlayerVisitor.visitPlaylist(playlistService.getOpenedPlaylist());
    }

    @Override
    public void openSong(long id) {
        mediaPlayerVisitor.visitSong(playlistService.openSongFromPlaylist(id));
    }

    @Override
    public void openEqualizer(long id) throws SQLException {
        equalizerService.chooseEqualizer(id);
        mediaPlayerVisitor.visitEqualizer(equalizerService.getChosenEqualizer());
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
