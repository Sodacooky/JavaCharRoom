package com.Client;

import java.io.IOException;

public class ClientMain extends Thread {
    public ClientMain() {
        m_clientCore = new ClientCore();

        Thread thr = new Thread(this);
        thr.start();
    }

    public void run() {
        ChatWindow cw = new ChatWindow(m_clientCore);

        while (true) {
            try {
                cw.pushInMessage(m_clientCore.waitForMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private ClientCore m_clientCore;
}
