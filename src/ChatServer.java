import java.io.*;
import java.net.*;
import java.security.PrivateKey;

public class ChatServer{
    private boolean started = false;
    private ServerSocket ss = null;

    public static void main(String[] args) {
        new ChatServer().started();
    }

    public void started() {
        try {
            ss = new ServerSocket(8888);
            started = true;
        } catch (BindException erro) {
            System.out.println("This port has been used!");
            System.out.println("Please close the application,and run again!");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
            try {
                while (started) {
                    Socket s = ss.accept();
                    System.out.println("A client connetcted!");
                    new Thread(new Client(s)).start();
                }
            }
             catch (IOException ioException) {
                ioException.printStackTrace();
            }finally {
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Client implements Runnable{
        Socket s = null;
        DataInputStream dis = null;
        private boolean bConnected = false;

        public Client(Socket s) {
            this.s = s;
            try {
                dis = new DataInputStream(this.s.getInputStream());
                bConnected = true;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str);
                    if (str == "bye"){
                        bConnected = false;
                    }
                }
            }catch (EOFException e){
                System.out.println("对方已经下线");}
            catch (IOException ioe) {
                ioe.printStackTrace();
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
