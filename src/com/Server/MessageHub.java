package com.Server;

import com.Common.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//收集各用户线程的新消息
public class MessageHub {
    static private List<Message> sm_arrayMessage;

    static private Lock sm_lock;
    static private Condition sm_cond;

    static private boolean sm_hasInit = false;

    public static void init() {
        sm_hasInit = true;

        sm_arrayMessage = new ArrayList<>();
        sm_lock = new ReentrantLock();
        sm_cond = sm_lock.newCondition();
    }

    //加一个
    public static void add(String nickname, String content) {
        if (!sm_hasInit) {
            System.out.println("[Error] MessageHub 未初始化");
            return;
        }

        sm_lock.lock();

        Message message = new Message();
        message.strOwner = nickname;
        message.strContent = content;
        message.setTimeNow();
        sm_arrayMessage.add(message);

        sm_cond.signal();
        sm_lock.unlock();
    }

    //等待新消息，阻塞直到新消息
    public static void waitNewMessage() {
        if (!sm_hasInit) {
            System.out.println("[Error] MessageHub 未初始化");
            return;
        }

        try {
            //已经有消息不用等
            if (sm_arrayMessage.size() != 0) {
                return;
            }

            //没消息才等
            sm_lock.lock();
            sm_cond.await();
            sm_lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //取出一个消息
    //如果没有新消息就试图取出，返回null
    public static Message getMessage() {
        if (!sm_hasInit) {
            System.out.println("[Error] 未初始化");
            return null;
        }

        if (sm_arrayMessage.size() == 0) {
            return null;
        }

        return sm_arrayMessage.remove(0);
    }
}
