import java.io.*;
import java.net.*;

public class ChatServer {

    public static void main(String[] args) {

        boolean started = false;
        DataInputStream dis = null;

        try {

            ServerSocket ss = new ServerSocket(8888);
            started = true;
            while(started){
                boolean connected = false;
                Socket s = ss.accept();
                connected = true;
                dis = new DataInputStream(s.getInputStream());
                while (connected){
                    String str = dis.readUTF();
                    System.out.println(str);
                }
                dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
