package client.controller;

import client.ClientJob;
import client.view.*;

import java.io.IOException;
import java.net.ConnectException;

///import com.client.view.Frame;

public class Controller implements ILoginController,IGetMessage,IChatController {
    Frame frame;
    IGetMessage chatPanel;
    ClientJob cj;
    private boolean error;

    public void start(){
        this.frame = new Frame((ILoginController) this);
    }
    @Override
    public void login(String name){
        try {
            error = false;
            cj = new ClientJob(name,(IGetMessage) this);
        }catch (ConnectException e){
            error = true;
        }
        catch (IOException e){
            e.printStackTrace();
        }

        if (error == false)
            chatPanel = frame.openChatDialog((IChatController)this);
        else
            noConnection();

    }

    public void noConnection (){
        frame.showError("Нет соединения с сервером");
    }

    @Override
    public void cancel() {
        frame.dispose();
    }

    @Override
    public void getMessage(String inMessage){
        chatPanel.getMessage(inMessage);
    }

    @Override
    public void sendMessage(String outMessage)  {
        try {
            cj.sendMessage(outMessage);
        } catch (ConnectException e){
            noConnection();
//            System.out.println("Нет соединения с сервером");
//            error = true;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void exit(){
        try {
            cj.exit();
        }
        catch (ConnectException e){
            noConnection();
//            System.out.println("Нет соединения с сервером");
//            error = true;
        }
        catch (IOException e){
            e.printStackTrace();
        }

        frame.dispose();
    }
}
