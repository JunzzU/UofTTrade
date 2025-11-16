package app;

import data_access.UserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.register.RegisterController;
import interface_adapter.register.RegisterPresenter;
import interface_adapter.register.RegisterViewModel;
import use_case.register.RegisterInputBoundary;
import use_case.register.RegisterInteractor;
import use_case.register.RegisterOutputBoundary;
import view.LoginView;
import view.RegisterView;
import view.ViewManager;

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

    public AppBuilder() throws IOException {
        contentPane.setLayout(cardLayout);
    }

    public AppBuilder addRegisterView() {

        registerViewModel = new RegisterViewModel();
        registerView = new RegisterView(registerViewModel);
        contentPane.add(registerView, registerView.getViewName());
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

    public JFrame build() {
        final JFrame application = new JFrame("UofTTrade");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setMinimumSize(new Dimension(1920, 1080));
        application.setResizable(false);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setBounds(100, 100, 450, 300);
        application.add(contentPane);

        viewManagerModel.setState(registerView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;

    }

}
