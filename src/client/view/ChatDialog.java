package client.view;

import client.controller.IChatController;
import client.controller.IGetMessage;

import javax.swing.*;
import java.io.IOException;
import java.net.SocketException;

public class ChatDialog extends JPanel implements IGetMessage {
    JTextArea chat;
    IChatController controller;

    public ChatDialog(IChatController controller){
        this.controller = controller;
        setLayout(null);
        setBounds(100,100,500,170);
        chat = new JTextArea();
        JTextArea message = new JTextArea();
        JButton send = new JButton("Send");
        JButton exit = new JButton("Exit");

        chat.setBounds(10, 10, 450, 200);
        message.setBounds(10 ,220, 290,20);
        send.setBounds(310, 220, 70,20);
        exit.setBounds(390, 220, 70,20);

        add(chat);
        add(message);
        add(send);
        add(exit);

        setVisible(true);
        send.addActionListener(e -> {
            try {
                if (message.getText().length() > 0) {
                    controller.sendMessage(message.getText());
                    message.setText("");
                }
            }
            catch (SocketException e2){

            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        exit.addActionListener(l->{
            try {
                controller.exit();
            } catch (IOException e) {
                System.out.println("llllll");
//                e.printStackTrace();
            }
        });
    }

    @Override
    public void getMessage(String inMessage) {
        chat.append(inMessage.concat("\n"));
    }


}
