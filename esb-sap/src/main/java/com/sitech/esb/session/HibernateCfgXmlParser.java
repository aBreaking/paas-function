package com.sitech.esb.session;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.Map;

/**
 *
 * @author liwei_paas
 * @date 2020/9/11
 */
public class HibernateCfgXmlParser {

    private static final String TEMPLATE_HIBERNATE_CFG_XML_NAME = "hibernate.cfg.xml";

    private static final String[] DTD_REGISTRATIONS = {
            "-//Hibernate/Hibernate Configuration DTD 3.0//EN",
            "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd"
    };

    /**
     * 默认的hibernate配置文件的模板文档， 用于生成sessionFactory
     */
    private static Document TEMPLATE_HIBERNATE_DOCUMENT = null;

    public Document parseSF(Map<String,String> kvForProperty) throws Exception {
        Document document = getTemplateConfigDocument();
        NodeList nodes = document.getElementsByTagName("hibernate-configuration");
        Element hc = (Element) nodes.item(0);
        Element sf = (Element) hc.getElementsByTagName("session-factory").item(0);
        NodeList property = sf.getElementsByTagName("property");
        int length = property.getLength();
        for (int i = 0; i < length; i++) {
            Element pe = (Element) property.item(i);
            if (pe.hasAttribute("name")){
                String name = pe.getAttribute("name");
                if (kvForProperty.containsKey(name)){
                    pe.setTextContent(kvForProperty.get(name));
                }
            }
        }
        return document;
    }

    private Document getTemplateConfigDocument() throws Exception {
        if (TEMPLATE_HIBERNATE_DOCUMENT==null){
            synchronized (HibernateCfgXmlParser.class){
                if (TEMPLATE_HIBERNATE_DOCUMENT == null){
                    URL url = HibernateCfgXmlParser.class.getClassLoader().getResource(TEMPLATE_HIBERNATE_CFG_XML_NAME);
                    File file = new File(url.getPath());
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    db.setEntityResolver(((publicId, systemId) -> {
                        for (int i = 0; i < DTD_REGISTRATIONS.length; i += 2) {
                            if(publicId.equals(DTD_REGISTRATIONS[i])){
                                //忽略dtd格
                                return new InputSource( new ByteArrayInputStream( "<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
                            }
                        }
                        return null;
                    }));
                    return db.parse(file.getAbsolutePath());
                }
            }
        }
        return TEMPLATE_HIBERNATE_DOCUMENT;
    }

    public static void document2File(Document document,String filename) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(
                filename));
        transformer.transform(source, streamResult);
    }

}
