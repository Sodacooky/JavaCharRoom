package com.Common;

import java.util.Date;

//一个消息
public class Message {
    public String strOwner;
    public long lTime;
    public String strContent;

    //设置时间为当前
    public void setTimeNow() {
        Date date = new Date();
        lTime = date.getTime();
    }

}
