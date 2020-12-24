import com.beust.jcommander.JCommander;

public class ServerMain {
    public static void main(String[] argv) {
        Args args = new Args();

        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);

        EchoServerSocket serverSocket = new EchoServerSocket();
        serverSocket.start(args.port);
    }
}
/*
java -jar chat-server.jar
java-jar chat-client.jar --server-ip=127.0.0.1
--server-port=43214
 */
