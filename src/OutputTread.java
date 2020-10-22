import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class OutputTread extends Thread {
    public void run() {
        try {
            String str;
            Socket client = new Socket("localhost", 8888);
            while (true) {
                BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
                PrintStream out = new PrintStream(client.getOutputStream());
                str = buf.readLine();
                out.flush();
                out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
