public class TestUser {
    public static void main(String[] args) {
        User user = new User.Builder().setName("hhl").
            setAccount(1124869905).setPassword(123456).build();
        user.getUser();
    }
}
