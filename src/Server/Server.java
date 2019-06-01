package Server;

import DatabaseAccessObjects.QueryObjects.DatabaseConnector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    static public DatabaseConnector databaseConnectorForLibraryInchargeInterface;
    static public DatabaseConnector databaseConnectorForStudentAndEmployeeInterface;
    ArrayList<Socket> connections = new ArrayList<>();
    ServerSocket serverSocket;
    ExecutorService pool = Executors.newCachedThreadPool();

    public Server() throws IOException {

    }

    private boolean initiateConnection(String connectionType, String connectionPassword) {
//        String command = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqld.exe";
//
//        try {
//            Process process = Runtime.getRuntime().exec(command);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        switch (connectionType) {
            case "library_incharge": {
                databaseConnectorForLibraryInchargeInterface = new DatabaseConnector();
                databaseConnectorForLibraryInchargeInterface.connectionType = connectionType;
                databaseConnectorForLibraryInchargeInterface.connectionPassword = connectionPassword;
                databaseConnectorForLibraryInchargeInterface.setConnectionAttributes("jdbc:mysql://localhost:3307/skncoe_computer_department?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true", Server.databaseConnectorForLibraryInchargeInterface.connectionType, Server.databaseConnectorForLibraryInchargeInterface.connectionPassword);
                return databaseConnectorForLibraryInchargeInterface.connect();
            }
            case "student_and_employee": {
                databaseConnectorForStudentAndEmployeeInterface = new DatabaseConnector();
                databaseConnectorForStudentAndEmployeeInterface.connectionType = connectionType;
                databaseConnectorForStudentAndEmployeeInterface.connectionPassword = connectionPassword;
                databaseConnectorForStudentAndEmployeeInterface.setConnectionAttributes("jdbc:mysql://localhost:3307/skncoe_computer_department?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true", Server.databaseConnectorForStudentAndEmployeeInterface.connectionType, Server.databaseConnectorForStudentAndEmployeeInterface.connectionPassword);
                return databaseConnectorForStudentAndEmployeeInterface.connect();
            }
            default:
                return false;
        }
    }

    private boolean closeConnection(String connectionType) {
        try {
            if (null == connectionType) {
                return false;
            } else {
                switch (connectionType) {
                    case "library_incharge":
                        databaseConnectorForLibraryInchargeInterface.disconnect();
                        return true;
                    case "student_and_employee":
                        databaseConnectorForStudentAndEmployeeInterface.disconnect();
                        return true;
                    default:
                        return false;
                }
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("Failed to close database interfaces");
            return false;
        }
    }

    public void startServer() throws Exception {
        this.serverSocket = new ServerSocket(8090);
        boolean openedSuccessfully = true;
        if (this.initiateConnection("library_incharge", "CDIS@SKN_dli")) {
            System.out.println("libray_incharge connection initiated");
        } else {
            System.out.println("library_incharge connection failed");
            openedSuccessfully = false;
        }
        if (this.initiateConnection("student_and_employee", "CDIS@SKN_se")) {
            System.out.println("student_and_employee connection initiated");
        } else {
            System.out.println("student_and_employee connection failed");
            openedSuccessfully = false;
        }
        if (openedSuccessfully) {
            System.out.println("Connection established with database");
        } else {
            System.out.println("Failed to establish connection with database");
            return;
        }

        System.out.println("Server started.");
        //String publicIP=IpChecker.getIp();
        String localIP = InetAddress.getLocalHost().toString();
        // System.out.println("Public IP: "+publicIP);
        System.out.println("Local IP: " + localIP);
        while (true) {
            Socket socket;
            socket = serverSocket.accept();
            System.out.println(socket.getInetAddress() + " connected on " + socket.getPort());
            connections.add(socket);
            ServerThread serverThread = new ServerThread(socket, this);
            pool.execute(serverThread);
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        Thread initiateServer = new Thread(() -> {
            try {
                server.startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println("#Menu: \n\t1. Start server. \n\t2. Show status.\n\t3. Show connected clients.\n\t4. Stop Server.");
        System.out.print("#Select:");
        do {
            int choice;
            Scanner scan = new Scanner(System.in);
            choice = scan.nextInt();
            switch (choice) {
                case 1:
                    if (initiateServer.isAlive()) {
                        System.out.println("#Server is running...");
                    } else {
                        initiateServer.start();
                    }
                    break;
                case 2:
                    if (initiateServer.isAlive()) {
                        System.out.println("#Server is running...");
                        System.out.println("#Currently " + server.connections.size() + " clients are connected");
                    } else {
                        System.out.println("#Server has stopped");
                    }
                    break;
                case 3:
                    if (server.connections.size() > 0) {
                        System.out.println("#Client list: ");
                        for (int i = 0; i < server.connections.size(); i++) {
                            System.out.println(i + ") " + server.connections.get(i).getInetAddress() + " is connected on " + server.connections.get(i).getPort());
                        }
                    } else {
                        System.out.println("No single client is connected.");
                    }
                    break;
                case 4: {
                    //TODO code to stop server
                    server.closeConnection("libray_incharge");
                    server.closeConnection("student_and_employee");
                    System.out.println("Server stopped.");
                    System.exit(0);
                }
            }

        } while (true);
    }
}
