package client;

import client.controller.IChatController;
import client.controller.IGetMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientJob implements IChatController {
    private DataOutputStream out;
    private DataInputStream in;
    private IGetMessage controller;
    private Socket cs;
    private static final String HOST = "127.0.0.1";
    private static final String MESSAGE_TAG = "MSG:";
    private static final String LOGIN_TAG = "LGN:";
    private static final String EXIT_TAG = "EXT:";
    private static final int PORT = 7777;
    InputRead read;
    boolean isFinish;



    public ClientJob(String name,IGetMessage controller) throws IOException {
        this.controller=controller;
            cs = new Socket(HOST, PORT);
            out = new DataOutputStream(cs.getOutputStream());
            in = new DataInputStream(cs.getInputStream());


            read = new InputRead();
            isFinish = false;
            read.start();
            out.writeUTF(LOGIN_TAG.concat(name));
    }

    @Override
    public void sendMessage(String outMessage) throws IOException {
        out.writeUTF(MESSAGE_TAG.concat(outMessage));
        out.flush();
        System.out.println(MESSAGE_TAG.concat(outMessage));
    }

    @Override
    public void exit() throws IOException {
// закрывать потоки ввода вывода отдельно не надо, они закрываются при закрытии сокета автоматически
        sendMessage(EXIT_TAG);
        isFinish = true;
        // надо ли закрывать InputRead -  надо иначе исключение выпадает
        cs.close();
    }

    class InputRead extends Thread  {
        @Override
        public void run() {
            try {
                while (!isFinish) {
                    if (in.available() > 0) {
                        controller.getMessage(in.readUTF());
//                        System.out.println(in.readUTF());
                    }
                    Thread.sleep(50);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
