package repository;

import java.util.List;

public interface IRepository<T> {
    T add(T t);
    T getById(long id);
    List<T> getAll();
    T delete(long id);

}
