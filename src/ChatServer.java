import java.io.*;
import java.net.*;

public class ChatServer {

    public static void main(String[] args) {

        boolean started = false;
        ServerSocket ss = null;
        Socket s = null;
        DataInputStream dis = null;


        try {
            ss = new ServerSocket(8888);
            started = true;
        }catch(BindException erro){
            System.out.println("This port has been used!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (started) {
                boolean connected = false;
                s = ss.accept();
                connected = true;
                dis = new DataInputStream(s.getInputStream());
                while (connected) {
                    String str = dis.readUTF();
                    System.out.println(str);
                }
            }
        }catch(EOFException e1){
            System.out.println("Client closed!");
        }
        catch ( IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (dis != null) dis.close();
                if (s != null) s.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }
}
