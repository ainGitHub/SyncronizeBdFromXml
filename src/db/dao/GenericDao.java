package db.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public interface GenericDao<T, K extends Serializable> {
    List<T> findAll() throws DaoException;

    void create(T t, Connection connection) throws DaoException;

    void delete(K id, Connection connection) throws DaoException;

    T find(K id, Connection connection) throws DaoException;

    void update(T t, Connection connection) throws DaoException;
}