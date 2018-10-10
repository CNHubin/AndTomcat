package com.hubin.andtomcat.core;

import com.hubin.util.LogUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * @项目名： WebServer
 * @包名： com.hubin.httpserver.http
 * @文件名: Response
 * @创建者: 胡英姿
 * @创建时间: 2018/9/30 16:15
 * @描述：
 *
 *      A:构建报文头
 *      B:构建响应的HTML正文内容
 *      C：将报文头和HTML正文内容发送给客户端（浏览器
 */
public class Response {

    private static final String ENTER = "\r\n";
    private static final String SPACE = " ";
    //存储头信息
    private StringBuilder headerInfo ;
    //2、存储正文信息
    private StringBuilder textContent;
    //3、记录正文信息长度
    private int contentLength ;

    //4、构建输出流
    private BufferedWriter bw ;

    public Response() {
        headerInfo = new StringBuilder();
        textContent =  new StringBuilder();
        contentLength = 0;
    }

    public Response(OutputStream os) {
        this();
        bw = new BufferedWriter(new OutputStreamWriter(os));
    }

    /**
     * 创建头部信息 html报文
     * @param code
     */
    private void createHeader(int code){
        headerInfo.append("HTTP/1.1").append(SPACE).append(code).append(SPACE);
        switch (code) {
            case 200:
                headerInfo.append("OK").append(ENTER);
                break;
            case 404:
                headerInfo.append("NOT FOUND").append(ENTER);
                break;
            case 500:
                headerInfo.append("SERVER ERROR").append(ENTER);
                break;
            default:
                break;
        }

        headerInfo.append("Server:myServer").append(SPACE).append("0.0.1v").append(ENTER);
        headerInfo.append("Date:Sat,"+SPACE).append(new Date()).append(ENTER);
        headerInfo.append("Content-Type:text/html;charset=UTF-8").append(ENTER);
        headerInfo.append("Content-Length:").append(contentLength).append(ENTER);
        headerInfo.append(ENTER);
    }
    /**
     * 响应给浏览器解析的内容（html正文）
     * @param content
     * @return
     */
    public Response htmlContent(String content){
        textContent.append(content).append(ENTER);
        contentLength += (content+ENTER).toString().getBytes().length;
        return this;
    }
    /**
     * 发送给浏览器端
     * @param code
     */
    public void write(int code){
        createHeader(code);
        try {
            bw.append(headerInfo.toString());
            LogUtils.d("pushToClient  header : "+headerInfo.toString());
            bw.append(textContent.toString());
            LogUtils.d("pushToClient  D : "+textContent.toString());
        }catch(IOException e){
            LogUtils.e("pushToClient E : "+e.getMessage());
        }
    }

    public void flush() {
        try {
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            bw.close();
        } catch (IOException e) {
            LogUtils.e("pushToClient E : "+e.getMessage());
        }
    }
}
