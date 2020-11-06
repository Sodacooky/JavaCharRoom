package com.Client;

import com.Common.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

//用于构造聊天记录信息框中很长的字符串
//内部储存这个字符串，如果过长会从开头删除部分
public class HistoryMsgStrBuilder {
    public HistoryMsgStrBuilder() {
        m_strBuilder = new StringBuilder();
        m_strBuilder.append("欢迎进入聊天室\n");
    }

    //加入一条消息，刷新内部储存的字符串
    public void appendMessage(Message msg) {
        m_strBuilder.append(msg.strOwner + " " + __transTime(msg.lTime) + ":\n");
        m_strBuilder.append(msg.strContent + "\n\t--------\n");
        __cleanup();
    }

    //取出最新版本的字符串
    public String getString() {
        return m_strBuilder.toString();
        //我知道这样子是不好的做法，但是这样方便啊！
    }

    //将long型时间转换为时间字符串 hh:mm:ss
    private String __transTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        return sdf.format(date);
    }

    //删除直到长度小于2048
    private void __cleanup() {
        while (m_strBuilder.length() >= 2048) {
            int index = m_strBuilder.indexOf("\n", 0);
            if (index == -1) {
                return;
            } else {
                m_strBuilder.delete(0, index);
            }
        }
    }

    private StringBuilder m_strBuilder;
}
