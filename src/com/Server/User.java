package com.Server;

import java.io.IOException;
import java.net.Socket;

//一个用户和线程
public class User {
    //
    private Socket m_sock;

    //user nickname
    public String strNickname;


    User(Socket socket) {
        m_sock = socket;
        Thread thr = new Thread(new UserThread(this));
        thr.start();
    }

    //返回他的socket
    Socket getSocket() {
        return m_sock;
    }

    //向其广播消息
    public void broadcastMessage(Message msg) {
        ServerTool.sendMessage(m_sock, "[[!BC]]");

        ServerTool.sendMessage(m_sock, msg.strOwner);
        ServerTool.sendMessage(m_sock, String.valueOf(msg.lTime));
        ServerTool.sendMessage(m_sock, msg.strContent);

    }

}


//处理用户发来消息的线程
class UserThread extends Thread {
    private User m_usr;

    UserThread(User usr) {
        m_usr = usr;
    }

    //线程主函数
    public void run() {
        String recv;

        while (true) {
            //等待用户的标志信息
            recv = ServerTool.waitMessage(m_usr.getSocket());

            //处理
            if (recv.equals("[[!MSG]]")) {
                //用户发来新消息

                //添加信息到MessageHub
                recv = ServerTool.waitMessage(m_usr.getSocket());
                MessageHub.add(m_usr.strNickname, recv);

                //给用户发送回应
                ServerTool.sendMessage(m_usr.getSocket(), "[[!OK]]");

            } else if (recv.equals("[[!QUIT]]")) {
                //用户请求登出
                ServerTool.sendMessage(m_usr.getSocket(), "[[!OK]]");
                UserList.deleteUser(m_usr.strNickname);
                MessageHub.add("*系统*", m_usr.strNickname + " 离开了");

                try {
                    m_usr.getSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            } else {
                //用户发送了错误的标志
                //什么都不做

            }
        }

    }

}
