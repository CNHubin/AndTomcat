package com.hubin.andtomcat.bean;

/**
 * @项目名： WebServer
 * @包名： com.hubin.httpserver.http
 * @文件名: XmlServlet
 * @创建者: 胡英姿
 * @创建时间: 2018/10/8 16:22
 * @描述： 封装xml文件 解析的xml文件  bean类
 */
public class XmlServlet {

    private String servlet_name;

    private String servlet_class;

    public String getServlet_name() {
        return servlet_name;
    }

    public void setServlet_name(String servlet_name) {
        this.servlet_name = servlet_name;
    }

    public String getServlet_class() {
        return servlet_class;
    }

    public void setServlet_class(String servlet_class) {
        this.servlet_class = servlet_class;
    }

}
