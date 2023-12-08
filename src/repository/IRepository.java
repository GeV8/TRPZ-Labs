package repository;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T> {
    T add(T t) throws SQLException;

    T getById(long id) throws SQLException;

    List<T> getAll() throws SQLException;

    T delete(long id) throws SQLException;

}
