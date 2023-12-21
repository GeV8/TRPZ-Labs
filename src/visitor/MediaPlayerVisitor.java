package visitor;

import Iterator.PlaylistIterator;
import Iterator.SongIterator;
import command.PlaylistCommand.PlaylistShuffleCommand;
import command.SongCommand.SongDeleteCommand;
import command.SongCommand.SongPlayCommand;
import entity.Equalizer;
import entity.Playlist;
import entity.Song;
import service.EqualizerService;
import service.PlaylistService;
import service.SongService;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class MediaPlayerVisitor implements IMediaPlayerVisitor {
    EqualizerService equalizerService;
    PlaylistService playlistService;
    SongService songService;

    public MediaPlayerVisitor(EqualizerService equalizerService, PlaylistService playlistService, SongService songService) {
        this.equalizerService = equalizerService;
        this.playlistService = playlistService;
        this.songService = songService;
    }

    @Override
    public void visitPlaylist(Playlist playlist) {
        System.out.println(playlist.getName());
        System.out.println("Пісні плейліста:");
        SongIterator songIterator = new SongIterator(playlist.getSongs());
        while (songIterator.hasNext()) {
            visitSong(songIterator.getNext());

        }
    }

    @Override
    public void visitSong(Song song) {
        System.out.println(song.getName() + " " + song.getGenre());
    }

    @Override
    public void visitEqualizer(Equalizer equalizer) {
        System.out.println(equalizer.getName());
        System.out.println(equalizer.getBassBooster());
        System.out.println(equalizer.getBassBooster());
    }

    @Override
    public void visitAllPlaylists(LinkedList<Playlist> playlists) {
        PlaylistIterator playlistIterator = new PlaylistIterator(playlists);
    }
}
