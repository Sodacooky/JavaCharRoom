package com.Client;

import com.Common.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ChatWindow {
    public ChatWindow(ClientCore clientCore) {
        m_clientCore = clientCore;

        LoginWindow lw = new LoginWindow(m_clientCore);
        lw.waitForLogin();

        m_history = new HistoryMsgStrBuilder();
        __buildWindow();
        (new MsgWaitThread(m_history, clientCore, this)).start();
    }

    private void __buildWindow() {
        m_fWindow = new JFrame("聊天室");
        m_fWindow.setSize(640, 480);
        m_fWindow.setResizable(false);
        m_fWindow.setLayout(null);
        m_fWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        m_taHistory = new JTextArea(m_history.getString());
        m_taHistory.setBounds(0, 0, 640, 480 - 32);
        m_taHistory.setEditable(false);
        m_taHistory.setLineWrap(true);
        m_taHistory.setWrapStyleWord(true);
        JScrollPane scroll_pane = new JScrollPane();
        scroll_pane.add(m_taHistory);
        scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        m_fWindow.add(scroll_pane);

        m_taInput = new JTextArea();
        m_taInput.setBounds(0, 480 - 30, 600, 30);
        m_fWindow.add(m_taInput);

        m_btnSend = new JButton("发送");
        m_btnSend.setBounds(608, 480 - 30, 32, 30);
        m_btnSend.setToolTipText("快捷键：Ctrl+Enter");
        m_btnSend.registerKeyboardAction(new SendBtnActionListener(this),
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK),
            JComponent.WHEN_IN_FOCUSED_WINDOW);
        m_btnSend.addActionListener(new SendBtnActionListener(this));
        m_fWindow.add(m_btnSend);

        m_fWindow.setVisible(true);
    }

    //将消息框的内容发出去
    //当不合法时返回false并弹窗
    //为空也返回false
    public boolean sendMessage() {
        String msg = m_taInput.getText();

        //一条有"[[!"的消息不是一条好消息
        if (msg.contains("[[!")) {
            JOptionPane.showMessageDialog(m_fWindow, "你输入的内容不应有[[!xxx]]之类的控制符", "错误",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (msg.length() == 0) {
            return false;
        }

        try {
            m_clientCore.sendMessage(msg);
        } catch (IOException e) {
            JOptionPane
                .showMessageDialog(m_fWindow, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }

        return true;
    }

    //更新历史信息文本框
    public void updateHistory() {
        m_taHistory.setText(m_history.getString());
    }


    private ClientCore m_clientCore;

    private JFrame m_fWindow;
    private JButton m_btnSend;
    private JTextArea m_taHistory;
    private JTextArea m_taInput;

    private HistoryMsgStrBuilder m_history;
}


class SendBtnActionListener implements ActionListener {
    public SendBtnActionListener(ChatWindow chatWindow) {
        m_chatWindow = chatWindow;
    }

    @Override public void actionPerformed(ActionEvent e) {
        m_chatWindow.sendMessage();
    }

    private ChatWindow m_chatWindow;
}


class MsgWaitThread extends Thread {
    public MsgWaitThread(HistoryMsgStrBuilder hmsb, ClientCore cc, ChatWindow cw) {
        m_hmsb = hmsb;
        m_cc = cc;
        m_cw = cw;
    }

    public void run() {
        try {
            while (true) {
                Message msg = m_cc.waitForMessage();
                if (msg == null) {
                    continue;
                }

                m_hmsb.appendMessage(msg);
                m_cw.updateHistory();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }

    }

    private HistoryMsgStrBuilder m_hmsb;
    private ClientCore m_cc;
    private ChatWindow m_cw;
}
