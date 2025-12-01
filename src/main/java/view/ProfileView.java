package view;

import entity.Listing;
import entity.User;
import interface_adapter.view_profile.ViewProfileController;
import interface_adapter.view_profile.ViewProfileState;
import interface_adapter.view_profile.ViewProfileViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.function.Consumer;

/**
 * The ProfileView is responsible for rendering the user's profile screen.
 *
 * Displays:
 *  - The username title
 *  - A scrollable list of listings (for the current user), each with:
 *      * Listing name
 *      * Listing description
 *      * Delete button
 *  - A "Create Listing" button
 *  - A "Home" button
 *  - An error message when profile loading fails
 */
public class ProfileView extends JPanel implements PropertyChangeListener {

    private final ViewProfileViewModel viewModel;
    private final ViewProfileController profileController;

    /** Callback for Create Listing use case (from outside). */
    private final Runnable onCreateListing;

    /** Callback for Home button navigation. */
    private final Runnable onHome;

    /** Callback for Delete Listing use case. */
    private final Consumer<String> onDeleteListing;

    // UI Components
    private final JLabel titleLabel = new JLabel();
    private final JLabel noListingsLabel = new JLabel();
    private final JLabel errorLabel = new JLabel();
    private final JPanel listingsPanel = new JPanel();

    /**
     * Constructs a ProfileView screen.
     *
     * @param viewModel       the ViewModel holding the profile state
     * @param controller      the View Profile controller to trigger the use case
     * @param onCreateListing callback executed when Create Listing is pressed
     * @param onHome          callback executed when Home is pressed
     * @param onDeleteListing callback executed when Delete is pressed for a specific listing
     */
    public ProfileView(ViewProfileViewModel viewModel,
                       ViewProfileController controller,
                       Runnable onCreateListing,
                       Runnable onHome,
                       Consumer<String> onDeleteListing) {

        this.viewModel = viewModel;
        this.profileController = controller;
        this.onCreateListing = onCreateListing;
        this.onHome = onHome;
        this.onDeleteListing = onDeleteListing;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // ----- Title area -----
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        noListingsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        noListingsLabel.setForeground(Color.GRAY);
        noListingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(noListingsLabel);

        add(titlePanel, BorderLayout.NORTH);

        // ----- Error label -----
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(errorLabel, BorderLayout.SOUTH);

        // ----- Listings panel -----
        listingsPanel.setLayout(new BoxLayout(listingsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listingsPanel);
        add(scrollPane, BorderLayout.CENTER);

        // ----- Bottom buttons -----
        JPanel buttonPanel = new JPanel();

        JButton createBtn = new JButton("Create Listing");
        JButton homeBtn = new JButton("Home");

        createBtn.addActionListener(e -> onCreateListing.run());
        homeBtn.addActionListener(e -> onHome.run());

        buttonPanel.add(createBtn);
        buttonPanel.add(homeBtn);

        add(buttonPanel, BorderLayout.PAGE_END);
    }

    /**
     * Called whenever the ViewModel updates its state, typically after
     * the presenter calls firePropertyChanged().
     *
     * @param evt the property change event containing the updated state
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"state".equals(evt.getPropertyName())) return;

        ViewProfileState state = (ViewProfileState) evt.getNewValue();

        // ---- Title & error text ----
        titleLabel.setText(state.getTitleText());

        String err = state.getErrorMessage();
        if (err == null || err.isEmpty()) {
            errorLabel.setText("");
        } else {
            errorLabel.setText(err);
        }

        // ---- Clear old rows ----
        listingsPanel.removeAll();

        // ---- Prefer full Listing entities from the User, so we can show descriptions ----
        User currentUser = state.getUser();
        List<Listing> listings = null;
        if (currentUser != null && currentUser.get_listings() != null) {
            listings = currentUser.get_listings();
        }

        if (listings != null && !listings.isEmpty()) {
            // We have full Listing objects → show name + description
            noListingsLabel.setText("");

            for (Listing listing : listings) {
                String name = listing.get_name();
                String description = listing.get_description();
                JPanel row = createListingRow(name, description);
                listingsPanel.add(row);
                listingsPanel.add(Box.createVerticalStrut(6));
            }
        } else {
            // Fallback: use listingNames from state, in case listings are not populated
            List<String> names = state.getListingNames();
            List<String> descriptions = state.getListingDescriptions();
            if (names == null) names = java.util.Collections.emptyList();
            if (descriptions == null) descriptions = java.util.Collections.emptyList();

            if (names.isEmpty()) {
                noListingsLabel.setText("No listings yet...");
            } else {
                noListingsLabel.setText("");
            }

            int size = names.size();
            for (int i = 0; i < size; i++) {
                String name = names.get(i);
                String desc = (i < descriptions.size()) ? descriptions.get(i) : "";
                JPanel row = createListingRow(name, desc);  // your helper
                listingsPanel.add(row);
                listingsPanel.add(Box.createVerticalStrut(6));
            }
        }

        listingsPanel.revalidate();
        listingsPanel.repaint();
    }

    /**
     * Creates a row panel for a single listing, showing its name,
     * description, and delete button.
     */
    private JPanel createListingRow(String name, String description) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // ---- Text area (name + description) ----
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        // Name
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Description (handle null/empty)
        if (description == null || description.isEmpty()) {
            description = "(No description)";
        }
        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        textPanel.add(nameLabel);
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(descLabel);

        row.add(textPanel, BorderLayout.CENTER);

        // ---- Delete button ----
        JButton deleteBtn = new JButton("Delete");
        // keep same contract: pass name back to the delete handler
        deleteBtn.addActionListener(e -> onDeleteListing.accept(name));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(deleteBtn);
        buttonPanel.add(Box.createVerticalGlue());

        row.add(buttonPanel, BorderLayout.EAST);

        return row;
    }

    /**
     * Called by another view when the user clicks their Profile button.
     * It triggers the View Profile use case.
     */
    public void loadProfile() {
        profileController.onProfileButtonClicked();
    }
}
