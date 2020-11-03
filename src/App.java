import com.Client.ClientTesting;
import com.Server.ServerMain;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int input = scn.nextInt();

        if (input == 1) {
            (new ServerMain()).run();
        } else if (input == 2) {
            (new ClientTesting()).run();
        }

        input = scn.nextInt();
        scn.close();
    }
}
