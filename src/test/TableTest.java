package test;

import db.ConnectionFactory;
import db.dao.DaoException;
import db.dao.impl.DepartmentDaoImpl;
import db.domain.Department;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableTest {
    private final static String createTableQuery = "CREATE TABLE department (" +
            "  id BIGSERIAL PRIMARY KEY ," +
            "  DepCode VARCHAR(20) DEFAULT NULL," +
            "  DepJob VARCHAR(100) DEFAULT NULL," +
            "  Description VARCHAR(255) DEFAULT NULL," +
            "  UNIQUE(DepCode, DepJob)" +
            ");";


    public static void main(String[] args) {
        findAllTest();
    }

    public static void delteTest() {
        DepartmentDaoImpl departmentDao = new DepartmentDaoImpl();
        try {
            departmentDao.delete(4l);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void createTest() {
        DepartmentDaoImpl departmentDao = new DepartmentDaoImpl();
        Department department = new Department("dep1", "job1", "Description");

        try {
            departmentDao.create(department);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void findAllTest() {
        DepartmentDaoImpl departmentDao = new DepartmentDaoImpl();
        try {
            for (Department department : departmentDao.findAll()) {
                System.out.println(department.getId());
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void createTable() {
        Statement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionFactory.getInstance().getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(createTableQuery);
            System.out.println("Connection success");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            //позакрываем теперь все
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
