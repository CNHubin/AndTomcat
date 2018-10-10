package com.hubin.andtomcat.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @项目名： WebServer
 * @包名： com.hubin.httpserver.http
 * @文件名: Server
 * @创建者: 胡英姿
 * @创建时间: 2018/9/30 16:10
 * @描述： TODO
 */
public class Server {
    private boolean isShutDown = false;
    /**
     * 启动服务器
     */
    public void start(){
        start(8080);
    }
    /**
     * 指定服务器端口
     * @param port
     */
    public void start(int port){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            //2、接收来自浏览器的请求
            this.recevie(serverSocket);
        } catch (IOException e) {
            //e.printStackTrace();
            stop();
        }
    }
    /**
     * 关闭服务器
     */
    public void stop() {
        isShutDown = true;
    }
    /**
     * 接受客户端信息
     * @param serverSocket
     */
    private void recevie(ServerSocket serverSocket){
        try {
            while(!isShutDown){
                Socket client = serverSocket.accept();

                new Thread(new Dispatch(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //如果这里面有问题直接关闭服务器
            isShutDown = true;
        }
    }
}
