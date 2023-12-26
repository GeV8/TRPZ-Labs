package repository;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public interface IRepository<T> {
    T add(T t) ;

    T getById(long id) ;

    LinkedList<T> getAll() ;

    T delete(long id) ;

}
