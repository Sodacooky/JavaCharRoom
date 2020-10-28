package com.Server;

//
public class ServerMain extends Thread {

    //构造就开始运行
    ServerMain() {
        (new Thread(this)).start();
        handleMessage();
    }

    //开始运行服务器
    public void run() {
        UserList.init();
        MessageHub.init();

        LoginHub login_sys = new LoginHub();
        while (true) {
            User usr_new = login_sys.waitForLogin();
            UserList.addUser(usr_new);
        }
    }

    //处理消息
    private void handleMessage() {
        while (true) {
            //等待
            MessageHub.waitNewMessage();
            //广播
            UserList.broadcastMessage(MessageHub.getMessage());
        }
    }

}
