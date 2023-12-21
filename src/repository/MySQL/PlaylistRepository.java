package repository.MySQL;

import entity.Playlist;
import entity.Song;
import repository.IPlaylistRepository;

import java.sql.*;
import java.util.LinkedList;

public class PlaylistRepository implements IPlaylistRepository {

    Connection connection;

    private static final String addPlaylist = "INSERT INTO playlists(PlaylistName) values (?)";
    private static final String getById = "SELECT id, PlaylistName FROM playlists WHERE id=?";
    private static final String getAll = "SELECT Id, PlaylistName FROM playlists";
    private static final String delete = "DELETE FROM playlists where id=?";
    private static final String deleteAllSongsOfPlaylist = "DELETE FROM playlist_song where playlist_id=?";
    private static final String getSongsOfPlaylist = "SELECT * from songs join playlist_song ps on songs.id = ps.song_id where ps.playlist_id=?";
    private static final String addSongToPlaylist = "INSERT INTO playlist_song(playlist_id, song_id) value (?,?)";

    public PlaylistRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Playlist add(Playlist playlist) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(addPlaylist, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, playlist.getName());
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        return new Playlist(
                resultSet.getLong(1),
                playlist.getName()
        );
    }

    @Override
    public Playlist getById(long id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(getById);
        stmt.setLong(1, id);
        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();
        return new Playlist(
                resultSet.getString("PlaylistName"),
                resultSet.getLong("id"),
                getSongsOfPlaylist(id)
        );
    }

    @Override
    public LinkedList<Playlist> getAll() throws SQLException {
        LinkedList<Playlist> playlists = new LinkedList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(getAll);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            Playlist playlist = new Playlist(
                    resultSet.getString("PlaylistName"),
                    id,
                    getSongsOfPlaylist(id)
            );
            playlists.add(playlist);
        }
        return playlists;
    }

    @Override
    public Playlist delete(long id) throws SQLException {
        Playlist playlistToDelete = getById(id);
        PreparedStatement songPreparedStatement = connection.prepareStatement(deleteAllSongsOfPlaylist);
        songPreparedStatement.setLong(1, id);
        songPreparedStatement.execute();
        PreparedStatement playlistPreparedStatement = connection.prepareStatement(delete);
        playlistPreparedStatement.setLong(1, id);
        playlistPreparedStatement.execute();
        return playlistToDelete;
    }

    @Override
    public LinkedList<Song> getSongsOfPlaylist(long id) throws SQLException {
        LinkedList<Song> songOfPlaylist = new LinkedList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(getSongsOfPlaylist);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Song song = new Song(
                    resultSet.getString("songName"),
                    resultSet.getString("path"),
                    resultSet.getString("songGenre"),
                    resultSet.getLong("id")
            );
            songOfPlaylist.add(song);
        }
        return songOfPlaylist;
    }

    @Override
    public Playlist addSongToPlaylist(long idOfPlaylist, long idOfSong) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(addSongToPlaylist);
        preparedStatement.setLong(1, idOfPlaylist);
        preparedStatement.setLong(2, idOfSong);
        preparedStatement.execute();
        return getById(idOfPlaylist);
    }


}
