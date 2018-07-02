package com.xml.converter.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class XmlParser {

    @Autowired
    private XmlParserInitializer initializer;
    private final DocumentBuilder builder;

    public XmlParser() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
    }

    public Map<String, String> parseFromXml(String xml)
            throws IOException, SAXException {

        HashMap<String, String> result = new HashMap<>();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        List<XmlObject> xmlObjects = initializer.getXmlObjects();

        xmlObjects.forEach(o -> {
            try {
                String value = (String) o.getXPathExpression().evaluate(doc, XPathConstants.STRING);
                if (!"".equals(value)) {
                    result.put(o.getConvenientName(), value);
                }
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
        });

        result.forEach((k, v) -> log.debug("result -> " + k + " : " + v));
        return result;
    }
}
