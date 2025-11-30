package use_case.messaging;

public class MessagingInputData {
    private final String username;

    public MessagingInputData(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
