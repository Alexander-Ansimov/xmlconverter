package com.xml.converter.parser;

import lombok.Builder;
import lombok.Data;

import javax.xml.xpath.XPathExpression;

@Data
@Builder
public class XmlObject {

    private String convenientName;
    private String tagName;
    private String xpathPattern;
    private XPathExpression xPathExpression;
}
