package com.hubin.andtomcat.core;

import com.hubin.andtomcat.HttpServlet;
import com.hubin.andtomcat.parser.WebApp;
import com.hubin.util.LogUtils;

import java.io.IOException;
import java.net.Socket;

/**
 * @项目名： WebServer
 * @包名： com.hubin.httpserver.http
 * @文件名: Dispatch
 * @创建者: 胡英姿
 * @创建时间: 2018/10/8 16:03
 * @描述： 分发 调度
 */
public class Dispatch implements Runnable{
    private Socket client;
    private Request request;
    private Response response;
    private int code = 200;
    public Dispatch(Socket client) {
        LogUtils.d("Dispatch  D : 调度！");
        this.client = client;
        try {
            request = new Request(client.getInputStream());
            response = new Response(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            code = 500;
        }
    }
    @Override
    public void run() {
        try{
            String action = request.getAction();
            HttpServlet httpServlet = WebApp.getServlet(action);
            if(httpServlet == null){
                this.code = 404;
                response.write(code);
                return;
            }
            httpServlet.service(request, response);

        } catch (Exception e) {
            LogUtils.e("run E : "+e.getMessage());
            code = 500;
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
