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


    public void synchronize(Set<Department> xmlDepartments) {
        DepartmentDaoImpl departmentDao = new DepartmentDaoImpl();
        List<Department> dbDepartments = null;
        try {
            dbDepartments = departmentDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }


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

                        }

                        dbIterator.remove();
                        break;
                    }

                }

                if (!xmlDepExist) {
                    System.out.println("Created" + xmlDepartment.getDepCode() + " " + xmlDepartment.getDepJob());
                    //create
                }
            }

            if (!dbDepartments.isEmpty()) {
                for (Department d : dbDepartments) {
                    System.out.println("Deleted" + d.getDepCode() + " " + d.getDepJob());
                    //delete
                }
            }
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
