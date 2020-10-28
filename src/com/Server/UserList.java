package com.Server;

import java.util.ArrayList;
import java.util.List;

//记录登录的用户
public class UserList {
    private static List<User> sm_listUser;

    private static boolean sm_hasInit = false;

    //初始化这个列表
    static void init() {
        sm_hasInit = true;
        sm_listUser = new ArrayList<>();
    }

    //用户名是否已经存在
    //@Return: true存在
    public static boolean isUserExist(String usrname) {
        if (!sm_hasInit) {
            System.out.println("[Error]\tUserList isn't initialized.");
            return false;
        }
        for (User usr : sm_listUser) {
            if (usr.strNickname.equals(usrname)) {
                return true;
            }
        }
        return false;
    }

    //添加用户
    //@Return: true添加成功
    public static boolean addUser(User usr) {
        if (!sm_hasInit) {
            System.out.println("[Error]\tUserList isn't initialized.");
            return false;
        }

        if (isUserExist(usr.strNickname)) {
            return false;
        }

        sm_listUser.add(usr);
        return true;
    }

    //删除用户
    //@Return: true删除成功
    public static boolean deleteUser(String usrname) {
        if (!sm_hasInit) {
            System.out.println("[Error]\tUserList isn't initialized.");
            return false;
        }

        for (int index = 0; index != sm_listUser.size(); index += 1) {
            if (sm_listUser.get(index).strNickname.equals(usrname)) {
                sm_listUser.remove(index);
                return true;
            }
        }
        return false;
    }

    //向所有用户广播
    public static void broadcastMessage(Message msg) {
        for (User usr : sm_listUser) {
            usr.broadcastMessage(msg);
        }
    }

}
