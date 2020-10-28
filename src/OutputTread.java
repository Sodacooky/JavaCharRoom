import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;

// 输入消息并发送给服务器
public class OutputTread extends Thread {
    private User user;
    private Date date = new Date();

    public OutputTread(User user) {
        this.user = user;
    }

    public void run() {
        try {
            String str;
            Socket client = new Socket("localhost", 8888);
            while (true) {
                BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
                PrintStream out = new PrintStream(client.getOutputStream());
                str = buf.readLine();
                out.flush();
                out.println(date.toString() + "     " + user.getUser() + ": " + str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
