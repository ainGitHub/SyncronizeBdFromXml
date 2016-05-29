package db.services;

import db.ConnectionFactory;
import db.dao.DaoException;
import db.dao.impl.DepartmentDaoImpl;
import db.domain.Department;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SynchronizedService {


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
                Iterator xmlIterator = xmlDepartments.iterator();

                while (xmlIterator.hasNext()) {
                    Department xmlDepartment = (Department) xmlIterator.next();
                    boolean xmlDepExist = false;

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
                        System.out.println("Created" + xmlDepartment.getDepCode() + " " + xmlDepartment.getDepJob());
                        departmentDao.create(xmlDepartment, connection);
                    }
                }

                if (!dbDepartments.isEmpty()) {
                    for (Department d : dbDepartments) {
                        System.out.println("Deleted" + d.getDepCode() + " " + d.getDepJob());
                        departmentDao.delete(d.getId(), connection);
                    }
                }
            }
            connection.commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            connection.close();
        }
    }

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
