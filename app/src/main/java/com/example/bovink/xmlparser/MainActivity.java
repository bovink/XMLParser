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

                DomParser domParser = new DomParser(inputStream);
                domParser.parse();
                break;
            case 2:

                SaxParser saxParser = new SaxParser(inputStream);
                saxParser.parse();
                break;
            case 3:

                XmlPullParser xmlPullParser = new XmlPullParser(inputStream);
                xmlPullParser.parse();
                break;
        }
    }


}
