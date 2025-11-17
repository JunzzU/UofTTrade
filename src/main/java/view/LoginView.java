package view;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import use_case.login.LoginInputData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;
    private LoginController loginController = null;
    private final JLabel lblNewLabel;
    private final JLabel titleLabel;
    private final JLabel usernameLabel;
    private final JTextField usernameField;
    private final JLabel passwordLabel;
    private final JPasswordField passwordField;
    private final JButton btnLogin;
    private final JButton btnRegister;
    private final JLabel usernameErrorField = new JLabel();


    public LoginView(LoginViewModel loginViewModel) {

        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        setBackground(new Color(255, 255, 255));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        usernameErrorField.setFont(new Font("Rubik", Font.PLAIN, 24));
        usernameErrorField.setBounds(583, 869, 543, 62);
        add(usernameErrorField);

        lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\humbe\\IdeaProjects\\API Starter\\src\\resources\\images\\university-of-toronto-01.jpg"));
        lblNewLabel.setBounds(0, -286, 493, 1442);
        add(lblNewLabel);

        titleLabel = new JLabel("UofTTrade Login");
        titleLabel.setFont(new Font("Rubik", Font.BOLD, 96));
        titleLabel.setBounds(522, 10, 965, 133);
        add(titleLabel);

        usernameLabel = new JLabel("Username/Email:");
        usernameLabel.setFont(new Font("Rubik", Font.PLAIN, 48));
        usernameLabel.setBounds(585, 265, 602, 94);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Rubik", Font.PLAIN, 40));
        usernameField.setBounds(585, 358, 541, 68);
        add(usernameField);
        usernameField.setColumns(10);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Rubik", Font.PLAIN, 48));
        passwordLabel.setBounds(585, 469, 602, 94);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Rubik", Font.PLAIN, 40));
        passwordField.setColumns(10);
        passwordField.setBounds(585, 567, 541, 68);
        add(passwordField);

        btnLogin = new JButton("Log In");
        btnLogin.setBackground(new Color(128, 128, 255));
        btnLogin.setFont(new Font("Rubik", Font.PLAIN, 36));
        btnLogin.setBounds(585, 709, 258, 124);
        add(btnLogin);

        btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(0, 128, 0));
        btnRegister.setFont(new Font("Rubik", Font.PLAIN, 36));
        btnRegister.setBounds(868, 709, 258, 124);
        add(btnRegister);

        btnLogin.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(btnLogin)) {
                            final LoginState currentState = loginViewModel.getState();

                            try {
                                loginController.executeLogin(
                                        currentState.getUserIdentifier(),
                                        currentState.getPassword()
                                );
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
        );

        btnRegister.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(btnRegister)) {
                            final LoginState currentState = loginViewModel.getState();
                            loginController.switchToRegisterView();
                        }
                    }
                }
        );

        usernameField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUserIdentifier(usernameField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        passwordField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getLoginError());
    }

    private void setFields(LoginState state) {
        usernameField.setText(state.getUserIdentifier());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

}
