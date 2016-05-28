package test;

import db.domain.Department;
import xml.XmlWriter;

import java.util.ArrayList;
import java.util.List;

public class XmlWriterTest {
    public static void main(String[] args) {
        List<Department> departments = new ArrayList<>();

        XmlWriter xmlWriter = new XmlWriter();
        xmlWriter.writeToFile("departments.xml", departments);
    }
}
