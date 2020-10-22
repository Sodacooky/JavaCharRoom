import java.net.ServerSocket;

public class ChatServer {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8888);
        boolean flag = true;

        // 运行服务器，若有新用户则连接
        while (flag) {
            ServerThread st = new ServerThread(server.accept());
            Thread t = new Thread(st);
            t.start();
        }
    }
}
