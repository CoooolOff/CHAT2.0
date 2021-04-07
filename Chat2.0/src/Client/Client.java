package Client;


import Server.Auth.AutentificationDB;
import TCP.TCPConectionListner;
import TCP.TCPConnection;

import java.io.IOException;
import java.util.Scanner;

public class Client implements TCPConectionListner {
    private final TCPConnection connection;
    private final String HOST = "127.0.0.1";
    private final int PORT = 8888;
    private String name;
    private AutentificationDB autentificationDB;

    public Client(){
        try {
            connection = new TCPConnection(this, HOST,PORT);
            autentificationDB = new AutentificationDB();
            Scanner scanner = new Scanner(System.in);
            autentificationDB.regist(scanner);
            name = autentificationDB.getName();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        connection.sendMassage(name + " : " +
                                scanner.nextLine());
                    }
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }


    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("You connection ready");
    }
    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        System.out.println("Connection close");
    }

    @Override
    public void onReciveString(TCPConnection tcpConnection, String str) {
        System.out.println(str);
    }

    @Override
    public void onExeption(TCPConnection tcpConnection, Exception e) {
        System.out.println("Conection exeption: " + e);
    }

    public String getName() {
        return name;
    }
}
