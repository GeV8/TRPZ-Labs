package repository.MySQL;

import entity.Equalizer;
import repository.IEqualizerRepository;

import java.sql.*;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PropertyPermission;

public class EqualizerRepository implements IEqualizerRepository {

    Connection connection;
    private static final String addEqualizer = "INSERT INTO equalizers(volume, bassBooster, name) values (?,?,?)";
    private static final String getById = "SELECT id, name, volume, bassbooster  FROM equalizers WHERE id=?";
    private static final String getAll = "SELECT id, name, volume, bassbooster FROM equalizers";
    private static final String delete = "DELETE FROM equalizers where id=?";

    public EqualizerRepository(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public Equalizer add(Equalizer equalizer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(addEqualizer, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, equalizer.getVolume());
        preparedStatement.setInt(2, equalizer.getBassBooster());
        preparedStatement.setString(3, equalizer.getName());
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();


        return new Equalizer(
                resultSet.getLong(1),
                equalizer.getName(),
                equalizer.getVolume(),
                equalizer.getBassBooster()
        );
    }

    @Override
    public Equalizer getById(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(getById);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Equalizer(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getInt(3),
                resultSet.getInt(4)
        );
    }

    @Override
    public List<Equalizer> getAll() throws SQLException {
        LinkedList<Equalizer> equalizerList = new LinkedList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(getAll);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Equalizer equalizer = new Equalizer(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4)
            );
            equalizerList.add(equalizer);
        }
        return equalizerList;
    }

    @Override
    public Equalizer delete(long id) throws SQLException {
        Equalizer equalizerToDelete = getById(id);
        PreparedStatement preparedStatement = connection.prepareStatement(delete);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        return equalizerToDelete;
    }
}
