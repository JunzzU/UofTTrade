package use_case.create_listing;

import entity.Category;
import entity.Listing;

import java.awt.image.BufferedImage;
import java.util.List;

public class CreateListingInteractor implements CreateListingInputBoundary{
    private final CreateListingUserDataAccessInterface createListingDataAccessObject;
    private final CreateListingOutputBoundary createListingPresenter;

    public CreateListingInteractor(CreateListingUserDataAccessInterface createListingDataAccess, CreateListingOutputBoundary createListingOutputBoundary) {
        this.createListingDataAccessObject = createListingDataAccess;
        this.createListingPresenter = createListingOutputBoundary;
    }

    @Override
    public void execute(CreateListingInputData createListingInputData) {
        final String name = createListingInputData.get_name();
        final BufferedImage photo = createListingInputData.get_img();
        final List<Category> categories = createListingInputData.get_categories();

        if(!createListingDataAccessObject.existsByName(createListingInputData.get_name())){
            createListingPresenter.prepareFailView("A listing with this name already exists");
        }
        else if(createListingInputData.get_name() == null){
            createListingPresenter.prepareFailView("A listing with a null name");
        }
        else if(createListingInputData.get_img() == null){
            createListingPresenter.prepareFailView("The listing image is empty");
        }
        else {
            final CreateListingOutputData createListingOutputData;
            final Listing listing;

            if (createListingInputData.get_categories().isEmpty()) {
                listing = new Listing(createListingInputData.get_name(), createListingInputData.get_img());
                createListingOutputData = new CreateListingOutputData(createListingInputData.get_name(), createListingInputData.get_img());
            }
            else {
                listing = new Listing(createListingInputData.get_name(), createListingInputData.get_img(), createListingInputData.get_categories());
                createListingOutputData = new CreateListingOutputData(createListingInputData.get_name(), createListingInputData.get_img(), createListingInputData.get_categories());
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
