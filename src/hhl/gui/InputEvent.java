package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class InputEvent implements ActionListener {
    JTextField input = new JTextField(10);
    JTextField output = new JTextField(10);
    BufferedReader buf;
    Socket client;
    String s = input.getText();     // 读取文本框中输入的内容
    String str;

    {
        try {
            client = new Socket("localhost", 8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void actionPerformed(ActionEvent e) {
        try {
            PrintStream pout = new PrintStream(client.getOutputStream());
            pout.print(s);
            buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            str = buf.readLine();
            output.setText(str);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
