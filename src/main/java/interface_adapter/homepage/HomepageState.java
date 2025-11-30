package interface_adapter.homepage;

import entity.Listing;

import java.util.ArrayList;
import java.util.List;

public class HomepageState {

    private String username = "";
    private List<Listing> listings = new ArrayList<>();

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
