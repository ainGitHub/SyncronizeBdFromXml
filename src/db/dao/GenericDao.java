package db.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, K extends Serializable> {
    List<T> findAll() throws DaoException;

    void create(T t) throws DaoException;

    void delete(K id) throws DaoException;

    T find(K id) throws DaoException;

    void update(T t) throws DaoException;
}