package com.Common;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Tool {
    //将所有\n转义为[[!BR]]
    public static String transBreakRow(String str) {
        return str.toString().replaceAll("\n", "[[!BR]]");
    }

    //将所有的[[!BR]]恢复为\n
    public static String recoverBreakRow(String str) {
        return str.replaceAll("\\[\\[!BR]]", "\n");
    }
}
