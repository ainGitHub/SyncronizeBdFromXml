package xml;


import db.domain.Department;
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
import java.util.ArrayList;
import java.util.List;

public class XmlReader {
    private DocumentBuilder documentBuilder;
    private Document document;

    public void createDocumentBuilder() throws ParserConfigurationException {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public void createDocument(String fileName) throws IOException, SAXException {
        document = documentBuilder.parse(new File(fileName));
    }


    public List<Department> parseXML(String fileName) {
        try {
            createDocumentBuilder();
            createDocument(fileName);
        } catch (ParserConfigurationException e) {
            return null;
        } catch (SAXException e) {
            return null;
        } catch (IOException e) {
            return null;
        }


        return getDepartments();
    }

    private List<Department> getDepartments() {
        NodeList childes = document.getElementsByTagName("department");

        List<Department> departments = new ArrayList<>();

        for (int i = 0; i < childes.getLength(); i++) {
            Node node = childes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                departments.add(createDepartment(element));
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
