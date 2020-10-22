package gui;

import javax.swing.*;

public class SliptWindow extends JFrame {
    JTextArea textInput;
    JTextArea textOutput;
    JButton button;
    JLabel lab;
    JSplitPane jsp;

    public SliptWindow() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {

        textInput = new JTextArea(200, 100);
        // textInput.setBounds(500, 50, 400, 300);
        textInput.setLineWrap(true);
        textInput.setVisible(true);

        // textInput.setPreferredSize(new Dimension(200, 200));

        lab = new JLabel("输入框");
        lab.setVisible(true);
        textOutput = new JTextArea(300, 100);
        textOutput.setBounds(500, 400, 400, 300);
        textOutput.setLineWrap(true);
        textOutput.setVisible(true);
        // textOutput.setPreferredSize(new Dimension(200, 200));
        add(lab);

        jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, textInput, textOutput);
        jsp.setBounds(400, 400, 50, 50);
        jsp.setContinuousLayout(true);
        jsp.setVisible(true);
        jsp.setDividerLocation(400);
        add(jsp);

/*
        button = new JButton("发送");
        button.setBounds(60, 80, 1, 1);
        button.setVisible(true);
        add(button);
*/
    }
}


