package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class OutputEvent implements ActionListener {
    //JTextField output = new JTextField(10);

    public void actionPerformed(ActionEvent event) {
        Socket client = null;
        try {
            client = new Socket("localhost", 8888);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        while (true) {
            try {
                BufferedReader buf1 =
                    new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("\n\r" + buf1.readLine());          // 打印其他客户端输入的信息
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
