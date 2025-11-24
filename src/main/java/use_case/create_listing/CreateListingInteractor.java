package use_case.create_listing;

import data_access.CreateListingDAO;
import data_access.UserDataAccessObject;
import entity.Category;
import entity.Listing;
import entity.User;
import use_case.view_profile.ViewProfileUserDataAccessInterface;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class CreateListingInteractor implements CreateListingInputBoundary{
    private final CreateListingUserDataAccessInterface createListingDataAccessObject;
    private final CreateListingOutputBoundary createListingPresenter;
    private final ViewProfileUserDataAccessInterface userDataAccess;

    public CreateListingInteractor(CreateListingUserDataAccessInterface createListingDataAccess, CreateListingOutputBoundary createListingOutputBoundary, ViewProfileUserDataAccessInterface userDataAccess) {
        this.createListingDataAccessObject = createListingDataAccess;
        this.createListingPresenter = createListingOutputBoundary;
        this.userDataAccess = userDataAccess;
    }

    @Override
    public void execute(CreateListingInputData createListingInputData) throws IOException {
        final String name = createListingInputData.get_name();
        final String photo = createListingInputData.get_img_in_Base64();
        final List<Category> categories = createListingInputData.get_categories();

        if(createListingDataAccessObject.existsByName(createListingInputData.get_name())){
            createListingPresenter.prepareFailView("A listing with this name already exists");
        }
        else if(createListingInputData.get_name() == ""){
            createListingPresenter.prepareFailView("A listing with a null name");
        }
        else if(createListingInputData.get_img_in_Base64() == ""){
            createListingPresenter.prepareFailView("The listing image is empty");
        }
        else {
            final CreateListingOutputData createListingOutputData;
            final Listing listing;

            User user = userDataAccess.getCurrentLoggedInUser();

            if (createListingInputData.get_categories().isEmpty()) {
                listing = new Listing(createListingInputData.get_name(), createListingInputData.get_img_in_Base64(), user);
                createListingOutputData = new CreateListingOutputData(
                        createListingInputData.get_name(),
                        createListingInputData.get_img_in_Base64(),
                        user
                );
            }
            else {
                listing = new Listing(
                        createListingInputData.get_name(),
                        createListingInputData.get_img_in_Base64(),
                        createListingInputData.get_categories(),
                        user
                );
                createListingOutputData = new CreateListingOutputData(
                        createListingInputData.get_name(),
                        createListingInputData.get_img_in_Base64(),
                        createListingInputData.get_categories(),
                        user
                );
            }

            createListingDataAccessObject.save(listing);
            createListingPresenter.prepareSuccessView(createListingOutputData);
        }
    }

    @Override
    public void switchToProfileView() {
        createListingPresenter.switchToProfileView();
    }
}
