package use_case.view_listing;

public class ViewListingInputData {

    private final String listingName;
    private final String listingOwner;

    public ViewListingInputData(String listingName, String listingOwner) {
        this.listingName = listingName;
        this.listingOwner = listingOwner;
    }

    public String getListingName() {
        return listingName;
    }

    public String getListingOwner() {
        return listingOwner;
    }

}
