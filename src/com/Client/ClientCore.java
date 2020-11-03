package com.Client;

import com.Common.Message;
import com.Common.Tool;

import java.io.*;
import java.net.Socket;

//提供给GUI界面的底层核心
public class ClientCore {

    public ClientCore() {
        //default
        setServerEndpoint("localhost", 55555);
    }


    //设置服务器地址和端口
    public void setServerEndpoint(String addr, int port) {
        this.m_strAddr = addr;
        this.m_nPort = port;
    }


    //登录
    //返回: true登录成功，否则失败
    public boolean tryLogin(String usrname) throws IOException {
        m_socket = new Socket(m_strAddr, m_nPort);
        m_reader = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
        m_writer = new BufferedWriter(new OutputStreamWriter(m_socket.getOutputStream()));

        m_reader.readLine();//OK

        m_writer.write("[[!LOGIN]]\n");
        m_writer.flush();
        m_reader.readLine();//OK//必定

        m_writer.write(usrname + "\n");
        m_writer.flush();
        if (m_reader.readLine().equals("[[!OK]]")) {
            return true;
        } else {
            m_socket.close();
            return false;
        }
    }

    //登出
    public void logout() throws IOException {
        m_writer.write("[[!QUIT]]\n");
        m_writer.flush();
        m_reader.readLine();//OK//必定

        m_socket.close();
    }

    //等待服务器端消息（只能是BC广播消息
    public Message waitForMessage() throws IOException {
        if (!m_reader.readLine().equals("[[!BC]]")) {
            return null;
        }

        Message msg = new Message();
        msg.strOwner = m_reader.readLine();
        msg.lTime = Long.parseLong(m_reader.readLine());
        msg.strContent = Tool.recoverBreakRow(m_reader.readLine());

        return msg;
    }

    //向服务器发送一条消息
    //会把换行符都转换为[[!BR]]
    public void sendMessage(String content) throws IOException {
        m_writer.write("[[!MSG]]\n");
        m_writer.write(Tool.transBreakRow(content) + "\n");
        m_writer.flush();
    }


    //网络相关
    private Socket m_socket;
    private String m_strAddr;
    private int m_nPort;

    //for io
    private BufferedReader m_reader;
    private BufferedWriter m_writer;

    //用户名
    private String m_usrname;
}
