package app;

import data_access.UserDataAccessObject;
import data_access.CreateListingDAO;
import entity.Listing;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_listing.CreateListingController;
import interface_adapter.create_listing.CreateListingPresenter;
import interface_adapter.create_listing.CreateListingViewModel;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.register.RegisterController;
import interface_adapter.register.RegisterPresenter;
import interface_adapter.register.RegisterViewModel;
import interface_adapter.update_listing.UpdateListingController;
import interface_adapter.update_listing.UpdateListingPresenter;
import use_case.create_listing.CreateListingInputBoundary;
import use_case.create_listing.CreateListingInteractor;
import use_case.create_listing.CreateListingOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.register.RegisterInputBoundary;
import use_case.register.RegisterInteractor;
import use_case.register.RegisterOutputBoundary;
import use_case.update_listing.UpdateListingInputBoundary;
import use_case.update_listing.UpdateListingInteractor;
import use_case.update_listing.UpdateListingOutputBoundary;
import use_case.update_listing.UpdateListingUserDataAccessInterface;
import view.*;
import interface_adapter.view_profile.ViewProfileViewModel;
import interface_adapter.view_profile.ViewProfileController;
import interface_adapter.view_profile.ViewProfilePresenter;
import use_case.view_profile.ViewProfileInputBoundary;
import use_case.view_profile.ViewProfileOutputBoundary;
import use_case.view_profile.ViewProfileInteractor;



import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;

public class AppBuilder {

    private final JPanel contentPane = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(contentPane, cardLayout, viewManagerModel);

    final UserDataAccessObject userDataAccessObject = new UserDataAccessObject();
    final CreateListingDAO createListingDAO = new CreateListingDAO();


    private RegisterView registerView;
    private RegisterViewModel registerViewModel;
    private LoginViewModel loginViewModel;
    private LoginView loginView;
    private ProfileView profileView;
    private ViewProfileViewModel viewProfileViewModel = new ViewProfileViewModel();


    private HomepageViewModel homepageViewModel;
    private HomepageView homepageView;

    private CreateListingView createListingView;
    private CreateListingViewModel createListingViewModel;
    private UpdateListingController updateListingController;


    public AppBuilder() throws IOException {
        contentPane.setLayout(cardLayout);
    }

    public AppBuilder addUpdateListingController(UpdateListingController controller) {
        this.updateListingController = controller;
        return this;
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

    public AppBuilder addProfileView(ViewProfileController controller) {

        Runnable gotoCreateListing = () -> {
            viewManagerModel.setState("create listing");
            viewManagerModel.firePropertyChanged();
        };

        Runnable gotoHome = () -> {
            viewManagerModel.setState(homepageView.getViewName());
            viewManagerModel.firePropertyChanged();
        };

        Consumer<String> deleteHandler = rawListingName -> {
            // Sanitize the input name: remove brackets and trim whitespace
            String listingName = rawListingName.replaceAll("\\[|\\]", "").trim();

            User currentUser = viewProfileViewModel.getState().getUser();


            if (currentUser == null) {
                return;
            }

            currentUser.get_listings().forEach(l -> System.out.println(">" + l.get_name() + "<"));

            // Find the listing by name
            Listing listingToDelete = currentUser.get_listings().stream()
                    .filter(l -> l.get_name().equalsIgnoreCase(listingName))
                    .findFirst()
                    .orElse(null);

            if (listingToDelete != null) {
                System.out.println("✅ Deleting listing: " + listingToDelete.get_name());
                updateListingController.execute(true, currentUser, listingToDelete);
                profileView.loadProfile();
            } else {

                // No matching listing found
            }
        };

        // Initialize the ProfileView before adding it
        profileView = new ProfileView(
                viewProfileViewModel,
                controller,
                gotoCreateListing,
                gotoHome,
                deleteHandler
        );

        // Add listener from homepageView
        homepageView.addViewProfileListener(e -> {
            viewManagerModel.setState(viewProfileViewModel.getViewName());
            viewManagerModel.firePropertyChanged();
            profileView.loadProfile();
        });

        // Add the profileView to the content pane
        contentPane.add(profileView, viewProfileViewModel.getViewName());

        // Add a property change listener to update profileView when its view is active
        viewManagerModel.addPropertyChangeListener(evt -> {
            if (evt.getNewValue().equals(viewProfileViewModel.getViewName())) {
                profileView.loadProfile();
            }
        });

        return this;
    }

    public AppBuilder addViewProfileUseCase() {

        ViewProfileOutputBoundary presenter =
                new ViewProfilePresenter(viewProfileViewModel);

        ViewProfileInputBoundary interactor =
                new ViewProfileInteractor(userDataAccessObject, presenter);

        // Pass homepageViewModel into the controller
        ViewProfileController controller =
                new ViewProfileController(interactor);

        return addProfileView(controller);
    }


    public AppBuilder addCreateListingView() {
        createListingViewModel = new CreateListingViewModel();
        createListingView = new CreateListingView(createListingViewModel);
        contentPane.add(createListingView, createListingView.getViewName());

        return this;
    }

    public AppBuilder addCreateListingUseCase(){
        final CreateListingOutputBoundary createListingOutputBoundary = new CreateListingPresenter(
                createListingViewModel,
                viewProfileViewModel,
                viewManagerModel
        );

        final CreateListingInputBoundary createListingInteractor= new CreateListingInteractor(
                createListingDAO,
                createListingOutputBoundary,
                userDataAccessObject
        );

        CreateListingController controller = new CreateListingController(createListingInteractor);
        createListingView.setCreateListingController(controller);
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

