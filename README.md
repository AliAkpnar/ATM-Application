# ATM Client-Server Application

This application is a Java-based project developed using the Spring Boot framework and Maven for dependency management.
The application is designed to simulate an ATM system. 

# Purpose

The purpose of this project is to simulate an ATM system.
The application provides a command-line interface for users to interact with the ATM system, allowing them to perform various operations such as login, view balance, withdraw cash, deposit cash, change pin, and logoff. 

# Why WebSockets?

WebSockets are used in this project to facilitate real-time communication between the client and the server.
In a typical HTTP connection, the client sends a request to the server and waits for a response.
However, in a WebSocket connection, both the client and the server can send messages to each other at any time.
This makes WebSockets ideal for applications that require real-time, bidirectional communication, such as this ATM system.  

# Key Components

- AtmappApplication: This class is the main class for the ATM Application (server).
- Application: This class is the main class for the ATM Client application.
- WebSocketConfig: This class is responsible for configuring WebSocket connections.
- CommandListener: This class listens to commands coming from the user and executes the corresponding actions.
- WebSocketListener: This class is responsible for managing WebSocket connections.

# How it Works

The application starts by initializing the Spring Boot application context.
It then scans the atm.client and com.honeywell.atmapp packages for Spring components and starts the application context.
Once the application context is started, it prepares the WebSocketListener and starts listening to commands from the user using the CommandListener.  

# Running the Applications
The ATM system consists of two main applications: the ATM Application (server) and the ATM Client. To properly run the system, follow the steps below:

1. **Run the ATM Application (Server):** Start the server application first. To do this, execute the `main` method in the `AtmappApplication` class.
This will start the Spring Boot application and initialize all necessary components.

2. **Run the ATM Client:** After the server application is running, start the client application.
Execute the `main` method in the `Application` class of the ATM Client.
This will initialize the WebSocket connection and start listening to commands from the WebSocket connection using the `CommandListener`.

3. **Interact with the ATM System:** Once both the server and client applications are running, you can interact with the ATM system using the command-line interface provided by the client application.
You can perform various operations such as login, view balance, withdraw cash, deposit cash, change pin, and logoff.

4. **Stop the Applications:** To stop the applications, simply terminate the running processes in your IDE or use the appropriate commands in your terminal.

5. **Note:** This application may throw `ExecutionException`, `InterruptedException`, or `TimeoutException` if there are issues with the execution or the WebSocket connection.


# Design Patterns

This project utilizes the Strategy Design Pattern for handling different types of ATM operations such as login, view balance, withdraw cash, deposit cash, change pin, and logoff.
Each operation is encapsulated in its own class that implements a common interface, allowing the application to switch between different operations at runtime based on the user's input.

# Permissions

The application has been designed with a focus on security and permissions.
Each operation requires the user to be authenticated.
The authentication process involves checking the user's credentials and ensuring they have the necessary permissions to perform the requested operation.

# WebSocket Connection

The application establishes a WebSocket connection to facilitate real-time, bidirectional communication between the client and the server.
The connection is established once when the client application starts and remains open until the user logs off.
This persistent connection allows the client and server to send messages to each other at any time, making the application more responsive and efficient.

# Subscription

The client application subscribes to the server application. This subscription allows the client to receive updates from the server in real-time.
For example, when the user performs an operation such as withdrawing cash, the server sends a message to the client with the result of the operation,
and the client immediately displays this information to the user.

# Interdependency

The ATM Client and ATM Application are interdependent.
The ATM Client sends commands to the ATM Application via the WebSocket connection, and the ATM Application executes these commands and sends the results back to the ATM Client.
Both applications need to be running for the system to function properly.

# Dependencies

- Java 17
- Spring Boot
- Maven