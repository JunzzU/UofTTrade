package view;

import interface_adapter.create_listing.CreateListingViewModel;
import interface_adapter.create_listing.CreateListingState;
import interface_adapter.create_listing.CreateListingController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The View for when the user is creating a new listing
 */
public class CreateListingView implements ActionListener, PropertyChangeListener {
    private final String viewName = "create listing";
    private final CreateListingViewModel createListingViewModel;

    private final JPanel createListingPanel = new JPanel();
    private final JTextField listingNameInputField = new JTextField(15);

    public CreateListingView(CreateListingViewModel createListingViewModel) {
        this.createListingViewModel = createListingViewModel;

//        createListingPanel.add(new LabelTextPanel(new JLabel("Name")));
        createListingPanel.add(listingNameInputField);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
