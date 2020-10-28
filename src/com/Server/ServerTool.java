package com.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerTool {
    //向socket目标发送一行信息
    //会自动在末尾加上\n，如果没有的话
    public static void sendMessage(Socket dstSock, String content) {
        try {
            OutputStreamWriter output = new OutputStreamWriter(dstSock.getOutputStream());
            if (!content.endsWith("\n")) {
                output.write(transBreakRow(content + "\n"));
            } else {
                output.write(transBreakRow(content));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从socket等待一行消息
    //并将所有[[!BR]]恢复为\n
    //返回的字符串正常来说已经没有了\n结尾(readLine特性
    public static String waitMessage(Socket srcSock) {
        try {
            BufferedReader input =
                new BufferedReader(new InputStreamReader(srcSock.getInputStream()));
            return recoverBreakRow(input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }


    //将非末尾的\n转义为[[!BR]]
    public static String transBreakRow(String str) {
        StringBuilder str_builder = new StringBuilder(str);

        if (str.endsWith("\n")) {
            str_builder.deleteCharAt(str_builder.lastIndexOf("\n"));
            return str_builder.toString().replaceAll("\n", "[[!BR]]") + "\n";
        } else {
            return str_builder.toString().replaceAll("\n", "[[!BR]]");
        }

    }

    //将所有的[[!BR]]恢复为\n
    public static String recoverBreakRow(String str) {
        return str.replaceAll("\\[\\[!BR\\]\\]", "\n");
    }


}
