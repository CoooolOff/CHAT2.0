package TCP;

public interface TCPConectionListner {
    void onConnectionReady(TCPConnection tcpConnection);
    void onDisconnect(TCPConnection tcpConnection);
    void onReciveString(TCPConnection tcpConnection, String str);
    void onExeption(TCPConnection tcpConnection, Exception e);
}
