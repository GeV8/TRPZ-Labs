package repository;

import entity.Playlist;
import entity.Song;

import java.util.List;

public interface IPlaylistRepository extends IRepository<Playlist> {
    List<Song> getSongsOfPlaylist(long id);

    Playlist addSongToPlaylist(long song, long playlist);

    Playlist deleteSongFromPlaylist(long song, long playlist);
}
