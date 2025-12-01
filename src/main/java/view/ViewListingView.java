package view;

import interface_adapter.login.LoginState;
import interface_adapter.view_listing.ViewListingController;
import interface_adapter.view_listing.ViewListingState;
import interface_adapter.view_listing.ViewListingViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ViewListingView extends JPanel implements PropertyChangeListener {
    private final JPanel listingNamePanel;
    private final JPanel listingInfoPanel;
    private final JPanel listingButtonsPanel;
    private final JLabel listingTitleLabel;
    private final JLabel listingCategoriesLabel;
    private final JLabel listingOwnerLabel;
    private final JButton messageSellerButton;
    private final JButton toPreviousScreenButton;
    private final String viewName = "logged in";
    private String title;
    private String owner;
    private List<String> categories;
    private final ViewListingViewModel viewListingViewModel;
    private ViewListingController viewListingController = null;

    public ViewListingView(ViewListingViewModel viewListingViewModel) {
        this.viewListingViewModel = viewListingViewModel;
        this.viewListingViewModel.addPropertyChangeListener(this);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(new BorderLayout(60, 0));

        listingNamePanel = new JPanel();
        this.add(listingNamePanel, BorderLayout.NORTH);

        listingTitleLabel = new JLabel("");
        listingTitleLabel.setFont(new Font("Rubik", Font.BOLD, 60));
        listingNamePanel.add(listingTitleLabel);

        listingInfoPanel = new JPanel();
        this.add(listingInfoPanel, BorderLayout.WEST);
        listingInfoPanel.setLayout(new GridLayout(0, 1, 0, 0));

        listingCategoriesLabel = new JLabel("Categories:");
        listingCategoriesLabel.setFont(new Font("Rubik", Font.PLAIN, 24));
        listingInfoPanel.add(listingCategoriesLabel);

        listingOwnerLabel = new JLabel("Owner:");
        listingOwnerLabel.setFont(new Font("Rubik", Font.PLAIN, 24));
        listingInfoPanel.add(listingOwnerLabel);

        listingButtonsPanel = new JPanel();
        this.add(listingButtonsPanel, BorderLayout.CENTER);
        listingButtonsPanel.setLayout(new GridLayout(0, 1, 0, 0));

        messageSellerButton = new JButton("Message Seller");
        messageSellerButton.setFont(new Font("Rubik", Font.PLAIN, 16));
        listingButtonsPanel.add(messageSellerButton);

        toPreviousScreenButton = new JButton("Back");
        toPreviousScreenButton.setFont(new Font("Rubik", Font.PLAIN, 16));
        listingButtonsPanel.add(toPreviousScreenButton);

        toPreviousScreenButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(toPreviousScreenButton)) {
                            viewListingController.switchToPreviousView();
                        }
                    }
                }
        );

        messageSellerButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(messageSellerButton)) {
                            // add the activation of messaging here.
                        }
                    }
                }
        );
    }

    public void setViewListingController(ViewListingController viewListingController) {
        this.viewListingController = viewListingController;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final ViewListingState state = (ViewListingState) evt.getNewValue();
            title = state.getListingName();
            listingTitleLabel.setText(title);
            owner = state.getListingOwner();
            listingOwnerLabel.setText("Owner: " + owner);
            categories = state.getListingCategories();
            final StringBuilder categoriesBuilder = new StringBuilder("Categories: ");
            for (int i = 0; i < categories.size() - 1; i++) {
                categoriesBuilder.append(categories.get(i)).append(", ");
            }
            categoriesBuilder.append(categories.getLast());
            listingCategoriesLabel.setText(categoriesBuilder.toString());

        }

    }

    public String getViewName() {
        return viewName;
    }

}
