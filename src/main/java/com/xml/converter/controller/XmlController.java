package com.xml.converter.controller;

import com.xml.converter.handler.XmlHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class XmlController {

    @Autowired
    private XmlHandler handler;

    @PostMapping("/")
    public void postMessage(@RequestBody String xmlData) {
        handler.handle(xmlData);
    }
}
