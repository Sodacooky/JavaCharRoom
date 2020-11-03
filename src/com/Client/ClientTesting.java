package com.Client;

import com.Common.Message;

import java.io.IOException;

public class ClientTesting {
    public ClientTesting() {

    }


    public void run() {
        try {
            ClientCore core = new ClientCore();
            System.out.println(core.tryLogin("Sodacooky"));

            core.sendMessage("我日你妈");

            Message msg = core.waitForMessage();
            System.out.println(msg.strOwner + ":" + msg.strContent);

            core.logout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
