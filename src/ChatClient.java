import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatClient extends Frame {

    TextField tftxt = new TextField();
    TextArea taContent = new TextArea();

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
    }
    //监听内部类
    private class TfListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String s = tftxt.getText().trim();
            taContent.setText(s);
            tftxt.setText(""); //让每次发送完信息后，输入框里面为空
        }
    }
}
