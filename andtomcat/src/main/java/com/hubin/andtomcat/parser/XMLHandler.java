package com.hubin.andtomcat.parser;

import com.hubin.andtomcat.bean.XmlMapping;
import com.hubin.andtomcat.bean.XmlServlet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名： WebServer
 * @包名： com.hubin.httpserver.http
 * @文件名: XMLHandler
 * @创建者: 胡英姿
 * @创建时间: 2018/10/8 16:20
 * @描述： TODO
 */
public class XMLHandler extends DefaultHandler {

    private List<XmlServlet> servlet ;

    private List<XmlMapping> mapping ;

    public List<XmlServlet> getServlet() {
        return servlet;
    }

    public void setServlet(List<XmlServlet> servlet) {
        this.servlet = servlet;
    }

    public List<XmlMapping> getMapping() {
        return mapping;
    }

    public void setMapping(List<XmlMapping> mapping) {
        this.mapping = mapping;
    }

    private XmlServlet serv ;

    private XmlMapping map ;

    private String beginTag;

    private boolean isMap;

    @Override
    public void startDocument() throws SAXException {
        servlet = new ArrayList<XmlServlet>();
        mapping = new ArrayList<XmlMapping>();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if(null!=qName){
            beginTag = qName;
            if(qName.equals("servlet")){
                serv = new XmlServlet();
                isMap = false;
            }else if(qName.equals("servlet-mapping")){
                map = new XmlMapping();
                isMap = true;
            }
        }

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if(beginTag != null){
            String info = new String(ch, start, length);
            if(isMap){
                if(beginTag.equals("servlet-name")){
                    map.setServlet_name(info.trim());
                }else if(beginTag.equals("url-pattern")){
                    map.getUrl_pattern().add(info);
                }
            }else{
                if(beginTag.equals("servlet-name")){
                    serv.setServlet_name(info);
                }else if(beginTag.equals("servlet-class")){
                    serv.setServlet_class(info);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if(null!=qName){
            if(qName.equals("servlet")){
                servlet.add(serv);
            }else if(qName.equals("servlet-mapping")){
                mapping.add(map);
            }
        }
        beginTag = null;
    }

    @Override
    public void endDocument() throws SAXException {
        //文档结束
    }
}

