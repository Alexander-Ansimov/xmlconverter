package com.xml.converter.parser;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
@Slf4j
public class XmlParserInitializer {

    @Value("${mapping.path}")
    private String mappingCsvFile;

    List<XmlObject> xmlObjects = new ArrayList<>();

    @PostConstruct
    private void init() {
        fillMapping(xmlObjects);
        fillExpression(xmlObjects);
    }

    private void fillMapping(List<XmlObject> xmlObjects) {
        try (
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(mappingCsvFile);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                CSVReader csvReader = new CSVReaderBuilder(inputStreamReader)
                        .withSkipLines(1)
                        .build()
            )
        {
            List<String[]> records = csvReader.readAll();
            records.forEach(r -> xmlObjects.add(XmlObject.builder()
                    .convenientName(r[0])
                    .tagName(r[1])
                    .xpathPattern(r[2])
                    .build()));

            xmlObjects.forEach(o -> log.info("mapping -> " + o));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillExpression(List<XmlObject> xmlObjects) {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        xmlObjects.forEach(o -> {
            try {
                o.setXPathExpression(xpath.compile(o.getXpathPattern()));
            } catch (XPathExpressionException e) {
                log.error("not valid xpath pattern -> " + o.getXpathPattern());
                e.printStackTrace();
            }
        });

        xmlObjects.forEach(o -> log.info("xmlObject -> " + o));
    }
}