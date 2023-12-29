package repository;


import java.util.LinkedList;


public interface IRepository<T> {
    T add(T t);

    T getById(long id);

    LinkedList<T> getAll();

    T delete(long id);

}
