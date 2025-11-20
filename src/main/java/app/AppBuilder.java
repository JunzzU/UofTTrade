package app;

import data_access.UserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.register.RegisterController;
import interface_adapter.register.RegisterPresenter;
import interface_adapter.register.RegisterViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.register.RegisterInputBoundary;
import use_case.register.RegisterInteractor;
import use_case.register.RegisterOutputBoundary;
import view.HomepageView;
import view.LoginView;
import view.RegisterView;
import view.ViewManager;
import view.ProfileView;
import interface_adapter.view_profile.ViewProfileViewModel;
import interface_adapter.view_profile.ViewProfileController;
import interface_adapter.view_profile.ViewProfilePresenter;
import use_case.view_profile.ViewProfileInputBoundary;
import use_case.view_profile.ViewProfileOutputBoundary;
import use_case.view_profile.ViewProfileInteractor;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AppBuilder {

    private final JPanel contentPane = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(contentPane, cardLayout, viewManagerModel);

    final UserDataAccessObject userDataAccessObject = new UserDataAccessObject();

    private RegisterView registerView;
    private RegisterViewModel registerViewModel;
    private LoginViewModel loginViewModel;
    private LoginView loginView;
    private ProfileView profileView;
    private ViewProfileViewModel viewProfileViewModel;

    private HomepageViewModel homepageViewModel;
    private HomepageView homepageView;

    public AppBuilder() throws IOException {
        contentPane.setLayout(cardLayout);
    }

    public AppBuilder addRegisterView() {

        registerViewModel = new RegisterViewModel();
        registerView = new RegisterView(registerViewModel);
        contentPane.add(registerView, registerView.getViewName());
        return this;

    }

    public AppBuilder addLoginView() {

        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        contentPane.add(loginView, loginView.getViewName());
        return this;

    }

    public AppBuilder addHomepageView() {

        homepageViewModel = new HomepageViewModel();
        homepageView = new HomepageView(homepageViewModel);
        contentPane.add(homepageView, homepageView.getViewName());
        return this;

    }

    public AppBuilder addRegisterUseCase() {
        final RegisterOutputBoundary RegisterOutputBoundary = new RegisterPresenter(viewManagerModel,
                registerViewModel, loginViewModel);
        final RegisterInputBoundary userRegisterInteractor = new RegisterInteractor(
                userDataAccessObject, RegisterOutputBoundary);

        RegisterController controller = new RegisterController(userRegisterInteractor);
        registerView.setRegisterController(controller);
        return this;
    }

    public AppBuilder addViewProfileUseCase() {

        viewProfileViewModel = new ViewProfileViewModel();

        ViewProfileOutputBoundary presenter =
                new ViewProfilePresenter(viewProfileViewModel);

        ViewProfileInputBoundary interactor =
                new ViewProfileInteractor(userDataAccessObject, presenter);

        ViewProfileController controller =
                new ViewProfileController(interactor);

        return addProfileView(controller);
    }


    public AppBuilder addProfileView(ViewProfileController controller) {

        profileView = new ProfileView(
                viewProfileViewModel,
                controller,
                () -> System.out.println("Create listing"),
                () -> System.out.println("Home"),
                listingName -> System.out.println("Delete " + listingName)
        );

        contentPane.add(profileView, viewProfileViewModel.getViewName());
        return this;
    }

    public AppBuilder addLoginUseCase() {

        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                homepageViewModel, loginViewModel, registerViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;

    }

    public JFrame build() {
        final JFrame application = new JFrame("UofTTrade");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setBounds(100, 100, 450, 300);
        application.add(contentPane);

        viewManagerModel.setState(registerView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;

    }


}

