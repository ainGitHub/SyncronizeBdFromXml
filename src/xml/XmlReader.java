package xml;


import db.domain.Department;
import exceptions.NotUniqueElementException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * Класс для считывания данных с файла и преоброзования их к виду Department
 */
public class XmlReader {
    private DocumentBuilder documentBuilder;
    private Document document;
    private static Logger logger = LogManager.getRootLogger();

    public void createDocumentBuilder() throws ParserConfigurationException {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public void createDocument(String fileName) throws IOException, SAXException {
        document = documentBuilder.parse(new File(fileName));
    }


    public Set<Department> parseXML(String fileName) throws NotUniqueElementException {
        try {
            createDocumentBuilder();
            createDocument(fileName);
        } catch (ParserConfigurationException e) {
            logger.info("Error" + e.getMessage());
            return null;
        } catch (SAXException e) {
            logger.info("Error" + e.getMessage());
            return null;
        } catch (IOException e) {
            logger.info("Error" + e.getMessage());
            return null;
        }


        return getDepartments();
    }

    private Set<Department> getDepartments() throws NotUniqueElementException {
        NodeList childes = document.getElementsByTagName("department");

        HashSet<Department> departments = new HashSet<>();

        for (int i = 0; i < childes.getLength(); i++) {
            Node node = childes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (!departments.add(createDepartment(element))) {
                    throw new NotUniqueElementException();
                }
            }
        }

        return departments;
    }

    public Department createDepartment(Element element) {
        String depCode = element.getElementsByTagName("depcode").item(0).getTextContent();
        String depJob = element.getElementsByTagName("depjob").item(0).getTextContent();
        String description = element.getElementsByTagName("description").item(0).getTextContent();

        Department department = new Department(depCode, depJob, description);

        return department;
    }
}
