package repository;

import entity.Playlist;
import entity.Song;

import java.sql.SQLException;
import java.util.List;

public interface IPlaylistRepository extends IRepository<Playlist> {
    public List<Song> getSongsOfPlaylist(long id) ;

    public Playlist addSongToPlaylist(long song, long playlist) ;
    public Playlist deleteSongFromPlaylist(long song, long playlist);
}
