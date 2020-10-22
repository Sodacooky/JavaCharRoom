public class User {
    private String name;


    public static class Builder {
        private String name;

        public Builder() {
        }

        public Builder setName(String n) {
            name = n;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    private User(Builder builder) {
        name = builder.name;
    }

    public void getUser() {
        System.out.println(this.name);
    }
}
