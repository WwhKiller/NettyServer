package com.wwh.io.BioServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: BIO 模型
 * @author: wwh
 * @create: 2020/7/28
 */
public class BioServer {

    public static void main(String[] args) throws IOException {


        System.out.println("当前线程："+Thread.currentThread().getName());
        //创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //创建一个socket
        ServerSocket serverSocket  = new ServerSocket(6666);
        System.out.println("服务端已经启动，监听端口为：" + 6666);


        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("有新客户端连接上来了。。。");

            executorService.submit(() -> {
                try {
                    handler(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void handler(Socket socket) throws IOException {

            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];

            //读取socket中的数据
            while (true){
                System.out.println("当前线程："+Thread.currentThread().getName()+"      reading....");//阻塞在read
                int read  = inputStream.read(bytes);
                if (read != -1){

                    String info = new String(bytes,0,read,"GBK");

                    System.out.println("当前线程："+Thread.currentThread().getName()+"   socket读取的数据为： "+info);

                }else break;
            }


    }
}
