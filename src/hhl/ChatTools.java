import java.io.IOException;
import java.util.ArrayList;

public class ChatTools {
    private static ArrayList<ServerThread> stList = new ArrayList<>();

    // 把新用户加入到队列中
    public static void addClient(ServerThread st) throws IOException {
        stList.add(st);
        System.out.println("加入成功");
    }

    // 获取消息并转发到各个客户端
    public static void castMsg(String msg) throws IOException {
        for (int i = 0; i < stList.size(); i++) {
            ServerThread st = stList.get(i);
            st.sendMsg(msg);
        }
    }
}


