package com.example.bovink.xmlparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    private InputStream inputStream;
    private static int XML_PARSER_TYPE = 3;
    private RadioGroup radioGroup;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RadioGroup
        radioGroup = (RadioGroup) findViewById(R.id.rg_test);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                XML_PARSER_TYPE = checkedId;
            }
        });
        //Button
        button = (Button) findViewById(R.id.btn_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    inputStream = getAssets().open("Example01.xml");
                    handleXML(XML_PARSER_TYPE);
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleXML(int type) {
        switch (type) {
            case 1:
                handleXMLWithDOM();
                break;
            case 2:
                handleXMLWithSAX();
                break;
            case 3:
                handleXMLWithXMLPullParser();
                break;
        }

    }


    private void handleXMLWithDOM() {
        // 实例用工厂解析用builder
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

    private void handleXMLWithSAX() {
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

    private void handleXMLWithXMLPullParser() {
        // 新建实例可以用Xml或工厂
        XmlPullParser xmlPullParser = Xml.newPullParser();
        try {
            xmlPullParser.setInput(inputStream, "utf-8");
            int event = xmlPullParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("class")) {
                            System.out.println("=====班级=====");
                            System.out.println(xmlPullParser.getAttributeValue(0));
                            System.out.println(xmlPullParser.getAttributeValue(1));

                        } else if (xmlPullParser.getName().equals("teacher")) {
                            System.out.println("=====老师=====");
                            System.out.println(xmlPullParser.getAttributeValue(0));
                            System.out.println(xmlPullParser.nextText());

                        } else if (xmlPullParser.getName().equals("student")) {
                            System.out.println("=====学生=====");
                            System.out.println(xmlPullParser.getAttributeValue(0));
                            System.out.println(xmlPullParser.getAttributeValue(1));
                            System.out.println(xmlPullParser.nextText());

                        }

                        break;
                }

                event = xmlPullParser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

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
