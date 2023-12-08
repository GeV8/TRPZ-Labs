package repository.MySQL;

import entity.Song;
import repository.ISongRepository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SongRepository implements ISongRepository {
    Connection connection;

    private static final String addSong = "INSERT INTO songs(songGenre, SongName, path) values (?,?,?)";
    private static final String getById = "SELECT id,songGenre, SongName, path FROM songs WHERE id=?";
    private static final String getAll = "SELECT id, songGenre, SongName, path FROM songs";
    private static final String delete = "DELETE FROM songs where id=?";

    public SongRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Song add(Song song) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(addSong, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, song.getGenre());
        preparedStatement.setString(2, song.getName());
        preparedStatement.setString(3, song.getPath());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        return new Song(
                song.getName(),
                song.getPath(),
                song.getGenre(),
                resultSet.getLong(1)
        );
    }

    @Override
    public Song getById(long id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(getById);
        stmt.setLong(1, id);
        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();
        return new Song(
                resultSet.getString("songName"),
                resultSet.getString("path"),
                resultSet.getString("songGenre"),
                resultSet.getLong("id")
        );
    }

    @Override
    public List<Song> getAll() throws SQLException {
        LinkedList<Song> songLinkedList = new LinkedList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(getAll);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Song song = new Song(
                    resultSet.getString("songName"),
                    resultSet.getString("path"),
                    resultSet.getString("songGenre"),
                    resultSet.getLong("id"));
            songLinkedList.add(song);
        }
        return songLinkedList;
    }

    @Override
    public Song delete(long id) throws SQLException {
        Song songToDelete = getById(id);
        PreparedStatement statement = connection.prepareStatement(delete);
        statement.setLong(1, id);
        statement.execute();
        return songToDelete;
    }


}
