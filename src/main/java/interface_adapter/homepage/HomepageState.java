package interface_adapter.homepage;

public class HomepageState {

    private String username = "";

    public HomepageState(HomepageState copy) {
        username = copy.username;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public HomepageState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
