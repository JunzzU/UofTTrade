package interface_adapter.view_listing;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import use_case.view_listing.ViewListingOutputBoundary;
import use_case.view_listing.ViewListingOutputData;

public class ViewListingPresenter implements ViewListingOutputBoundary {

    private final ViewModel<?> previousViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ViewListingViewModel viewListingViewModel;

    public ViewListingPresenter(ViewModel<?> previousViewModel, ViewManagerModel viewManagerModel,
                                ViewListingViewModel viewListingViewModel) {

        this.previousViewModel = previousViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewListingViewModel = viewListingViewModel;

    }

    @Override
    public void switchToListingView(ViewListingOutputData viewListingOutputData) {

        final ViewListingState viewListingState = viewListingViewModel.getState();
        viewListingState.setListingName(viewListingOutputData.getListingName());
        viewListingState.setListingOwner(viewListingOutputData.getListingOwner());
        viewListingState.setListingCategories(viewListingOutputData.getListingCategories());
        this.previousViewModel.firePropertyChanged();

        this.viewManagerModel.setState(viewListingViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();

    }

    @Override
    public void switchToPreviousView() {

        viewManagerModel.setState(previousViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

    }
}
