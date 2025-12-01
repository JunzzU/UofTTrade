package interface_adapter.view_listing;

import interface_adapter.ViewModel;

public class ViewListingViewModel extends ViewModel<ViewListingState> {

    public ViewListingViewModel() {
        super("view listing");
        setState(new ViewListingState());
    }

}
