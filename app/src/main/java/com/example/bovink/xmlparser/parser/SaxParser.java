package com.example.bovink.xmlparser.parser;

import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;

/**
 * com.example.bovink.xmlparser.parser
 *
 * @author bovink
 * @since 2017/4/29
 */

public class SaxParser implements XmlParser {

    private InputStream inputStream;

    public SaxParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private void parse(InputStream inputStream) {

        // 解析用xml或工厂
        MySAXHandler mySAXHandler = new MySAXHandler();
        try {
            Xml.parse(inputStream, Xml.Encoding.UTF_8, mySAXHandler);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void parse() {

        parse(inputStream);
    }

    private class MySAXHandler extends DefaultHandler {
        private boolean printValue = false;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            switch (qName) {
                case "class":
                    System.out.println("=====班级=====");
                    for (int i = 0; i < attributes.getLength(); i++) {
                        System.out.println(attributes.getValue(i));
                    }
                    break;
                case "teacher":
                    System.out.println("=====老师=====");
                    for (int i = 0; i < attributes.getLength(); i++) {
                        System.out.println(attributes.getValue(i));
                    }
                    printValue = true;
                    break;
                case "student":
                    System.out.println("=====学生=====");
                    for (int i = 0; i < attributes.getLength(); i++) {
                        System.out.println(attributes.getValue(i));
                    }
                    printValue = true;
                    break;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String s = new String(ch, start, length);
            if (printValue) {
                System.out.println(s);
                printValue = false;
            }
        }
    }
}
