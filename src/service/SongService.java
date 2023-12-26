package service;

import entity.Playlist;
import entity.Song;
import repository.ISongRepository;
import repository.MySQL.SongRepository;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class SongService {

    ISongRepository songRepository;

    Clip clipOfSong;

    int framePosition;

    Queue<Song> playlistSongs;

    public SongService(Connection connection) {
        this.songRepository = new SongRepository(connection);
    }


    public void playMusic(long id) {
        try {
            if (clipOfSong == null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(songRepository.getById(id).getPath()).getAbsoluteFile());
                clipOfSong = AudioSystem.getClip();
                clipOfSong.open(audioInputStream);
            } else {
                clipOfSong.close();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(songRepository.getById(id).getPath()).getAbsoluteFile());
                clipOfSong = AudioSystem.getClip();
                clipOfSong.open(audioInputStream);
            }
            clipOfSong.loop(Clip.LOOP_CONTINUOUSLY);
            clipOfSong.start();


        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void playFirstPlaylist(LinkedList<Song> linkedPlaylist) {
        playlistSongs = new LinkedList<>(linkedPlaylist);
        if (!playlistSongs.isEmpty()) {
            Song song = playlistSongs.poll();
            System.out.println(song);
            playSongFromPlaylist(song.getPath());
        }
    }

    public void playPlaylist() {
        if (!playlistSongs.isEmpty()) {
            Song song = playlistSongs.poll();
            System.out.println(song);
            playSongFromPlaylist(song.getPath());
        }
    }

    private void playSongFromPlaylist(String path) {
        try {
            if (clipOfSong == null) {
                createClipForPlaylist(path);
            } else {
                clipOfSong.close();
                createClipForPlaylist(path);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void createClipForPlaylist(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File audioFile = new File(path);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        clipOfSong = AudioSystem.getClip();
        clipOfSong.open(audioStream);

        clipOfSong.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP & getSeconds() == getPosition()) {
                playPlaylist();
            }
        });
        clipOfSong.start();
    }

    public void pauseMusic() {
        framePosition = clipOfSong.getFramePosition();
        clipOfSong.stop();
    }

    public Clip getClipOfSong() {
        return clipOfSong;
    }

    public void resumeSong() {
        clipOfSong.setFramePosition(framePosition);
        clipOfSong.start();

    }

    public void restartSong() {
        clipOfSong.stop();
        clipOfSong.setFramePosition(0);
        clipOfSong.start();
    }

    public void stopSong() {
        clipOfSong.stop();
        clipOfSong.close();
        clipOfSong = null;
    }

    public void repeatSong() {
        clipOfSong.loop(1);
    }


    public void deleteSong(long id) {
        songRepository.delete(id);
    }


    public Song addSong(Song song) {
        return songRepository.add(song);
    }

    public LinkedList<Song> getAllSongs() {
        return songRepository.getAll();
    }

    public long getSeconds() {
        return clipOfSong.getMicrosecondLength();
    }

    public long getPosition() {
        return clipOfSong.getMicrosecondPosition();
    }

    public void setSeconds(long position) {
        if (clipOfSong != null) {
            clipOfSong.setMicrosecondPosition(position);
        }
    }
}
