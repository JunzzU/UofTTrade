package interface_adapter.view_listing;

import java.util.List;

public class ViewListingState {

    private String listingName = "";
    private List<String> listingCategories;
    private String listingOwner = "";
    private String listingOwnerEmail = "";
    private String listingDescription = "";

    public String getListingName() {
        return listingName;
    }

    public List<String> getListingCategories() {
        return listingCategories;
    }

    public void setListingCategories(List<String> listingCategories) {
        this.listingCategories = listingCategories;
    }

    public String getListingOwner() {
        return listingOwner;
    }

    public void setListingOwner(String listingOwner) {
        this.listingOwner = listingOwner;
    }

    public void setListingName(String listingName) {
        this.listingName = listingName;
    }

    public void setListingDescription(String listingDescription) {
        this.listingDescription = listingDescription;
    }

    public String getListingDescription() {
        return listingDescription;
    }

    public void setListingOwnerEmail(String listingOwnerEmail) {
        this.listingOwnerEmail = listingOwnerEmail;
    }

    public String getListingOwnerEmail() {
        return listingOwnerEmail;
    }

}
