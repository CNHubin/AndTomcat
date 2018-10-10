package com.hubin.httpserver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hubin.andtomcat.AndTomcat;
import com.hubin.andtomcat.core.Server;
import com.hubin.util.Ipv4Utils;
import com.hubin.util.ThreadUtils;

public class MainActivity extends AppCompatActivity {

    private Server mServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndTomcat.init(this,BuildConfig.DEBUG);
        TextView ipAddress = findViewById(R.id.text);

        ipAddress.setText("局域网内请使用浏览器访问如下地址：\r\n"+Ipv4Utils.getLocalIPAddress()+":8080/test");

        ThreadUtils.runOnNewThread(new Runnable() {
            @Override
            public void run() {
                //1、创建一个服务器端并开启
                mServer = new Server();
                mServer.start();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServer.stop();
    }
}
