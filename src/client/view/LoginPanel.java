package client.view;

import client.controller.ILoginController;

import javax.swing.*;

public class LoginPanel extends JPanel {
    ILoginController controller;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 20;

    public LoginPanel(ILoginController controller){
        this.controller = controller;
        setLayout(null);
//        setTitle("Login");
        setBounds(100,100,215,170);
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Имя:");
        label.setBounds(10,10,100, 20);

        JTextField name = new JTextField();
        name.setBounds(10,40,170, 20);

        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");
        loginButton.setBounds(10, 70,BUTTON_WIDTH,BUTTON_HEIGHT);
        cancelButton.setBounds(100, 70,BUTTON_WIDTH,BUTTON_HEIGHT);

        add(label);
        add(name);
        add(loginButton);
        add(cancelButton);

        setVisible(true);

        loginButton.addActionListener(l -> {
                    setVisible(false);
                    controller.login(name.getText());
                }
        );

        cancelButton.addActionListener(l -> {controller.cancel();});
    }
}

