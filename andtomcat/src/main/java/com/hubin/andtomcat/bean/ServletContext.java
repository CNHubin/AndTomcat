package com.hubin.andtomcat.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @项目名： WebServer
 * @包名： com.hubin.httpserver.http
 * @文件名: ServletContext
 * @创建者: 胡英姿
 * @创建时间: 2018/9/30 16:34
 * @描述： 　到此时我们会发现我们发过来的请求会有很多，也就意味着我们应该会有很多的servlet，例如：RegisterServlet、LoginServlet
 * 等等还有很多其他的访问。　　　　那么我们要用到类似于工厂模式的方法处理，来随时产生很多的servlet，来满足不同的功能性的请求。　　　　那么我们要抽象servlet
 * 。　　　　在我们抽象servlet之前，我们先来思考一个问题：　　　　我们会写很多的servlet，那么我们怎么将请求与各种的servlet相匹配呢？　　　　接下来我们要先写一个关于servlet
 * 的上下文，来封装servlet与请求。说到这里你是不是想到了什么呢？？
 */
public class ServletContext {
    //通过对应的servlet类名创建servlet对象
    //private Map<String, HttpServlet> servlet ;
    private Map<String, String> servlet ;
    //通过请求名（action）找到对应的servlet类名
    private Map<String , String> mapping ;

    public ServletContext() {
        servlet = new HashMap<String,String>();
        mapping = new HashMap<String,String>();
    }


    public Map<String, String> getServlet() {
        return servlet;
    }
    public void setServlet(Map<String, String> servlet) {
        this.servlet = servlet;
    }


    public Map<String, String> getMapping() {
        return mapping;
    }
    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }

}
