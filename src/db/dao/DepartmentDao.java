package db.dao;

import db.domain.Department;

import java.util.List;

public interface DepartmentDao extends GenericDao<Department, Long> {
    @Override
    List<Department> findAll() throws DaoException;
}
