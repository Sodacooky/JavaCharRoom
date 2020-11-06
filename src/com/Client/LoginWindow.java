package com.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoginWindow {

    public LoginWindow(ClientCore clientCore) {
        m_clientCore = clientCore;

        m_lock = new ReentrantLock();
        m_cond = m_lock.newCondition();

        __buildWindow();
    }

    private void __buildWindow() {
        m_fWindow = new JFrame("登陆到一个服务器");
        m_fWindow.setSize(120, 220);
        m_fWindow.setResizable(false);
        m_fWindow.setLayout(new FlowLayout());
        m_fWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        m_fWindow.add(new JLabel("服务器地址："));
        m_tfAddress = new JTextField("localhost");
        m_tfAddress.setColumns(12);
        m_tfAddress.setHorizontalAlignment(JTextField.CENTER);
        m_fWindow.add(m_tfAddress);

        m_fWindow.add(new JLabel("服务器端口："));
        m_tfPort = new JTextField("55555");
        m_tfPort.setColumns(12);
        m_tfPort.setHorizontalAlignment(JTextField.CENTER);
        m_fWindow.add(m_tfPort);

        m_fWindow.add(new JLabel("用户名："));
        m_tfUsername = new JTextField("小明123456");
        m_tfUsername.setColumns(12);
        m_tfUsername.setHorizontalAlignment(JTextField.CENTER);
        m_fWindow.add(m_tfUsername);

        m_btnConnect = new JButton("连接并登录");
        m_btnConnect.addActionListener(new LoginBtnActionListener(this));
        m_fWindow.add(m_btnConnect);

        m_fWindow.setVisible(true);
    }

    //使用输入的信息尝试登录
    public void tryLogin() {
        String addr = m_tfAddress.getText();
        int port = Integer.parseInt(m_tfPort.getText());
        String username = m_tfUsername.getText();

        m_clientCore.setServerEndpoint(addr, port);
        if (!m_clientCore.tryLogin(username)) {
            JOptionPane.showMessageDialog(m_fWindow, "无法连接到服务器或用户名已被使用", "登录失败",
                JOptionPane.ERROR_MESSAGE);
        } else {
            m_lock.lock();
            m_cond.signal();
            m_lock.unlock();

            m_fWindow.setVisible(false);
            m_fWindow.dispose();
        }
    }

    //登录成功才返回
    public void waitForLogin() {
        try {
            m_lock.lock();
            m_cond.await();
            m_lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ClientCore m_clientCore;

    private JFrame m_fWindow;
    private JButton m_btnConnect;
    private JTextField m_tfAddress;
    private JTextField m_tfPort;
    private JTextField m_tfUsername;

    private Lock m_lock;
    private Condition m_cond;

}


class LoginBtnActionListener implements ActionListener {
    @Override public void actionPerformed(ActionEvent e) {
        m_loginWindow.tryLogin();
    }

    public LoginBtnActionListener(LoginWindow loginWindow) {
        m_loginWindow = loginWindow;
    }

    private LoginWindow m_loginWindow;
}
