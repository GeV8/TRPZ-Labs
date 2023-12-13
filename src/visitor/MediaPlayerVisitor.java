package visitor;

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
        PlaylistShuffleCommand playlistShuffleCommand = new PlaylistShuffleCommand(playlistService);
        while (songIterator.hasNext()) {
            visitSong(songIterator.getNext());

        }
    }

    @Override
    public void visitSong(Song song) {
        System.out.println(song.getName() + " " + song.getGenre());
        SongPlayCommand songPlayCommand = new SongPlayCommand(song, songService);
        SongDeleteCommand songDeleteCommand = new SongDeleteCommand(song, songService);
    }

    @Override
    public void visitEqualizer(Equalizer equalizer) {
        System.out.println(equalizer.getName());
        System.out.println(equalizer.getBassBooster());
        System.out.println(equalizer.getBassBooster());
    }
}
