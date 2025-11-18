package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.register.RegisterViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final HomepageViewModel homepageViewModel;
    private final ViewManagerModel viewManagerModel;
    private final RegisterViewModel registerViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          HomepageViewModel homepageViewModel,
                          LoginViewModel loginViewModel,
                          RegisterViewModel registerViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homepageViewModel = homepageViewModel;
        this.loginViewModel = loginViewModel;
        this.registerViewModel = registerViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, update the loggedInViewModel's state
        final HomepageState homepageState = homepageViewModel.getState();
        homepageState.setUsername(response.getUsername());
        this.homepageViewModel.firePropertyChanged();

        // and clear everything from the LoginViewModel's state
        loginViewModel.setState(new LoginState());

        // switch to the logged in view
        this.viewManagerModel.setState(homepageViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void switchToRegisterView() {

        viewManagerModel.setState(registerViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
