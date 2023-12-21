package visitor;

import entity.Equalizer;
import entity.Playlist;
import entity.Song;

import java.io.PrintWriter;
import java.util.LinkedList;



public interface IMediaPlayerVisitor {
    public void visitPlaylist(Playlist playlist);
    public void visitSong(Song song);
    public void visitEqualizer(Equalizer equalizer);

    public void visitAllPlaylists(LinkedList<Playlist> playlists);
}
