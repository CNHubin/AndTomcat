package com.hubin.httpserver;

import com.hubin.andtomcat.HttpServlet;
import com.hubin.andtomcat.core.Request;
import com.hubin.andtomcat.core.Response;

/**
 * @项目名： WebServer
 * @包名： com.hubin.httpserver
 * @文件名: TestServlet
 * @创建者: 胡英姿
 * @创建时间: 2018/10/9 11:11
 * @描述：  简单的get请求与响应服务器
 */
public class TestServlet extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) throws Exception {
       response.htmlContent("hello !");

       response.write(200);
       response.flush();
       response.close();
    }
}
