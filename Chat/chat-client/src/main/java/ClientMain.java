import com.beust.jcommander.JCommander;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] argv) {
        Args args = new Args();

        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);

        ClientSocket client;
        try {
            client = new ClientSocket(new Socket(args.serverIp, args.serverPort), new EchoServerSocket());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        Scanner sc = new Scanner(System.in);
        while (true) {
            String message = sc.nextLine();
            client.sendMessage(message);
        }
    }
}
