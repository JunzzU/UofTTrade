package interface_adapter.view_profile;

import interface_adapter.ViewModel;

/**
 * ViewModel for the View Profile screen.
 */
public class ViewProfileViewModel extends ViewModel<ViewProfileState> {

    public ViewProfileViewModel() {
        super("view profile");
        setState(new ViewProfileState());
    }

    /** @return the title text for the view */
    public String getTitleText() {
        return getState().getTitleText();
    }
}


