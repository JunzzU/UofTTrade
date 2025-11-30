package use_case.view_listing;

import java.awt.image.BufferedImage;
import java.util.List;

public class ViewListingOutputData {

    private final String listingName;
    private final String listingOwner;
    private final List<String> listingCategories;
    private final BufferedImage listingImage;

    public ViewListingOutputData(String listingName, String listingOwner, List<String> listingCategories,
                                 BufferedImage listingImage) {
        this.listingName = listingName;
        this.listingOwner = listingOwner;
        this.listingCategories = listingCategories;
        this.listingImage = listingImage;
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

    public BufferedImage getListingImage() {
        return listingImage;
    }

}
