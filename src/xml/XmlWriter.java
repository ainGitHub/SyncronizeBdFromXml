package xml;

import db.domain.Department;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XmlWriter {
    private DocumentBuilder documentBuilder;
    private Document document;

    private void createDocumentBuilder() throws ParserConfigurationException {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    private void createDocument() {
        document = documentBuilder.newDocument();
    }


    private Element createDepartmentElement(Department department) {
        Element departmentElement = document.createElement("department");

        Element depcode = document.createElement("depcode");
        depcode.appendChild(document.createTextNode(department.getDepCode()));
        departmentElement.appendChild(depcode);

        Element depjob = document.createElement("depjob");
        depjob.appendChild(document.createTextNode(department.getDepJob()));
        departmentElement.appendChild(depjob);

        Element description = document.createElement("description");
        if (department.getDescription() == null)
            description.appendChild(document.createTextNode(""));
        else
            description.appendChild(document.createTextNode(department.getDescription()));
        departmentElement.appendChild(description);

        return departmentElement;
    }

    public void writeToFile(String fileName, List<Department> departmentList) {
        if (fileName != null && departmentList != null) {
            try {
                createDocumentBuilder();
                createDocument();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }

            Element rootElement = document.createElement("departments");
            document.appendChild(rootElement);

            for (Department department : departmentList) {
                rootElement.appendChild(createDepartmentElement(department));
            }

            Transformer transformer = createTransformer();

            if (transformer != null) {
                transform(transformer, document, fileName);
            }
        }
    }

    private Transformer createTransformer() {
        try {
            return TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void transform(Transformer transformer, Document document, String fileName) {
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(fileName));
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            System.out.println("Can't transform");
        }
    }
}
