package com.sitech.esb.session;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;

/**
 * 编辑xml
 * @author liwei_paas
 * @date 2020/9/11
 */
public class XmlTest {

    @Test
    public void testDom4j() throws Exception {
        URL url = XmlTest.class.getClassLoader().getResource("hibernate.cfg.xml");
        File file = new File(url.getPath());
        System.out.println(file.exists());

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setEntityResolver(((publicId, systemId) -> {
            for (int i = 0; i < registrations.length; i += 2) {
                if(publicId.equals(registrations[i])){
                    //忽略dtd格
                    return new InputSource( new  ByteArrayInputStream( "<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
                }
            }
            return null;
        }));

        Document doc = db.parse(file.getAbsolutePath());

    }


    private final static String registrations[] = {
            "-//Hibernate/Hibernate Configuration DTD 3.0//EN",
            "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd"
    };

}
