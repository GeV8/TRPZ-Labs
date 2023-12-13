package service;

import entity.Playlist;
import entity.Song;
import repository.ISongRepository;

import java.sql.SQLException;

public class SongService {
    Song songThatIsPlaying = null;
    boolean musicIsPlaying = false;
    boolean isSongRepeated = false;
    ISongRepository songRepository;

    public SongService(ISongRepository songRepository) {
        this.songRepository = songRepository;
    }



    public void playMusic(long id) throws SQLException {
        songThatIsPlaying = songRepository.getById(id);
        musicIsPlaying = true;
    }

    public void pauseMusic() {
        songThatIsPlaying = null;
        musicIsPlaying = false;
    }

    public void repeatSong() {
        isSongRepeated = true;
    }

    public void deleteSong(long id) throws SQLException {
        songRepository.delete(id);
    }

    public Song getSongForAdding(long id) throws SQLException {
        return songRepository.getById(id);
    }

    public Song addSong(Song song) throws SQLException {
        return songRepository.add(song);
    }


}
