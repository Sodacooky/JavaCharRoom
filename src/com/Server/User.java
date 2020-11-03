package com.Server;

import com.Common.Message;

import java.io.*;
import java.net.Socket;

//一个用户和线程
public class User {
    //
    protected Socket sock;

    //user nickname
    protected String strNickname;

    //for io
    protected BufferedWriter writer;
    protected BufferedReader reader;

    User(Socket socket) {
        //alloc
        sock = socket;

        //获取流
        try {
            writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //监听消息线程
        Thread thr = new Thread(new UserThread(this));
        thr.start();
    }

    //向其广播消息
    public void broadcastMessage(Message msg) {
        try {
            writer.write("[[!BC]]\n");
            writer.write(msg.strOwner + "\n");
            writer.write(String.valueOf(msg.lTime) + "\n");
            writer.write(msg.strContent + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            UserList.deleteUser(strNickname);
            MessageHub.add("**系统**", strNickname + " 离开了");
        }

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
        try {
            while (m_usr.sock.isConnected()) {
                //等待用户的标志信息
                recv = m_usr.reader.readLine();

                //处理
                if (recv.equals("[[!MSG]]")) {
                    //用户发来新消息
                    recv = m_usr.reader.readLine();
                    //添加信息到MessageHub
                    MessageHub.add(m_usr.strNickname, recv);

                } else if (recv.equals("[[!QUIT]]")) {
                    //用户请求登出
                    //发送确认
                    m_usr.writer.write("[[!OK]]\n");
                    m_usr.writer.flush();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            UserList.deleteUser(m_usr.strNickname);
            MessageHub.add("*系统*", m_usr.strNickname + " 离开了");
        }
    }
}
