package com.example.bovink.xmlparser.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * com.example.bovink.xmlparser.parser
 *
 * @author bovink
 * @since 2017/4/29
 */

public class DomParser implements XmlParse {

    private InputStream inputStream;

    public DomParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private void parse(InputStream inputStream) {

        printTeacherName(inputStream);
//        printStudentName(inputStream);
    }

    @Override
    public void parse() {
        parse(inputStream);
    }

    private void printTeacherName(InputStream inputStream) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            NodeList teachers = document.getElementsByTagName("teacher");

            for (int i = 0; i < teachers.getLength(); i++) {
                Element e = (Element) teachers.item(i);
                System.out.println(e.getTextContent());
            }
            NodeList students = document.getElementsByTagName("student");

            for (int i = 0; i < students.getLength(); i++) {
                Element e = (Element) students.item(i);
                System.out.println(e.getTextContent());
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseEntireFile(InputStream inputStream) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            Element root = document.getDocumentElement();
            NodeList classes = root.getElementsByTagName("class");
            for (int i = 0; i < classes.getLength(); i++) {
                Element element = (Element) classes.item(i);

                System.out.println("=====班级=====");
                System.out.println(element.getAttribute("name"));
                System.out.println(element.getAttribute("id"));
                NodeList nodeList = element.getChildNodes();
                for (int j = 0; j < nodeList.getLength(); j++) {
                    Node node = nodeList.item(j);
                    String name = node.getNodeName();
                    if (name.equalsIgnoreCase("teacher")) {
                        Element element1 = (Element) node;
                        System.out.println("=====老师=====");
                        System.out.println(element1.getAttribute("id"));
                        System.out.println(element1.getTextContent());

                    } else if (name.equalsIgnoreCase("student")) {
                        Element element1 = (Element) node;
                        System.out.println("=====学生=====");
                        System.out.println(element1.getAttribute("id"));
                        System.out.println(element1.getAttribute("sex"));
                        element1.setTextContent("nobody");
                        System.out.println(element1.getTextContent());

                    }

                }
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
