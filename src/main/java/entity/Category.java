package entity;
import java.util.ArrayList;
import java.util.List;

public class Category {
    private final String name;
    private List<Listing> listings;

    public Category(String name) {
        this.name = name;
        listings = new ArrayList<>();
    }

    public String getName() { return name; }

    public List<Listing> getListings() { return listings; }
}
