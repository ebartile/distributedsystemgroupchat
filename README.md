# CIT CHAT ROOMS (A Group Communication System)

## Description

CIT CHAT ROOMS is a robust and efficient group communication system designed to facilitate seamless communication among multiple devices. It utilizes a publish-subscribe model, allowing processes to subscribe to specific groups and publish messages to those groups. The system guarantees atomicity, ensuring that messages are either delivered to all members of the group or none at all. It also supports both FIFO-ordering and total-ordering, preserving the order of messages from a given sender and across different senders. CIT CHAT ROOMS is valuable for various scenarios such as collaborative discussions and interest groups.

## Instructions

To run the system, follow these steps:

1. Compile the required Java files using the Java compiler:
```shell
javac Server.java
javac Client.java
javac ClientHandler.java
```

2. Start the server by running the Server class:
```shell
java Server
```

3. Start multiple instances of the client by running the Client class in separate terminals or command prompt windows:
```shell
java Client
```

4. When prompted, enter a unique name for each client.

5. Each client can join a group by entering the group name at the prompt.

6. Clients can send messages to their respective groups by entering messages at the prompt.

7. Observe the client user interface to see the group communication in action.
