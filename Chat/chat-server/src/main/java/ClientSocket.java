import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocket implements Runnable {

    private Socket client;
    private EchoServerSocket server;
    private Scanner fromServer;
    private PrintWriter toServer;
    private static int clientsCount = 0;

    public ClientSocket(Socket client, EchoServerSocket server) {
        try {
            clientsCount++;
            this.client = client;
            this.server = server;
            toServer = new PrintWriter(this.client.getOutputStream(), true);
            fromServer = new Scanner(this.client.getInputStream());
            new Thread(receiverMessagesTask).start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Runnable receiverMessagesTask = () -> {
        try {
            while (true) {
                server.sendMessageToAllClients("New person in chat! Clients in chat: " + clientsCount);
                break;
            }

            while (true) {
                if (fromServer.hasNextLine()) {
                    String clientMessage = fromServer.nextLine();
                    if (clientMessage.equalsIgnoreCase("exit")) {
                        this.exitFromChat();
                        break;
                    }
                    System.out.println("From server: " + clientMessage);
                    server.sendMessageToAllClients(clientMessage);
                }
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    };

    @Override
    public void run() {

    }

    public void sendMessage(String message) {
        toServer.println(message);
    }

    public void exitFromChat() {
        clientsCount--;
        server.sendMessageToAllClients("Clients in chat: " + clientsCount);
        server.removeClient(this);
    }
}
