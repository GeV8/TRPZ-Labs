package repository;

import entity.Playlist;
import entity.Song;

import java.sql.SQLException;
import java.util.List;

public interface IPlaylistRepository extends IRepository<Playlist> {
    public List<Song> getSongsOfPlaylist(long id) throws SQLException;

    public Playlist addSongToPlaylist(long playlist, long song) throws SQLException;
}
