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

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Главный класс, точка входа
 */
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


    /**
     * Метод для выгрузки данных из бд в xml файл
     *
     * @param fileName - название xml файла, в который будет выгружены данные
     */
    public static void uploadDB(String fileName) {
        DepartmentDao departmentDao = new DepartmentDaoImpl();
        try {
            rootLogger.info("Начало выгрузки данных в xml");
            List<Department> departments = departmentDao.findAll();

            XmlWriter xmlReader = new XmlWriter();
            xmlReader.writeToFile(fileName, departments);
            rootLogger.info("Выгрузка завершена");
        } catch (DaoException e) {
            rootLogger.info("Не возможно выгрузить");
            e.printStackTrace();
        }
    }


    /**
     * Метод для синхронизации данных xml файла и бд
     * @param fileName - название xml файла, с которого будут считаны данные
     */
    public static void syncDB(String fileName) {
        XmlReader xmlReader = new XmlReader();
        try {
            rootLogger.info("Начало синхронизации");

            Set<Department> xmlDepartments = xmlReader.parseXML(fileName);

            SynchronizedService synchronizedService = new SynchronizedService();
            synchronizedService.synchronize(xmlDepartments);

            rootLogger.info("Конец синхронизации");
        } catch (NotUniqueElementException e) {
            rootLogger.error("В файле существует два объекта с одинаковыми натуральными ключами");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
