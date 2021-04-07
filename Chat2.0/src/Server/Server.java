package Server;

import TCP.TCPConectionListner;
import TCP.TCPConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Server implements TCPConectionListner {

    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    public Server() {
        System.out.println("SERVER RUNNING ...");
        try(ServerSocket serverSocket = new ServerSocket(8888)){
            while (true){
                try {
                    new TCPConnection(this, serverSocket.accept());
                    System.out.println("NEW CONNECTION: " + serverSocket.toString());

                }catch (IOException e){
                    System.out.println("TSPConnecion exeption...");
                }
            }
        }catch (IOException e){
            throw new RuntimeException("SWW",e);
        }
    }

    private void sendAll(String value){
        for (int i = 0; i < connections.size(); i++) {
            connections.get(i).sendMassage(value);
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendAll("New Client connected: " + tcpConnection);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendAll("New Client disconnected: " + tcpConnection);
    }

    @Override
    public synchronized void onReciveString(TCPConnection tcpConnection, String str) {
        sendAll(str);
    }

    @Override
    public synchronized void onExeption(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exeption: " + e);
    }
}
