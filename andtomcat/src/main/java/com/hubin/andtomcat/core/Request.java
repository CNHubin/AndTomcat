package com.hubin.andtomcat.core;

import com.hubin.util.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @项目名： WebServer
 * @包名： com.hubin.httpserver.http
 * @文件名: Request
 * @创建者: 胡英姿
 * @创建时间: 2018/9/30 16:16
 * @描述：
 *  A:接受浏览器发送的请求
 *  B:解析浏览器发送来的请求
 */
public class Request {
    private static final String ENTER = "\r\n";
    //接收请求
    private BufferedReader request ;
    //储存接受信息
    private String requestHeader;
    //通过解析头信息得到请求方法
    private String method ;
    //通过解析头信息得到请求url
    private String action ;
    //通过解析头信息得到传过来的请求参数 ，可能存在一Key多Value的情况所以用list
    private Map<String, List<String>> parameter;
    //得到浏览器发过来的头信息
    public Request() {
        requestHeader = "";
        method = "";
        action = "";
        parameter = new HashMap<String, List<String>>();
    }
    public Request(InputStream inputStream) {
        this();
        try {
            request = new BufferedReader(new InputStreamReader(inputStream));
            //接收到头部信息
            String temp;
            while(!(temp=request.readLine()).equals("")){
                LogUtils.i("Request I : "+temp);
                requestHeader += (temp+ENTER);
            }
            //解析头部信息
            parseRequestHeader();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 解析头信息
     */
    public void parseRequestHeader(){

        //声明一个字符串，来存放请求参数
        String parameterString = "";
        //读取都头信息的第一行
        String firstLine = requestHeader.substring(0, requestHeader.indexOf(ENTER));
        //开始分离第一行
        //splitPoint分割点1
        int splitPointOne = firstLine.indexOf("/");
        method = firstLine.substring(0, splitPointOne).trim();
        //splitPoint分割点2
        int splitPointTwo = firstLine.indexOf("HTTP/");
        String actionTemp = firstLine.substring(splitPointOne,splitPointTwo).trim();
        if(method.equalsIgnoreCase("post")){
            //此处代码为得到post请求的参数字符串，哈哈哈哈，读者自己想想该怎么写哦~~
            this.action = actionTemp;
        }else if(method.equalsIgnoreCase("get")){
            if(actionTemp.contains("?")){
                parameterString = actionTemp.substring((actionTemp.indexOf("?")+1)).trim();
                this.action = actionTemp.substring(0, actionTemp.indexOf("?"));
            }else{
                this.action = actionTemp;
            }
            //将参数封装到Map中哦
            parseParameterString(parameterString);
        }

    }
    /**
     * 解析参数字符串，将参数封装到Map中
     * @param parameterString
     */
    private void parseParameterString(String parameterString) {
        if("".equals(parameterString)){
            return;
        }else{
            String[] parameterKeyValues = parameterString.split("&");

            for (int i = 0; i < parameterKeyValues.length; i++) {
                String[] KeyValues = parameterKeyValues[i].split("=");
                //可能会出现有key没有value的情况
                if(KeyValues.length == 1){
                    KeyValues = Arrays.copyOf(KeyValues, 2);
                    KeyValues[1] = null;
                }
                String key = KeyValues[0].trim();
                String values = null == KeyValues[1] ? null : decode(KeyValues[1].trim(),"UTF-8");
                //将key和values封装到Map中
                if(!parameter.containsKey(key)){//如果不存在key,就创建一个
                    parameter.put(key, new ArrayList<String>());
                }
                List<String> value = parameter.get(key);
                value.add(values);
            }
        }
    }
    /**
     * 反解码：使用指定的编码机制对 application/x-www-form-urlencoded 字符串解码。
     * @param string
     * @param encoding
     * @return
     */
    public String decode(String string,String encoding){
        try {
            return URLDecoder.decode(string, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 根据名字得到多个值
     * @param name
     * @return
     */
    public String[] getParamterValues(String name){
        List<String> values = parameter.get(name);
        if(values == null){
            return null;
        }else{
            return values.toArray(new String[0]);
        }
    }
    /**
     * 根据名字返回单个值
     * @param name
     * @return
     */
    public String getParamter(String name){
        String[] value = getParamterValues(name);
        if(value == null){
            return null;
        }else{
            return value[0];
        }
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
}
