package com.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//处理用户登录
public class LoginHub {
    //用来接受（accept）连接入请求
    private ServerSocket m_srvSock;

    //bind 10000 port
    LoginHub() {
        try {
            m_srvSock = new ServerSocket(10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //等待一个登录，返回用户
    public User waitForLogin() {
        try {
            Socket socket_new = m_srvSock.accept();

            String recv;

            ServerTool.sendMessage(socket_new, "[[!OK]]");

            //等待login
            recv = ServerTool.waitMessage(socket_new);
            if (!recv.equals("[[!LOGIN]]")) {
                //无效的申请
                socket_new.close();
                return null;
            }

            //等待用户名
            recv = ServerTool.waitMessage(socket_new);
            if (UserList.isUserExist(recv)) {
                //已存在用户返回fail
                ServerTool.sendMessage(socket_new, "[[!FAIL]]");
                return null;
            } else {
                ServerTool.sendMessage(socket_new, "[[!OK]]");
            }

            //实例化并返回
            User usr_new = new User(socket_new);
            usr_new.strNickname = recv;
            return usr_new;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
