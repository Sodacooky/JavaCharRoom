import java.util.Scanner;

public class Client {
    public static User user;

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.print("请输入用户名： ");
        String name = scn.nextLine();
        user = new User(name);
        new Thread(new OutputTread(user)).start();
        new Thread(new PrintMessage()).start();
    }
}
