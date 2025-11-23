package view;

import interface_adapter.create_listing.CreateListingViewModel;
import interface_adapter.create_listing.CreateListingState;
import interface_adapter.create_listing.CreateListingController;
import interface_adapter.login.LoginState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.imageio.ImageIO;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The View for when the user is creating a new listing
 */
public class CreateListingView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "create listing";
    private final CreateListingViewModel createListingViewModel;

    private final JTextField listingNameInputField = new JTextField(50);

    private final JButton imgFileChooserButton = new JButton();
    private final JFileChooser imgFileChooser = new JFileChooser();
    private BufferedImage selectedImage = null;

    private final JComboBox<String> listingCategory1ComboBox = new JComboBox<>();

    private final JComboBox<String> listingCategory2ComboBox = new JComboBox<>();

    private final JButton publishListingButton;
    private final JButton cancelButton;

    private CreateListingController createListingController = null;

    private final JLabel nameErrorField = new JLabel();
    private final JLabel photoErrorField = new JLabel();


    public CreateListingView(CreateListingViewModel createListingViewModel) {
        this.createListingViewModel = createListingViewModel;
        createListingViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(CreateListingViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // name
        final LabelTextPanel listingNameInfo = new LabelTextPanel(
                new JLabel(CreateListingViewModel.NAME_LABEL), listingNameInputField
        );

        // img
        final JPanel listingImagePanel = new JPanel();
        listingImagePanel.setLayout(new BoxLayout(listingImagePanel, BoxLayout.X_AXIS));
        listingImagePanel.add(new JLabel(CreateListingViewModel.IMG_LABEL));
        listingImagePanel.add(imgFileChooserButton);

        // categories
        final JPanel listingCategoryTitlePanel = new JPanel();
        listingCategoryTitlePanel.setLayout(new BoxLayout(listingCategoryTitlePanel, BoxLayout.Y_AXIS));
        listingCategoryTitlePanel.add(new JLabel(CreateListingViewModel.CATEGORIES_LABEL));

        final JPanel listingCategoryDropdownPanel = new JPanel();
        listingCategoryDropdownPanel.setLayout(new BoxLayout(listingCategoryDropdownPanel, BoxLayout.Y_AXIS));
        listingCategoryDropdownPanel.add(listingCategory1ComboBox);
        listingCategoryDropdownPanel.add(listingCategory2ComboBox);

        final JPanel listingCategoryWrapperPanel = new JPanel();
        listingCategoryWrapperPanel.setLayout(new BoxLayout(listingCategoryWrapperPanel, BoxLayout.X_AXIS));
        listingCategoryWrapperPanel.add(listingCategoryTitlePanel);
        listingCategoryWrapperPanel.add(listingCategoryDropdownPanel);

        // buttons
        publishListingButton = new JButton(CreateListingViewModel.PUBLISH_LISTING_BUTTON_LABEL);
        cancelButton = new JButton(CreateListingViewModel.CANCEL_BUTTON_LABEL);
        final JPanel buttons = new JPanel();
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(publishListingButton);
        buttons.add(cancelButton);

        publishListingButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "Listing published successfully!");
                        createListingController.switchToProfileView();
                    }
                }
        );

        cancelButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        createListingController.switchToProfileView();
                    }
                }
        );

        // other
        listingNameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final CreateListingState currentState = createListingViewModel.getState();
                currentState.set_name(listingNameInputField.getText());
                createListingViewModel.setState(currentState);
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

        imgFileChooserButton.addActionListener(e -> {
            int result = imgFileChooser.showOpenDialog(CreateListingView.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = imgFileChooser.getSelectedFile();

                try {
                    BufferedImage image = ImageIO.read(file);
                    selectedImage = image; // Store locally if needed

                    // Update ViewModel
                    CreateListingState state = createListingViewModel.getState();
                    state.set_photo(image);
                    createListingViewModel.setState(state);

                } catch (IOException ex) {
                    ex.printStackTrace();

                    // If loading fails, update error
                    CreateListingState state = createListingViewModel.getState();
                    state.set_photo_error("Could not load image.");
                    createListingViewModel.setState(state);
                }
            }
        });

        // put panels together
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(listingNameInfo);
        this.add(listingImagePanel);
        this.add(listingCategoryWrapperPanel);
        this.add(buttons);
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final CreateListingState state = (CreateListingState) evt.getNewValue();
        setFields(state);
        nameErrorField.setText(state.get_name_error());
        photoErrorField.setText(state.get_photo_error());
    }

    private void setFields(CreateListingState state) {
        nameErrorField.setText(state.get_name_error());
        photoErrorField.setText(state.get_photo_error());
    }


    public String getViewName() { return viewName; }

    public void setCreateListingController(CreateListingController createListingController) {
        this.createListingController =createListingController;
    }
}
