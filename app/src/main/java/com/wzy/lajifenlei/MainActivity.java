package com.wzy.lajifenlei;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.littlegreens.netty.client.NettyTcpClient;
import com.littlegreens.netty.client.listener.MessageStateListener;
import com.littlegreens.netty.client.listener.NettyClientListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button server;
    private Button send;
    private EditText ip_text;
    private EditText content_text;
    private int port = 8899;
    private TextView receive_message;


    private SocketServer serverSocket;
    private SocketClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        server = (Button) findViewById(R.id.server);
        send = (Button) findViewById(R.id.send);

        server.setOnClickListener(this);
        send.setOnClickListener(this);
        ip_text = (EditText) findViewById(R.id.ip_text);
        ip_text.setOnClickListener(this);
        content_text = (EditText) findViewById(R.id.content_text);
        content_text.setOnClickListener(this);
        receive_message = (TextView) findViewById(R.id.receive_message);
        receive_message.setOnClickListener(this);
        client = new SocketClient();


        /**socket收到消息线程*/
        SocketServer.ServerHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                receive_message.setText(msg.obj.toString());
                Log.d("msg", "消息收到，谢谢");
            }
        };

        SocketClient.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                receive_message.append(msg.obj.toString());
//                Log.i ( "msghh",msg.obj.toString ());
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.server:
                serverSocket = new SocketServer(port);
                serverSocket.beginListen();

                break;
            case R.id.send:
                submit();

                break;
        }
    }

    private void submit() {
        // validate
        final String text = ip_text.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(this, "请输入要发送的ip地址", Toast.LENGTH_SHORT).show();
            return;
        }

        final String text1 = content_text.getText().toString().trim();
        if (TextUtils.isEmpty(text1)) {
            Toast.makeText(this, "请输入要发送的内容", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        client.clintValue(MainActivity.this, text, port);
        client.openClientThread();
//
        client.sendMsg(text1);
//        NettyTcpClient mNettyTcpClient = new NettyTcpClient.Builder()
//                .setHost(text)    //设置服务端地址
//                .setTcpPort(port) //设置服务端端口号
//                .setMaxReconnectTimes(5)    //设置最大重连次数
//                .setReconnectIntervalTime(5)    //设置重连间隔时间。单位：秒
//                .setSendheartBeat(true) //设置发送心跳
//                .setHeartBeatInterval(5)    //设置心跳间隔时间。单位：秒
//                .setHeartBeatData(new byte[]{0x03, 0x0F, (byte) 0xFE, 0x05, 0x04, 0x0a}) //设置心跳数据，可以是String类型，也可以是byte[]
//                //    .setHeartBeatData("I'm is HeartBeatData") //设置心跳数据，可以是String类型，也可以是byte[]，以后设置的为准
//                .setIndex(0)    //设置客户端标识.(因为可能存在多个tcp连接)
//                .build();
//
//        mNettyTcpClient.setListener(new NettyClientListener<String>() {
//            @Override
//            public void onMessageResponseClient(String msg, int index) {
//                //服务端过来的消息回调
//                receive_message.setText(msg);
//            }
//
//            @Override
//            public void onClientStatusConnectChanged(int statusCode, int index) {
//                //连接状态回调
//                Log.d("status", "statusCode=" + statusCode + ",index=" + index);
//            }
//        });
//        mNettyTcpClient.connect();
//        mNettyTcpClient.sendMsgToServer(text1, new MessageStateListener() {
//            @Override
//            public void isSendSuccss(boolean isSuccess) {
//                if (isSuccess) {
//                    Log.d("msg", "send successful");
//                } else {
//                    Log.d("msg", "send error");
//                }
//            }
//        });
//        SocketUtil.setMessageReceiver(new MessageReceiver() {
//            @Override
//            public void onSuccess(String message) {
////                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//                Message message1 = new Message();
//                message1.what = 0;
//                message1.obj = message;
//                handler.sendMessage(message1);
//
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    SocketUtil.sendMessage(text, port, text1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        Handler handler=new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    SocketUtil.sendMessage(text, port, text1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}
