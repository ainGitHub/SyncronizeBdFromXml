import db.dao.DaoException;
import db.dao.DepartmentDao;
import db.dao.impl.DepartmentDaoImpl;
import db.domain.Department;
import db.services.SynchronizedService;
import exceptions.NotUniqueElementException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xml.XmlReader;
import xml.XmlWriter;

import java.util.List;
import java.util.Set;

public class Main {
    private static final String SYNC_CODE = "sync";
    private static final String UPLOAD_CODE = "upload";

    static final Logger rootLogger = LogManager.getRootLogger();

    public static void main(String[] args) {
        if (args.length == 2) {
            String code = args[0];
            String fileName = args[1];

            switch (code) {
                case SYNC_CODE:
                    syncDB(fileName);
                    break;
                case UPLOAD_CODE:
                    uploadDB(fileName);
                    break;
            }
        }
    }

    public static void uploadDB(String fileName) {
        DepartmentDao departmentDao = new DepartmentDaoImpl();
        try {
            List<Department> departments = departmentDao.findAll();

            XmlWriter xmlReader = new XmlWriter();
            xmlReader.writeToFile(fileName, departments);
            rootLogger.info("Uploaded");
        } catch (DaoException e) {
            rootLogger.info("Can't upload");
            e.printStackTrace();
        }
    }

    public static void syncDB(String fileName) {
        XmlReader xmlReader = new XmlReader();
        try {
            Set<Department> xmlDepartments = xmlReader.parseXML(fileName);

            SynchronizedService synchronizedService = new SynchronizedService();
            synchronizedService.synchronize(xmlDepartments);
            rootLogger.info("Synchronized");
        } catch (NotUniqueElementException e) {
            rootLogger.error("В файле существует два объекта с одинаковыми натуральными ключами");
        }
    }
}
