import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

// 处理消息
public class ServerThread implements Runnable {
    private Socket client;
    private OutputStream ous;

    ServerThread(Socket client) {
        this.client = client;
    }

    // 各客户端写入得到的消息
    public void sendMsg(String msg) throws IOException {
        msg += "\r\n";
        ous.write(msg.getBytes());
        ous.flush();
    }

    public void run() {
        try {
            processSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 信息的接收和转发具体实现
    private void processSocket() throws IOException {
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            ous = client.getOutputStream();
            ChatTools.addClient(this);
            String str = buf.readLine();
            boolean temp = true;
            while (temp) {
                ChatTools.castMsg(str);
                str = buf.readLine();
            }
            client.close();
        } catch (Exception e) {
        }

    }
}


