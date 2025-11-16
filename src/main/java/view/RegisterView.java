package view;

import interface_adapter.register.RegisterController;
import interface_adapter.register.RegisterState;
import interface_adapter.register.RegisterViewModel;

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

public class RegisterView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "register";
    private RegisterController registerController = null;
    private RegisterViewModel registerViewModel;
    private final JPanel backgroundPanel;
    private final JLabel usernameLabel;
    private final JLabel emailLabel;
    private final JLabel passwordLabel;
    private final JLabel confirmPasswordLabel;
    private final JLabel titleLabel;
    private final JLabel imageLabel;
    private final JTextField emailField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JButton registerButton;
    private final JButton loginButton;


    public RegisterView(RegisterViewModel registerViewModel) {

        this.registerViewModel = registerViewModel;

        setBackground(new Color(255, 255, 255));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        registerButton = new JButton(RegisterViewModel.REGISTER_BUTTON_LABEL);
        registerButton.setBackground(new Color(0, 128, 0));
        registerButton.setBounds(585, 758, 344, 139);
        registerButton.setFont(new Font("Rubik", Font.PLAIN, 36));
        add(registerButton);

        titleLabel = new JLabel(RegisterViewModel.TITLE_LABEL);
        titleLabel.setBounds(505, 10, 1059, 161);
        titleLabel.setFont(new Font("Rubik", Font.BOLD, 96));
        add(titleLabel);

        backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(0, 0, 128));
        backgroundPanel.setBounds(0, 0, 488, 941);
        add(backgroundPanel);
        backgroundPanel.setLayout(null);

        imageLabel = new JLabel("");
        imageLabel.setBounds(-90, -120, 695, 641);
        backgroundPanel.add(imageLabel);
        imageLabel.setIcon(new ImageIcon("C:\\Users\\humbe\\IdeaProjects\\API Starter\\src\\resources\\images\\istockphoto-157334765-612x612.jpg"));

        usernameLabel = new JLabel(RegisterViewModel.USERNAME_LABEL);
        usernameLabel.setBounds(585, 192, 375, 42);
        usernameLabel.setFont(new Font("Rubik", Font.PLAIN, 48));
        add(usernameLabel);

        usernameField = new JTextField(15);
        usernameField.setBounds(585, 244, 750, 57);
        usernameField.setFont(new Font("Rubik", Font.PLAIN, 40));
        add(usernameField);
        usernameField.setColumns(10);

        emailLabel = new JLabel(RegisterViewModel.EMAIL_LABEL);
        emailLabel.setFont(new Font("Rubik", Font.PLAIN, 48));
        emailLabel.setBounds(585, 324, 375, 42);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("Rubik", Font.PLAIN, 48));
        emailField.setColumns(10);
        emailField.setBounds(585, 376, 750, 57);
        emailField.setFont(new Font("Rubik", Font.PLAIN, 40));
        add(emailField);

        passwordLabel = new JLabel(RegisterViewModel.PASSWORD_LABEL);
        passwordLabel.setFont(new Font("Rubik", Font.PLAIN, 48));
        passwordLabel.setBounds(585, 467, 375, 42);
        add(passwordLabel);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(585, 519, 750, 57);
        passwordField.setFont(new Font("Rubik", Font.PLAIN, 40));
        add(passwordField);

        confirmPasswordLabel = new JLabel(RegisterViewModel.REPEAT_PASSWORD_LABEL);
        confirmPasswordLabel.setFont(new Font("Rubik", Font.PLAIN, 48));
        confirmPasswordLabel.setBounds(585, 605, 750, 42);
        add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setBounds(585, 657, 750, 57);
        confirmPasswordField.setFont(new Font("Rubik", Font.PLAIN, 40));
        add(confirmPasswordField);

        loginButton = new JButton(RegisterViewModel.LOGIN_BUTTON_LABEL);
        loginButton.setBackground(new Color(128, 128, 255));
        loginButton.setBounds(970, 758, 365, 139);
        loginButton.setFont(new Font("Rubik", Font.PLAIN, 36));
        add(loginButton);

        addUsernameListener();
        addEmailListener();
        addPasswordListener();
        addRepeatPasswordListener();

        registerButton.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(registerButton)) {
                            final RegisterState currentState = registerViewModel.getState();

                            try {
                                registerController.execute(
                                        currentState.getUsername(),
                                        currentState.getEmail(),
                                        currentState.getPassword(),
                                        currentState.getRepeatPassword()
                                );

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        loginButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        registerController.switchToLoginView();
                    }
                }
        );

    }

    private void addUsernameListener() {
        usernameField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final RegisterState currentState = registerViewModel.getState();
                currentState.setUsername(usernameField.getText());
                registerViewModel.setState(currentState);
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

    private void addEmailListener() {
        emailField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final RegisterState currentState = registerViewModel.getState();
                currentState.setEmail(emailField.getText());
                registerViewModel.setState(currentState);
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

    private void addPasswordListener() {
        passwordField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final RegisterState currentState = registerViewModel.getState();
                currentState.setPassword(new String(passwordField.getPassword()));
                registerViewModel.setState(currentState);
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

    private void addRepeatPasswordListener() {
        confirmPasswordField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final RegisterState currentState = registerViewModel.getState();
                currentState.setRepeatPassword(new String(confirmPasswordField.getPassword()));
                registerViewModel.setState(currentState);
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

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final RegisterState state = (RegisterState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setRegisterController(RegisterController controller) {
        this.registerController = controller;
    }


}
