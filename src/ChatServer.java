import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8888);
        boolean flag = true;

        // 运行服务器，若有新用户则连接
        while (flag) {
            Socket socket=server.accept();
            ServerThread st = new ServerThread(socket);
            Thread t = new Thread(st);
            t.start();
        }
    }
}
