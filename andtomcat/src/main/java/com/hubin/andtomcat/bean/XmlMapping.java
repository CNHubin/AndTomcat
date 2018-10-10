package com.hubin.andtomcat.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名： WebServer
 * @包名： com.hubin.httpserver.http
 * @文件名: XmlMapping
 * @创建者: 胡英姿
 * @创建时间: 2018/10/8 16:21
 * @描述： TODO
 */
public class XmlMapping {
    private String servlet_name;

    private List<String> url_pattern;

    public XmlMapping() {
        url_pattern = new ArrayList<String>();
    }
    public String getServlet_name() {
        return servlet_name;
    }
    public void setServlet_name(String servlet_name) {
        this.servlet_name = servlet_name;
    }
    public List<String> getUrl_pattern() {
        return url_pattern;
    }
    public void setUrl_pattern(List<String> url_pattern) {
        this.url_pattern = url_pattern;
    }

}
