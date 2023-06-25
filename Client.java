import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends JFrame {
    private String serverAddress;
    private int serverPort;
    private String clientName;
    private String groupName;

    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;

        chatArea = new JTextArea(10, 30);
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        messageField = new JTextField(20);
        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    sendMessage(message);
                    messageField.setText("");
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        panel.add(inputPanel, BorderLayout.SOUTH);

        setTitle("CIT CHAT ROOMS Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(panel);
        pack();
        setVisible(true);
    }

    public void start() {
        try {
            socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to the server.");

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Send client name and group name to the server
            writer.println(clientName);
            writer.println(groupName);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String input;
                    try {
                        while ((input = reader.readLine()) != null) {
                            chatArea.append(input + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public static void main(String[] args) {
        String serverAddress = "localhost"; // Change to the server's IP address or hostname
        int serverPort = 8080; // Change to the server's port number

        Client client = new Client(serverAddress, serverPort);

        // Get client name and group name from user input
        String clientName = JOptionPane.showInputDialog("Enter your name:");
        String groupName = JOptionPane.showInputDialog("Enter the group name:");
        if (clientName != null && !clientName.isEmpty() && groupName != null && !groupName.isEmpty()) {
            client.clientName = clientName;
            client.groupName = groupName;
            client.start();
        } else {
            System.out.println("Invalid client name or group name.");
        }
    }
}
