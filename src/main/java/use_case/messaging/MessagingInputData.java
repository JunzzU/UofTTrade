package use_case.messaging;

public class MessagingInputData {
    private final String username;
    private final String email;

    public MessagingInputData(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() { return username;
    }

    public String getEmail() { return email;}

}
