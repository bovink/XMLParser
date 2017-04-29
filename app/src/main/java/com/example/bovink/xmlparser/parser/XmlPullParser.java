package com.example.bovink.xmlparser.parser;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * com.example.bovink.xmlparser.parser
 *
 * @author bovink
 * @since 2017/4/29
 */

public class XmlPullParser implements XmlParse {

    private InputStream inputStream;

    public XmlPullParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private void parse(InputStream inputStream) {
        // 新建实例可以用Xml或工厂
        org.xmlpull.v1.XmlPullParser xmlPullParser = Xml.newPullParser();
        try {
            xmlPullParser.setInput(inputStream, "utf-8");
            int event = xmlPullParser.getEventType();
            while (event != org.xmlpull.v1.XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case org.xmlpull.v1.XmlPullParser.START_TAG:
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

    @Override
    public void parse() {
       parse(inputStream);
    }
}
