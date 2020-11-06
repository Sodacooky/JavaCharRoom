package com.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//处理用户登录
public class LoginHub {
    //用来接受（accept）连接入请求
    private ServerSocket m_srvSock;

    //正在处理的登录
    private BufferedReader m_readerHandling;
    private BufferedWriter m_writerHandling;

    //bind port
    LoginHub() {
        try {
            //交互输入监听端口
            int port;
            while (true) {
                System.out.println("输入要监听的端口：");
                Scanner scn = new Scanner(System.in);
                port = scn.nextInt();

                if (port > 100 && port < 65536) {
                    scn.close();
                    break;
                } else {
                    System.out.println("不合法/不建议的端口，请重新输入！");
                }
            }

            //开始监听
            System.out.println("[INFO] 初始化完毕，开始接受消息");
            m_srvSock = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //等待一个登录，返回用户
    public User waitForLogin() {
        try {
            //接受socket链接
            Socket socket_new = m_srvSock.accept();

            //获得流
            m_readerHandling =
                new BufferedReader(new InputStreamReader(socket_new.getInputStream()));
            m_writerHandling =
                new BufferedWriter(new OutputStreamWriter(socket_new.getOutputStream()));

            //发送连接成功通知
            sendConnectOkMessage(socket_new);

            //获取其用户名
            String nickname = waitForLegalNickname(socket_new);
            if (nickname == null) {
                //用户名失效要求其重新连接
                socket_new.close();
                return null;
            }

            //构建用户
            User usr_new = new User(socket_new);
            usr_new.strNickname = nickname;

            System.out.println("[INFO] " + usr_new.strNickname + " 登入成功");
            MessageHub.add("**系统**", nickname + " 进入了聊天室。");

            return usr_new;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void sendConnectOkMessage(Socket sock) throws IOException {
        m_writerHandling.write("[[!OK]]\n");
        m_writerHandling.flush();
        System.out.println("[INFO] 新的连接来自: " + sock.getRemoteSocketAddress().toString());
    }

    private String waitForLegalNickname(Socket sock) throws IOException {
        String ctrl_symbol;
        String inputed_nickname;
        String user_addr = sock.getRemoteSocketAddress().toString();

        ctrl_symbol = m_readerHandling.readLine();
        if (!ctrl_symbol.equals("[[!LOGIN]]")) {
            //不正确的请求
            System.out.println("[INFO] 不正确的登录请求来自: " + user_addr);
            m_writerHandling.write("[[!FAIL]]\n");
            m_writerHandling.flush();
            return null;
        }

        m_writerHandling.write("[[!OK]]\n");
        m_writerHandling.flush();

        inputed_nickname = m_readerHandling.readLine();
        if (UserList.isUserExist(inputed_nickname)) {
            //用户名已存在
            System.out.println("[INFO] 重复用户名的登录来自: " + user_addr);
            m_writerHandling.write("[[!FAIL]]\n");
            m_writerHandling.flush();
            return null;
        }

        m_writerHandling.write("[[!OK]]\n");
        m_writerHandling.flush();
        System.out.println("[INFO] 正在处理登录来自: " + user_addr);

        return inputed_nickname;


    }
}
