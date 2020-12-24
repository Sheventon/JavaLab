import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoServerSocket {

    private ArrayList<ClientSocket> clients = new ArrayList<>();

    public void start(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server is running");

            while (true) {
                Socket client = server.accept();
                ClientSocket clientSocket = new ClientSocket(client, this);
                clients.add(clientSocket);
                new Thread(clientSocket).start();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendMessageToAllClients(String message) {
        for (ClientSocket clientSocket : clients) {
            clientSocket.sendMessage(message);
        }
    }

    public void removeClient(ClientSocket clientSocket) {
        clients.remove(clientSocket);
    }
}
