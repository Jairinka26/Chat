package client.view;

import client.controller.IChatController;
import client.controller.IGetMessage;
import client.controller.ILoginController;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    JPanel loginPanel;
    JPanel chatPanel;
    public Frame(ILoginController controller) throws HeadlessException {
        setBounds(400, 150, 490, 310);
        setTitle("Chat v.1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        openLoginDialog(controller);

    }

    void openLoginDialog(ILoginController controller){
        add(loginPanel = new LoginPanel(controller));
        setVisible(true);
    }

    public IGetMessage openChatDialog(IChatController controller){
        loginPanel.setVisible(false);
        remove(loginPanel);
        add( chatPanel = new ChatDialog(controller));
        chatPanel.setVisible(true);
        return (IGetMessage) chatPanel;
    }

    public void showError (String errorMessage){
        JOptionPane.showMessageDialog(loginPanel,errorMessage);
    }

}
