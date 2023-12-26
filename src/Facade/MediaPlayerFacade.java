package Facade;

import Iterator.PlaylistIterator;
import command.PlaylistCommand.PlaylistShuffleCommand;
import entity.Playlist;
import entity.Song;

import service.EqualizerService;
import service.PlaylistService;
import service.SongService;
import visitor.MediaPlayerVisitor;

import java.sql.Connection;
import java.util.LinkedList;

public class MediaPlayerFacade implements IMediaPlayerFacade {

    private final PlaylistService playlistService;
    private final SongService songService;
    private final EqualizerService equalizerService;
    private final MediaPlayerVisitor mediaPlayerVisitor;

    private boolean musicIsPlaying = false;


    public MediaPlayerFacade(Connection connection) {
        this.playlistService = new PlaylistService(connection);
        this.songService = new SongService(connection);
        this.equalizerService = new EqualizerService();
        this.mediaPlayerVisitor = new MediaPlayerVisitor(equalizerService, playlistService, songService);
    }

    @Override
    public void createPlaylist(String name) {
        playlistService.createPlaylist(new Playlist(name));
    }

    @Override
    public void openPlaylist(long id) {
        playlistService.openPlaylist(id);

    }

    @Override
    public void openSong(long id) {
        mediaPlayerVisitor.visitSong(playlistService.openSongFromPlaylist(id));
    }

    @Override
    public void openEqualizer(long id) {

    }

    @Override
    public LinkedList<Playlist> getAllPlaylist() {
        return playlistService.getAllPlaylist();
    }

    @Override
    public Playlist getChoosenPlaylist() {
        return playlistService.getOpenedPlaylist();
    }

    @Override
    public void playSong(long id) {
        songService.playMusic(id);
        musicIsPlaying = true;
    }

    @Override
    public void pauseSong() {
        if (musicIsPlaying) {
            songService.pauseMusic();
            musicIsPlaying = false;
        }
    }

    @Override
    public void resumeSong() {
        if (!musicIsPlaying) {
            songService.resumeSong();
            musicIsPlaying = true;
        }
    }

    @Override
    public void restartSong() {
        songService.restartSong();
    }

    @Override
    public void stopSong() {
        songService.stopSong();
        musicIsPlaying = false;
    }

    @Override
    public void loopSong() {
        songService.repeatSong();
    }



    @Override
    public PlaylistShuffleCommand generateShuffleCommand() {
        return new PlaylistShuffleCommand(playlistService);
    }


    @Override
    public Song addSong(String name, String path, String genre) {
        return songService.addSong(new Song(name, path, genre));
    }

    @Override
    public void addSongToPlaylist(long idOfSong, long idOfPlaylist) {
        playlistService.addSongToPlaylist(idOfSong, idOfPlaylist);
    }

    @Override
    public boolean isMusicIsPlaying() {
        return musicIsPlaying;
    }

    @Override
    public LinkedList<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @Override
    public void removeSongFromPlaylist(long songId, long playlistId) {
        playlistService.removeSongFromPlaylist(songId, playlistId);
    }

    @Override
    public void changeVolume(float value) {
        equalizerService.changeVolume(songService.getClipOfSong(), value);
    }

    @Override
    public long getSeconds() {
        return songService.getSeconds();
    }

    @Override
    public void setSeconds(long position) {
        songService.setSeconds(position);
    }

    @Override
    public long getPosition() {
        return songService.getPosition();
    }

    @Override
    public void playPlaylist(Playlist playlist) {
        songService.playFirstPlaylist(playlist.getSongs());
        musicIsPlaying = true;
    }

    @Override
    public void deletePlaylist() {
        playlistService.deletePlaylist();
    }

    @Override
    public void deleteSong(Song song) {
        PlaylistIterator playlistIterator = new PlaylistIterator(getAllPlaylist());
        while(playlistIterator.hasNext()){
            removeSongFromPlaylist(song.getId(), playlistIterator.getNext().getId());
        }
        songService.deleteSong(song.getId());
    }
}
