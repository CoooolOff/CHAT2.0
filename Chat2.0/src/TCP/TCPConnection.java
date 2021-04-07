package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPConnection {
    private final Socket socket;
    TCPConectionListner eventListner;
    private  final  Thread rxThread;
    private final DataInputStream in;
    private final DataOutputStream out;

    public TCPConnection(TCPConectionListner eventListner, String ipAddr, int port) throws IOException{
        this(eventListner, new Socket( ipAddr,port));
    }

    public TCPConnection(TCPConectionListner eventListner, Socket socket) throws IOException {
        this.eventListner = eventListner;
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    eventListner.onConnectionReady(TCPConnection.this);
                    while (!rxThread.isInterrupted()){
                        eventListner.onReciveString(TCPConnection.this, in.readUTF());
                    }
                }catch (IOException e){
                    eventListner.onExeption(TCPConnection.this , e);
                }finally {
                    eventListner.onDisconnect(TCPConnection.this);
                }
            }
        });
        rxThread.start();
    }

    public synchronized void sendMassage(String value){
        try {
            out.writeUTF(value);
        } catch (IOException e) {
            eventListner.onExeption(TCPConnection.this,e);
            disconnect();
        }
    }

    public synchronized void disconnect(){
        rxThread.isInterrupted();
        try {
            socket.close();
        } catch (IOException e) {
            eventListner.onExeption(TCPConnection.this, e);
        }
    }
    @Override
    public String toString() {
        return "TCPConnection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
