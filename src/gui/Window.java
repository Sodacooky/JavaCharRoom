package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Window extends JFrame {
    JTextArea textInput;
    JTextArea textOutput;
    JButton button;
    JLabel lab;
    JSplitPane jsp;

    public Window() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        setLayout(null);

        textInput = new JTextArea(20, 10);
        textInput.setBounds(500, 400, 400, 300);
        textInput.setLineWrap(true);
        textInput.setVisible(true);
        add(textInput);

        // textInput.setPreferredSize(new Dimension(200, 200));

        lab = new JLabel("输入框");
        lab.setVisible(true);
        textOutput = new JTextArea(20, 10);
        textOutput.setBounds(500, 50, 400, 300);
        textOutput.setLineWrap(true);
        textOutput.setVisible(true);
        add(textOutput);
        ActionListener outputevent = new OutputEvent();

        // textOutput.setPreferredSize(new Dimension(200, 200));
        add(lab);

        button = new JButton();
        Font mf = new Font(" ", Font.BOLD, 8);
        button.setBounds(910, 450, 50, 50);
        button.setFont(mf);
        button.setText("发送");
        button.setVisible(true);
        add(button);
        button.addActionListener(new InputEvent());
    }
}


