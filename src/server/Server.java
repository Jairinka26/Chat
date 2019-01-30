package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class Server {
    private static int PORT = 7777;
    ServerSocket serverSocket;
    Logging logging;
    ArrayList<ClientConnection> clientsList = new ArrayList();
//    List<ClientConnection> synchClientsList = Collections.synchronizedList(clientsList);
    CopyOnWriteArraySet<ClientConnection> synchClientsList = new CopyOnWriteArraySet();

    String outMessage;

    Server() throws IOException {
        serverSocket= new ServerSocket(PORT);
        Listener listener = new Listener();
        logging = new Logging();
        ConnectionListener connectionListener = new ConnectionListener();
        listener.start();
        connectionListener.start();
        logging.writeToLog(" Сервер запущен");
        System.out.println("Сервер запущен");

    }

    protected void sendingMessage (String message) throws IOException {
        for (ClientConnection clientConnection : synchClientsList) {
            try {
                clientConnection.out.writeUTF(message);
                clientConnection.out.flush();
                logging.writeToLog("Пользователю ".concat(clientConnection.name).concat(" отправлено сообщение: ").concat(message));

//                logging.writeToLog(time.toString().concat("Пользователю ").concat(clientConnection.name).concat(" отправлено сообщение: ".concat(message)));

            }
            catch (SocketException e){
                removeClient(clientConnection);
        }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void removeClient (ClientConnection clientConnection) throws IOException {
        clientConnection.socket.close();
        synchClientsList.remove(clientConnection);
        outMessage = clientConnection.name.concat(" вышел из чата");
        logging.writeToLog(outMessage);
        sendingMessage(outMessage);
    }

    class Listener extends Thread{
        private ClientConnection newClientConnection;
        @Override
        public void run() {
            while (true) {
                try {
                    newClientConnection = new ClientConnection(serverSocket.accept());
                    if (newClientConnection.created){
                        synchClientsList.add(newClientConnection);
                        outMessage = newClientConnection.name.concat(" вошел в чат");
                        sendingMessage(outMessage);
                        logging.writeToLog("Подключился новый пользователь: ".concat(newClientConnection.name));
//                        System.out.println("Подключился новый пользователь: ".concat(newClientConnection.name));
                    }
                    else {
                        newClientConnection = null;
                        System.out.println("Неудачная попытка подключения");
                    }

                }

                catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ConnectionListener extends Thread{
        String inMessage;
        String clientMessage;

        private String prefix;
        @Override
        public synchronized void run() {
            while(true){
                for (ClientConnection clientConnection : synchClientsList)
                {
                    try {
                        if (clientConnection.in.available() > 0){
                            inMessage = clientConnection.in.readUTF();
                            System.out.println("Получено сообшение: ".concat(inMessage));
                            prefix = inMessage.substring(0,4);
                            clientMessage = inMessage.substring(4);
                            switch (prefix){
                                case "EXT:":
                                    removeClient(clientConnection);
                                    break;
                                case "MSG:" :
                                    outMessage = new Message().buildMessage(clientConnection.name,clientMessage);
                                    sendingMessage(outMessage);
                                    break;
                            }
                            logging.writeToLog(outMessage);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    }





