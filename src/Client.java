public class Client {
    public static void main(String[] args) {
        new Thread(new OutputTread()).start();
        new Thread(new PrintMessage()).start();
    }
}
