package db.services;

import db.ConnectionFactory;
import db.dao.DaoException;
import db.dao.impl.DepartmentDaoImpl;
import db.domain.Department;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * ����� ��� ������������� ������ � ����� � ��
 */
public class SynchronizedService {
    private Logger logger;

    /**
     * �������� ����� �������������
     *
     * @param xmlDepartments - ������ ��������� �� xml �����
     * @throws SQLException
     */
    public void synchronize(Set<Department> xmlDepartments) throws SQLException {
        DepartmentDaoImpl departmentDao = new DepartmentDaoImpl();
        List<Department> dbDepartments = null;
        try {
            dbDepartments = departmentDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }


        Connection connection = null;
        try {
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            if (xmlDepartments != null && dbDepartments != null) {

                for (Department xmlDepartment : xmlDepartments) {
                    boolean xmlDepExist = false; // ���� ��� �������� ������ ��������

                    Iterator dbIterator = dbDepartments.iterator();
                    while (dbIterator.hasNext()) {
                        Department dbDepartment = (Department) dbIterator.next();

                        if (xmlDepartment.equals(dbDepartment)) {
                            xmlDepExist = true;

                            if (!departmentsDescriptionEquals(xmlDepartment, dbDepartment)) {
                                xmlDepartment.setId(dbDepartment.getId());
                                departmentDao.update(xmlDepartment, connection);
                            }

                            dbIterator.remove();
                            break;
                        }
                    }

                    if (!xmlDepExist) {
                        logger.info("�������� ������ ��������");
                        departmentDao.create(xmlDepartment, connection);
                    }
                }

                checkAndDeleteDepartmentsInBd(departmentDao, dbDepartments, connection);
            }

            connection.commit();
            logger.info("������ ���� ��������� � ��");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("��������� ������, ������� ���� ���������");
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private void checkAndDeleteDepartmentsInBd(DepartmentDaoImpl departmentDao, List<Department> dbDepartments, Connection connection) throws DaoException {
        if (!dbDepartments.isEmpty()) {
            for (Department d : dbDepartments) {
                logger.info("�������� ��������, �������� ��� � xml �����");
                departmentDao.delete(d.getId(), connection);
            }
        }
    }

    /**
     * �������� ��������� ��������(description) ���� Department
     *
     * @param xmlDepartment - �� xml
     * @param dbDepartment  - �� ��
     * @return - true ���� �����, false ���� �� �����
     */
    public boolean departmentsDescriptionEquals(Department xmlDepartment, Department dbDepartment) {
        if (xmlDepartment.getDescription() != null && dbDepartment.getDescription() != null) {
            if (xmlDepartment.getDescription().equals(dbDepartment.getDescription())) {
                return true;
            }
        } else if (xmlDepartment.getDescription() == null && dbDepartment.getDescription() == null) {
            return true;
        }

        return false;
    }
}
