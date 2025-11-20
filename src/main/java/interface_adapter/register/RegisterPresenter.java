package interface_adapter.register;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import use_case.register.RegisterOutputBoundary;
import use_case.register.RegisterOutputData;

public class RegisterPresenter implements RegisterOutputBoundary {

    private final RegisterViewModel registerViewModel;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public RegisterPresenter(ViewManagerModel viewManagerModel,
                           RegisterViewModel registerViewModel,
                           LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.registerViewModel = registerViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(RegisterOutputData response) {
        // On success, switch to the login view.
        final LoginState loginState = loginViewModel.getState();
        loginState.setUserIdentifier(response.getUsername());
        loginViewModel.firePropertyChanged();

        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailureView(String error) {
        final RegisterState registerState = registerViewModel.getState();
        registerState.setUsernameError(error);
        registerViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
