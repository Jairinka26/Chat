package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection {
    Socket socket;
    String name;
    String inMessage;
    DataInputStream in;
    DataOutputStream out;
    boolean created = false;

    ClientConnection(Socket socket) throws IOException, InterruptedException {
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        setName();
    }

    private void setName() throws IOException, InterruptedException {
            Thread.sleep(2000);
            if (in.available() > 0 ) {
                inMessage = in.readUTF();
                if (inMessage.startsWith("LGN:")) {
                    this.name = inMessage.substring(4);
                    created = true;
                }
                else {
                    created = false;
                    System.out.println("Неправильный формат имени");
                    // TODO: close socket
                }
            }
            else {
                created = false;
                System.out.println("Сервер не получил имя клиента");
                // TODO: close socket
            }
    }
}

