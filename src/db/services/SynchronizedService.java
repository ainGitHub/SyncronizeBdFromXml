package db.services;

import db.ConnectionFactory;
import db.dao.DaoException;
import db.dao.impl.DepartmentDaoImpl;
import db.domain.Department;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Класс для синхронизации данных с файла и бд
 */
public class SynchronizedService {
    private Logger logger = LogManager.getRootLogger();

    /**
     * Основной метод синхронизации
     *
     * @param xmlDepartments - данные считанные из xml файла
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
                    boolean xmlDepExist = false; // флаг для создания нового элемента

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
                        logger.info("Создание нового элемента");
                        departmentDao.create(xmlDepartment, connection);
                    }
                }

                checkAndDeleteDepartmentsInBd(departmentDao, dbDepartments, connection);
            }

            connection.commit();
            logger.info("Запись всех изменений в бд");

        } catch (ClassNotFoundException e) {
            logger.info("Произошла ошибка");
            e.printStackTrace();
        } catch (Exception e) {
            logger.info("Произошла ошибка, возврат всех изменений");
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
                logger.info("Удаление элемента, которого нет в xml файле");
                departmentDao.delete(d.getId(), connection);
            }
        }
    }

    /**
     * Проверка равентсва описаний(description) двух Department
     *
     * @param xmlDepartment - из xml
     * @param dbDepartment  - из бд
     * @return - true если равны, false если не равны
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
