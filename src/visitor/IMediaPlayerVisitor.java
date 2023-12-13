package visitor;

import entity.Equalizer;
import entity.Playlist;
import entity.Song;


public interface IMediaPlayerVisitor {
    public void visitPlaylist(Playlist playlist);
    public void visitSong(Song song);
    public void visitEqualizer(Equalizer equalizer);
}
