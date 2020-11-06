package com.Client;

public class ClientMain extends Thread {
    public ClientMain() {
        Thread thr = new Thread(this);
        thr.start();
    }

    public void run() {
        m_clientCore = new ClientCore();
        
        ChatWindow cw = new ChatWindow(m_clientCore);
        cw.updateHistory();
    }

    private ClientCore m_clientCore;
}
