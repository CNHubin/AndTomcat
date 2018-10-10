package com.hubin.andtomcat;

import com.hubin.andtomcat.core.Request;
import com.hubin.andtomcat.core.Response;

/**
 * @项目名： WebServer
 * @包名： com.hubin.httpserver.http
 * @文件名: HttpServlet
 * @创建者: 胡英姿
 * @创建时间: 2018/9/30 16:18
 * @描述： 抽象出来的处理请求和响应类 子类实现即可
 */
public abstract class HttpServlet {
    public void service(Request request, Response response) throws Exception{
        String method = request.getMethod();
        if(method.equalsIgnoreCase("post")){
            this.doPost(request, response);
        }else if(method.equalsIgnoreCase("get")){
            this.doGet(request, response);
        }
    }

    public void doGet(Request request, Response response) throws Exception{

    }

    public void doPost(Request request, Response response) throws Exception{

    }


}
