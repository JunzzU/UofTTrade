package use_case.create_listing;

import entity.Category;

import java.awt.image.BufferedImage;
import java.util.List;

public class CreateListingInteractor implements CreateListingInputBoundary{
    private final CreateListingUserDataAccessInterface createListingDataAccess;
    private final CreateListingOutputBoundary createListingPresenter;

    public CreateListingInteractor(CreateListingUserDataAccessInterface createListingDataAccess, CreateListingOutputBoundary createListingPresenter) {
        this.createListingDataAccess = createListingDataAccess;
        this.createListingPresenter = createListingPresenter;
    }

    @Override
    public void execute(CreateListingInputData createListingInputData) {
        final String name = createListingInputData.get_name();
        final BufferedImage photo = createListingInputData.get_img();
        final List<Category> categories = createListingInputData.get_categories();

        if(!createListingDataAccess.nameFieldIsNull(name)){
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

            if (createListingInputData.get_categories().isEmpty()) {
                createListingOutputData = new CreateListingOutputData(createListingInputData.get_name(), createListingInputData.get_img());

            }
            else {
                createListingOutputData = new CreateListingOutputData(createListingInputData.get_name(), createListingInputData.get_img(), createListingInputData.get_categories());
            }

            createListingPresenter.prepareSuccessView(createListingOutputData);
        }
    }

    @Override
    public void switchToProfileView() {
        createListingPresenter.switchToProfileView();
    }
}
