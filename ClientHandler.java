import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter writer;
    private Server server;

    public ClientHandler(Socket clientSocket, PrintWriter writer, Server server) {
        this.clientSocket = clientSocket;
        this.writer = writer;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientName = reader.readLine();
            String groupName = reader.readLine();
            server.joinGroup(groupName, writer);

            String input;
            while ((input = reader.readLine()) != null) {
                server.sendMessage(groupName, clientName, input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
