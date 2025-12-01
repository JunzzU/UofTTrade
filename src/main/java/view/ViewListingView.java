package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import interface_adapter.ViewManagerModel;
import interface_adapter.messaging.MessagingController;
import interface_adapter.messaging.MessagingViewModel;
import interface_adapter.view_listing.ViewListingController;
import interface_adapter.view_listing.ViewListingState;
import interface_adapter.view_listing.ViewListingViewModel;

public class ViewListingView extends JPanel implements PropertyChangeListener {

    private static final int LISTING_INFO_FONT_SIZE = 24;
    private static final int LISTING_TITLE_FONT_SIZE = 60;
    private static final int LISTING_BUTTON_FONT_SIZE = 16;
    private static final String LISTING_FONT = "Rubik";
    private static final int BORDER_SIZE = 5;
    private static final int INDENT_GAP = 60;
    private final JPanel listingNamePanel;
    private final JPanel listingInfoPanel;
    private final JPanel listingButtonsPanel;
    private final JLabel listingTitleLabel;
    private final JLabel listingCategoriesLabel;
    private final JLabel listingDescriptionLabel;
    private final JLabel listingOwnerLabel;
    private final JButton messageSellerButton;
    private final JButton toPreviousScreenButton;
    private final String viewName = "view listing";
    private String title;
    private String description;
    private String owner;
    private List<String> categories;
    private final ViewListingViewModel viewListingViewModel;
    private ViewListingController viewListingController;
    private MessagingController messagingController;
    private MessagingViewModel messagingViewModel;
    private ViewManagerModel viewManagerModel;

    public ViewListingView(ViewListingViewModel viewListingViewModel) {
        this.viewListingViewModel = viewListingViewModel;
        this.viewListingViewModel.addPropertyChangeListener(this);
        this.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        this.setLayout(new BorderLayout(INDENT_GAP, 0));

        listingNamePanel = new JPanel();
        this.add(listingNamePanel, BorderLayout.NORTH);

        listingTitleLabel = new JLabel("");
        listingTitleLabel.setFont(new Font(LISTING_FONT, Font.BOLD, LISTING_TITLE_FONT_SIZE));
        listingNamePanel.add(listingTitleLabel);

        listingInfoPanel = new JPanel();
        this.add(listingInfoPanel, BorderLayout.WEST);
        listingInfoPanel.setLayout(new GridLayout(0, 1, 0, 0));

        listingCategoriesLabel = new JLabel();
        listingCategoriesLabel.setFont(new Font(LISTING_FONT, Font.PLAIN, LISTING_INFO_FONT_SIZE));
        listingInfoPanel.add(listingCategoriesLabel);

        listingDescriptionLabel = new JLabel();
        listingDescriptionLabel.setFont(new Font(LISTING_FONT, Font.PLAIN, LISTING_INFO_FONT_SIZE));

        final JPanel descriptionWrapper = new JPanel();
        descriptionWrapper.add(listingDescriptionLabel);
        listingInfoPanel.add(descriptionWrapper);

        listingOwnerLabel = new JLabel();
        listingOwnerLabel.setFont(new Font(LISTING_FONT, Font.PLAIN, LISTING_INFO_FONT_SIZE));
        listingInfoPanel.add(listingOwnerLabel);

        listingButtonsPanel = new JPanel();
        this.add(listingButtonsPanel, BorderLayout.CENTER);
        listingButtonsPanel.setLayout(new GridLayout(0, 1, 0, 0));

        messageSellerButton = new JButton("Message Seller");
        messageSellerButton.setFont(new Font(LISTING_FONT, Font.PLAIN, LISTING_BUTTON_FONT_SIZE));
        listingButtonsPanel.add(messageSellerButton);

        toPreviousScreenButton = new JButton("Back");
        toPreviousScreenButton.setFont(new Font(LISTING_FONT, Font.PLAIN, LISTING_BUTTON_FONT_SIZE));
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

    /**
     * Changes the information on the detailed listing view depending on the state given.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
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
            description = state.getListingDescription();
            listingDescriptionLabel.setText("<html><div style='width: 500px;'>Description: " + description
                    + "</div></html>");

            messageSellerButton.addActionListener(event -> {
                if (messagingController == null || messagingViewModel == null || viewManagerModel == null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Messaging is not available yet",
                            "error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                final String sellerIdentifier = owner;
                final String sellerEmail= state.getListingOwnerEmail();
                messagingController.createGmailComposeLink(sellerIdentifier, sellerEmail);

                viewManagerModel.setState(MessagingViewModel.VIEW_NAME);
                viewManagerModel.firePropertyChanged();
            });

        }

    }

    public String getViewName() {
        return viewName;
    }

    public void setMessagingDependencies(MessagingController messagingController,
                                         MessagingViewModel messagingViewModel,
                                         ViewManagerModel viewManagerModel) {
        this.messagingController = messagingController;
        this.messagingViewModel = messagingViewModel;
        this.viewManagerModel = viewManagerModel;
    }

}
