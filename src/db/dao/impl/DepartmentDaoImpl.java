package db.dao.impl;

import db.ConnectionFactory;
import db.dao.DaoException;
import db.dao.DepartmentDao;
import db.domain.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * ����� ��� ������ � ��
 */
public class DepartmentDaoImpl implements DepartmentDao {
    private static final String SQL_DEPARTMENTS = "SELECT * FROM department";
    private static final String SQL_CREATE_DEPARTMENT = "INSERT INTO department (depcode, depjob, description) VALUES (?, ?, ?)";


    @Override
    public List<Department> findAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Department> departments = null;
        try {
            connection = ConnectionFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_DEPARTMENTS);
            ResultSet resultSet = preparedStatement.executeQuery();
            departments = new ArrayList<>();
            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getLong("id"));
                department.setDepCode(resultSet.getString("depcode"));
                department.setDepJob(resultSet.getString("depjob"));
                department.setDescription(resultSet.getString("description"));
                departments.add(department);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return departments;
    }

    @Override
    public void create(Department department, Connection connection) throws DaoException {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_DEPARTMENT); // Ctr + 1 (atalho)
            statement.setString(1, department.getDepCode());
            statement.setString(2, department.getDepJob());
            statement.setString(3, department.getDescription());

            boolean result = statement.execute();
            if (result) {
                System.out.println("Data was insert successfully!!!");
            }
        } catch (Exception e) {
            System.out.println("Failure");
            //throw new NotUniqueFieldsException
            //throw new PersistenceExceptions(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id, Connection connection) throws DaoException {
        try {
            String sql = "DELETE FROM department WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new PersistenceExceptions(e.getMessage(), e);
        }
    }


    @Override
    public void update(Department newDepartment, Connection connection) throws DaoException {
        try {
            String sql = "UPDATE department SET"
                    + " depcode = ?, "
                    + " depjob = ?,"
                    + " description = ?"
                    + " WHERE id = ?";


            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newDepartment.getDepCode());
            statement.setString(2, newDepartment.getDepJob());
            statement.setString(3, newDepartment.getDescription());
            statement.setLong(4, newDepartment.getId());

            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new PersistenceExceptions(e.getMessage(), e);
        }
    }
}
