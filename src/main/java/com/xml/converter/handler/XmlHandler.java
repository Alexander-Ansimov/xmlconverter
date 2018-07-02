package com.xml.converter.handler;

import com.xml.converter.parser.XmlParser;
import com.xml.converter.service.NextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class XmlHandler {

    @Autowired
    private XmlParser xmlParser;
    @Autowired
    private NextService nextService;

    public void handle(String xmlData) {

        Map<String, String> xmlValues = new HashMap<>();
        try {
            xmlValues = xmlParser.parseFromXml(xmlData);
        } catch (IOException | SAXException e) {
            log.error("Exception while parsing xml data:  " + xmlData + e);
            e.printStackTrace();
        }

        nextService.sendToSupplier(xmlValues);
    }
}
