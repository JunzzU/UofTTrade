package interface_adapter.view_listing;

import entity.Category;

import java.awt.image.BufferedImage;
import java.util.List;

public class ViewListingState {

    private String listingName;
    private List<String> listingCategories;
    private String listingOwner;
    private BufferedImage listingImage;

    public String getListingName() {
        return listingName;
    }

    public void setListingImage(BufferedImage listingImage) {
        this.listingImage = listingImage;
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

    public BufferedImage getListingImage() {
        return listingImage;
    }

}
