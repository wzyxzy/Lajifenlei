package com.wzy.lajifenlei;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketUtil {

    private static MessageReceiver messageReceiver;

    public static void setMessageReceiver(MessageReceiver messageReceiver) {
        SocketUtil.messageReceiver = messageReceiver;
    }

    public static void createSocket(int port) throws IOException {
        /**
         * 基于TCP协议的Socket通信，实现用户登录，服务端
         */
//1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        ServerSocket serverSocket = new ServerSocket(port);//1024-65535的某个端口
//2、调用accept()方法开始监听，等待客户端的连接
        Socket socket = serverSocket.accept();
        socket.setKeepAlive(true);
//3、获取输入流，并读取客户端信息
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String info = null;
        while ((info = bufferedReader.readLine()) != null) {
            System.out.println("我是服务器，客户端说：" + info);
            messageReceiver.onSuccess(info);
        }
        socket.shutdownInput();//关闭输入流
//4、获取输出流，响应客户端的请求
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(outputStream);
        pw.write("信息收到，谢谢！");
        pw.flush();


//5、关闭资源
//        pw.close();
//        outputStream.close();
//        bufferedReader.close();
//        inputStreamReader.close();
//        inputStream.close();
//        socket.close();
//        serverSocket.close();
    }

    public static void sendMessage(String address, int port, String message) throws IOException {
        //客户端
//1、创建客户端Socket，指定服务器地址和端口
        Socket socket = new Socket(address, port);
//2、获取输出流，向服务器端发送信息
        OutputStream outputStream = socket.getOutputStream();//字节输出流
        PrintWriter pw = new PrintWriter(outputStream);//将输出流包装成打印流
        pw.write(message);
        pw.flush();
        socket.shutdownOutput();
//3、获取输入流，并读取服务器端的响应信息
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String info = null;
        while ((info = bufferedReader.readLine()) != null) {
            System.out.println("我是客户端，服务器说：" + info);
            messageReceiver.onSuccess(info);
        }

//4、关闭资源
        bufferedReader.close();
        inputStream.close();
        pw.close();
        outputStream.close();
        socket.close();
    }
}
