import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {

    TextField tftxt = new TextField();
    TextArea taContent = new TextArea();
    Socket s = null;

    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }

    public void launchFrame() {
        setLocation(400,300);
        this.setSize(300,300);
        add(tftxt,BorderLayout.SOUTH);
        add(taContent,BorderLayout.NORTH);
        pack();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        tftxt.addActionListener(new TfListener());
        setVisible(true);
        connect();


    }
    //监听内部类
    private class TfListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tftxt.getText().trim();
            taContent.setText(str);
            tftxt.setText(""); //让每次发送完信息后，输入框里面为空

            try {
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(str);
                dos.flush();
                dos.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    public void connect() {
        try {
            s = new Socket("127.0.0.1",8888);
            System.out.println("connected!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
