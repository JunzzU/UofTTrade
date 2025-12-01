package use_case.view_listing;

import java.util.List;

public class ViewListingOutputData {

    private final String listingName;
    private final String listingOwner;
    private final List<String> listingCategories;
    private final String listingDescription;

    public ViewListingOutputData(String listingName, String listingOwner, List<String> listingCategories,
                                 String listingDescription) {
        this.listingName = listingName;
        this.listingOwner = listingOwner;
        this.listingCategories = listingCategories;
        this.listingDescription = listingDescription;
    }

    public String getListingName() {
        return listingName;
    }

    public List<String> getListingCategories() {
        return listingCategories;
    }

    public String getListingOwner() {
        return listingOwner;
    }

    public String getListingDescription() {
        return listingDescription;
    }

}
