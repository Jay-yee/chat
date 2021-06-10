import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer{
    private boolean started = false;
    private ServerSocket ss = null;

    static List<Client> clientsList = new ArrayList<Client>();

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
                    Client c = new Client(s);
                    new Thread(c).start();
                    clientsList.add(c);
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
        DataOutputStream dos = null;
        private boolean bConnected = false;

        public Client(Socket s) {
            this.s = s;
            try {
                dis = new DataInputStream(this.s.getInputStream());
                dos = new DataOutputStream(this.s.getOutputStream());
                bConnected = true;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        public void sendMessage(String str){
            try {
                dos.writeUTF(str);
            }catch (IOException e) {
                ChatServer.clientsList.remove(this); //当一个客户端退出后，删除退出的客户端；
                System.out.println("对方结束通话");
            }
        }

        @Override
        public void run() {
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str);
                    for (int i = 0; i < ChatServer.clientsList.size(); i++) {
                        Client c = ChatServer.clientsList.get(i);
                        c.sendMessage(str);
                    }

                    /* for(Iterator<Client> it = ChatServer.clientsList.iterator(); it.hasNext();) {
                        Client c = it.next();
                        c.sendMessage(str);
                    }*/

                    if (str == "bye"){
                        bConnected = false;
                    }
                }
            }catch (SocketException e1){
                System.out.println("对方已经下线");}
            catch (EOFException e){
                System.out.println("对方已经下线");}
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
            finally {
                try {
                    if (dis != null) dis.close();
                    if (dos != null) dos.close();
                    if (s != null) s.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
