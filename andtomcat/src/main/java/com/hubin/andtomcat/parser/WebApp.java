package com.hubin.andtomcat.parser;

import com.hubin.andtomcat.AndTomcat;
import com.hubin.andtomcat.HttpServlet;
import com.hubin.andtomcat.bean.ServletContext;
import com.hubin.andtomcat.bean.XmlMapping;
import com.hubin.andtomcat.bean.XmlServlet;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @项目名： WebServer
 * @包名： com.hubin.httpserver.http
 * @文件名: WebApp
 * @创建者: 胡英姿
 * @创建时间: 2018/10/8 16:19
 * @描述： 存储一些我们会用到的servlet上下文，并且提供获取他们的方法
 */
public class WebApp {
    private static ServletContext context;
    static {
        context = new ServletContext();
        //创建存放servlet上下文的容器
        Map<String, String> mapping = context.getMapping();
        Map<String, String> servlet = context.getServlet();

        //解析配置文件，将对应的字符串存入里面
        /*补充知识：
         * 解析配置文件的方法有很多，最基本的是SAX解析和DOM解析：SAX解析式基于事件流的解析，DOM解析是基于XML文档树结构的解析
         * 另外还有DOM4J和JDOM都可以解析。
         * DOM和SAX的区别：
         * DOM解析适合于对文件进行修改和随机存取的操作，但是不适合于大型文件的操作；
         * SAX采用部分读取的方式，所以可以处理大型文件，而且只需要从文件中读取特定内容，SAX解析可以由用户自己建立自己的对象模型。
         * 所以DOM解析适合于修改，SAX解析适合于读取大型文件，2者结合的话可以用JDOM
         *
         * 本次示例为了方便就选择SAX解析，步骤一共分三步：
         * 1、获得解析工程类。
         * 2、工程获取解析器
         * 3、加载文档注册处理器
         */
        //1、获得工厂类
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            //2、从解析工程获取解析器
            SAXParser parser = factory.newSAXParser();
            //3、加载文档并注册处理器（handle）。注意：此处的文档可以用file的形式也可以用流的形式，随便,便于学习，下面提供两种。
            //String filePath = "";
            //parser.parse(new File(filePath), handler);
            XMLHandler handler = new XMLHandler();
//            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("assets/WEB-INF/server.xml");
            InputStream is = AndTomcat.getContext().getAssets().open("andtomcat/WEB-INF/web.xml");

            parser.parse(is, handler);

            List<XmlServlet> serv = handler.getServlet();
            for (XmlServlet xmlServlet : serv) {
                servlet.put(xmlServlet.getServlet_name(), xmlServlet.getServlet_class());
            }
            List<XmlMapping> map = handler.getMapping();
            for (XmlMapping maps : map) {
                List<String> actions = maps.getUrl_pattern();
                for (String action : actions) {
                    mapping.put(action, maps.getServlet_name());
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HttpServlet getServlet(String action){
        if("".equals(action) || action == null){
            return null;
        }
        //通过action找到servlet-name
        String servlet_name = context.getMapping().get(action);
        //通过反射，找到相应的类，创建其对象并返回
        String classPath =  context.getServlet().get(servlet_name);//通过action得到类路径
        HttpServlet httpServlet = null;
        if(classPath != null){
            Class<?> clazz = null;
            try {
                clazz = Class.forName(classPath);
                httpServlet = (HttpServlet)clazz.newInstance();//要确保空构造存在
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return httpServlet;
    }
}
