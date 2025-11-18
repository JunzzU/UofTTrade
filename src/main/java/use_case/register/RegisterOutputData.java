package use_case.register;

public class RegisterOutputData {

    private final String username;
    private final String email;

    public RegisterOutputData(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() { return username; }

    public String getEmail() { return email; }
}
