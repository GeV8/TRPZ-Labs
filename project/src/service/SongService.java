package service;

import entity.Song;
import repository.SongRepository;

public class SongService {
    Song songThatIsPlaying;
    boolean musicIsPlaying;
    boolean isSongRepeated;
    SongRepository songRepository;

    public void playMusic(long id) {
        songThatIsPlaying = songRepository.getById(id);
        musicIsPlaying = true;
    }

    public void pauseMusic(long id) {
        songThatIsPlaying = null;
        musicIsPlaying = false;
    }

    public void repeatSong(long id) {
        isSongRepeated = true;
    }

    public void deleteSong(long id) {
        songRepository.delete(id);
    }

    public void addSong(String name, String author, String genre, int duration, double size){
        Song newSong = new Song(name, author, genre, duration, size);
        songRepository.add(newSong);
    }
}
