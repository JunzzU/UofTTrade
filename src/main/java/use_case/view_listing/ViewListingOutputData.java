package use_case.view_listing;

import entity.Category;

import java.awt.image.BufferedImage;
import java.util.List;

public class ViewListingOutputData {

    private final String listingName;
    private final String listingOwner;
    private final List<String> listingCategories;

    public ViewListingOutputData(String listingName, String listingOwner, List<String> listingCategories) {
        this.listingName = listingName;
        this.listingOwner = listingOwner;
        this.listingCategories = listingCategories;
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


}
