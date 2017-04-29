package com.example.bovink.xmlparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.bovink.xmlparser.parser.DomParser;
import com.example.bovink.xmlparser.parser.SaxParser;
import com.example.bovink.xmlparser.parser.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private InputStream inputStream;
    private static int XML_PARSER_TYPE = 3;
    private RadioGroup radioGroup;
    private Button button;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rb1 = (RadioButton) findViewById(R.id.rb_domparse);
        rb2 = (RadioButton) findViewById(R.id.rb_saxparse);
        rb3 = (RadioButton) findViewById(R.id.rb_pullparse);


        // RadioGroup
        radioGroup = (RadioGroup) findViewById(R.id.rg_test);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rb1.getId()) {

                    XML_PARSER_TYPE = 1;
                } else if (checkedId == rb2.getId()) {

                    XML_PARSER_TYPE = 2;
                } else if (checkedId == rb3.getId()) {

                    XML_PARSER_TYPE = 3;
                }
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
//                handleXMLWithDOM();
                DomParser domParser = new DomParser(inputStream);
                domParser.parse();
                break;
            case 2:
//                handleXMLWithSAX();
                SaxParser saxParser = new SaxParser(inputStream);
                saxParser.parse();
                break;
            case 3:
//                handleXMLWithXMLPullParser();
                XmlPullParser xmlPullParser = new XmlPullParser(inputStream);
                xmlPullParser.parse();
                break;
        }

    }


//    private void handleXMLWithDOM() {
//        // 实例用工厂解析用builder
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        try {
//
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(inputStream);
//            Element root = document.getDocumentElement();
//            NodeList classes = root.getElementsByTagName("class");
//            for (int i = 0; i < classes.getLength(); i++) {
//                Element element = (Element) classes.item(i);
//
//                System.out.println("=====班级=====");
//                System.out.println(element.getAttribute("name"));
//                System.out.println(element.getAttribute("id"));
//                NodeList nodeList = element.getChildNodes();
//                for (int j = 0; j < nodeList.getLength(); j++) {
//                    Node node = nodeList.item(j);
//                    String name = node.getNodeName();
//                    if (name.equalsIgnoreCase("teacher")) {
//                        Element element1 = (Element) node;
//                        System.out.println("=====老师=====");
//                        System.out.println(element1.getAttribute("id"));
//                        System.out.println(element1.getTextContent());
//
//                    } else if (name.equalsIgnoreCase("student")) {
//                        Element element1 = (Element) node;
//                        System.out.println("=====学生=====");
//                        System.out.println(element1.getAttribute("id"));
//                        System.out.println(element1.getAttribute("sex"));
//                        element1.setTextContent("nobody");
//                        System.out.println(element1.getTextContent());
//
//                    }
//
//                }
//            }
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void handleXMLWithSAX() {
//        // 解析用xml或工厂
//        MySAXHandler mySAXHandler = new MySAXHandler();
//        try {
//            Xml.parse(inputStream, Xml.Encoding.UTF_8, mySAXHandler);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }
//
//    }

//    private void handleXMLWithXMLPullParser() {
//        // 新建实例可以用Xml或工厂
//        XmlPullParser xmlPullParser = Xml.newPullParser();
//        try {
//            xmlPullParser.setInput(inputStream, "utf-8");
//            int event = xmlPullParser.getEventType();
//            while (event != XmlPullParser.END_DOCUMENT) {
//                switch (event) {
//                    case XmlPullParser.START_TAG:
//                        if (xmlPullParser.getName().equals("class")) {
//                            System.out.println("=====班级=====");
//                            System.out.println(xmlPullParser.getAttributeValue(0));
//                            System.out.println(xmlPullParser.getAttributeValue(1));
//
//                        } else if (xmlPullParser.getName().equals("teacher")) {
//                            System.out.println("=====老师=====");
//                            System.out.println(xmlPullParser.getAttributeValue(0));
//                            System.out.println(xmlPullParser.nextText());
//
//                        } else if (xmlPullParser.getName().equals("student")) {
//                            System.out.println("=====学生=====");
//                            System.out.println(xmlPullParser.getAttributeValue(0));
//                            System.out.println(xmlPullParser.getAttributeValue(1));
//                            System.out.println(xmlPullParser.nextText());
//
//                        }
//
//                        break;
//                }
//
//                event = xmlPullParser.next();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//
//    }

//    private class MySAXHandler extends DefaultHandler {
//        private boolean printValue = false;
//
//        @Override
//        public void startElement(String uri, String localName, String qName, Attributes attributes) {
//            switch (qName) {
//                case "class":
//                    System.out.println("=====班级=====");
//                    for (int i = 0; i < attributes.getLength(); i++) {
//                        System.out.println(attributes.getValue(i));
//                    }
//                    break;
//                case "teacher":
//                    System.out.println("=====老师=====");
//                    for (int i = 0; i < attributes.getLength(); i++) {
//                        System.out.println(attributes.getValue(i));
//                    }
//                    printValue = true;
//                    break;
//                case "student":
//                    System.out.println("=====学生=====");
//                    for (int i = 0; i < attributes.getLength(); i++) {
//                        System.out.println(attributes.getValue(i));
//                    }
//                    printValue = true;
//                    break;
//            }
//        }
//
//        @Override
//        public void characters(char[] ch, int start, int length) {
//            String s = new String(ch, start, length);
//            if (printValue) {
//                System.out.println(s);
//                printValue = false;
//            }
//        }
//    }
}
