package com.xml.converter.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import java.io.IOException;
import java.util.Map;


@SpringBootTest
@RunWith(SpringRunner.class)
public class XmlParserTest {

    @Autowired
    private XmlParser xmlParser;

    @Test
    public void parseFromXml() throws SAXException, IOException {

        Map<String, String> resultMap = xmlParser.parseFromXml(XmlConst.xml_1);
        resultMap.forEach((k, v) -> System.out.println(k + "  :  " + v));

    }
}