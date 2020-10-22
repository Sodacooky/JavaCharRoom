import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PrintMessage extends Thread {
    public void run() {
        Socket client = null;
        try {
            client = new Socket("localhost", 8888);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        while (true) {
            try {
                BufferedReader buf1 =
                    new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("\n\r" + buf1.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




