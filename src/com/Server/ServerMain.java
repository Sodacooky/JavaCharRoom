package com.Server;

//
public class ServerMain extends Thread {

    //构造就开始运行
    public ServerMain() {
        UserList.init();
        MessageHub.init();

        Thread thr = new Thread(this);
        thr.start();

        handleMessage();
    }

    //开始运行服务器
    public void run() {
        LoginHub login_sys = new LoginHub();
        while (true) {
            User usr_new = login_sys.waitForLogin();
            if (usr_new != null) {
                UserList.addUser(usr_new);
            }
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
