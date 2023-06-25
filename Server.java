import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private int port;
    private Map<String, List<PrintWriter>> groups;

    public Server(int port) {
        this.port = port;
        this.groups = new HashMap<>();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                Thread clientThread = new Thread(new ClientHandler(clientSocket, writer, this));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void joinGroup(String groupName, PrintWriter writer) {
        synchronized (groups) {
            List<PrintWriter> groupMembers = groups.getOrDefault(groupName, new ArrayList<>());
            groupMembers.add(writer);
            groups.put(groupName, groupMembers);
            System.out.println("A client joined group " + groupName);
        }
    }

    public void sendMessage(String groupName, String sender, String message) {
        synchronized (groups) {
            List<PrintWriter> groupMembers = groups.get(groupName);
            if (groupMembers != null) {
                for (PrintWriter memberWriter : groupMembers) {
                    memberWriter.println(sender + ": " + message);
                }
            }
        }
    }

    public static void main(String[] args) {
        int port = 8080; // Change to desired port number
        Server server = new Server(port);
        server.start();
    }
}
