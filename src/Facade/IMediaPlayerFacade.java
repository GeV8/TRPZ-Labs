package Facade;

import command.PlaylistCommand.PlaylistShuffleCommand;
import entity.Playlist;
import entity.Song;

import java.util.LinkedList;

public interface IMediaPlayerFacade {
    void addSongToPlaylist(long idOfSong, long idOfPlaylist);

    Song addSong(String name, String path, String genre);


     void createPlaylist(String name);

     void openPlaylist(long id);

     void openSong(long id);

     void openEqualizer(long id);

     LinkedList<Playlist> getAllPlaylist();

     Playlist getChoosenPlaylist();

     void playSong(long id);

     void pauseSong();

     void resumeSong();

     void restartSong();

     void stopSong();

     void loopSong();


     PlaylistShuffleCommand generateShuffleCommand();
     boolean isMusicIsPlaying();
     LinkedList<Song> getAllSongs();
     void removeSongFromPlaylist(long songId, long playlistId);
     void changeVolume(int value);
     long getSeconds();
     void setSeconds(int value);
     long getPosition();
     void playPlaylist(Playlist playlist);
     void deletePlaylist();

     void deleteSong(Song song);
}
