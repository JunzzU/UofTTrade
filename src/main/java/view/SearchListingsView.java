package view;

import interface_adapter.search.SearchListingsController;
import interface_adapter.search.SearchListingsState;
import interface_adapter.search.SearchListingsViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class SearchListingsView extends JPanel implements PropertyChangeListener {
    public final String viewName = "search listings";
    private final SearchListingsViewModel viewModel;
    private SearchListingsController controller;

    // --- Search Inputs ---
    private final JTextField keywordField = new JTextField(15);
    private final JComboBox<String> categoryBox;
    private final JButton searchButton = new JButton("Search");
    private final JButton backButton = new JButton("Back to Home");

    // --- Results Area ---
    private final JPanel resultsPanel = new JPanel();
    private final JLabel messageLabel = new JLabel("");

    public SearchListingsView(SearchListingsViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // 1. TOP PANEL: Filters and Search Button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        categoryBox = new JComboBox<>();
        // Populate category dropdown
        if (viewModel.getCategories() != null) {
            for (String cat : viewModel.getCategories()) {
                categoryBox.addItem(cat);
            }
        }

        topPanel.add(new JLabel("Key:"));
        topPanel.add(keywordField);
        topPanel.add(new JLabel("Cat:"));
        topPanel.add(categoryBox);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        //Results Grid
        resultsPanel.setLayout(new GridLayout(0, 4, 10, 10));
        resultsPanel.setBackground(new Color(245, 245, 245));

        JPanel gridWrapper = new JPanel(new BorderLayout());
        gridWrapper.setBackground(new Color(245, 245, 245));
        gridWrapper.add(resultsPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(gridWrapper);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smoother scrolling speed

        // Container for Message + ScrollPane
        JPanel centerContainer = new JPanel(new BorderLayout());
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(new EmptyBorder(5, 0, 5, 0));

        centerContainer.add(messageLabel, BorderLayout.NORTH);
        centerContainer.add(scrollPane, BorderLayout.CENTER);

        add(centerContainer, BorderLayout.CENTER);

        // 3. BOTTOM PANEL: Navigation
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Action Listeners ---
        searchButton.addActionListener(e -> {
            if (controller != null) {
                String keyword = keywordField.getText();
                String category = (String) categoryBox.getSelectedItem();
                controller.execute(keyword, category);
            }
        });
    }

    public void setSearchListingsController(SearchListingsController controller) {
        this.controller = controller;
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            SearchListingsState state = (SearchListingsState) evt.getNewValue();
            updateView(state);
        }
    }

    /**
     * Rebuilds the grid of cards based on the new state.
     */
    private void updateView(SearchListingsState state) {
        resultsPanel.removeAll(); // Clear old cards

        List<SearchListingsState.ListingViewModel> results = state.getResults();

        if (results.isEmpty()) {
            if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
                messageLabel.setText(state.getErrorMessage());
                messageLabel.setForeground(Color.RED);
            } else {
                messageLabel.setText("No results found.");
                messageLabel.setForeground(Color.BLACK);
            }
        } else {
            String msg = state.isShowingFallbackResults()
                    ? "No exact match. Showing results for: " + state.getCategoryName()
                    : "Results found: " + results.size();
            messageLabel.setText(msg);
            messageLabel.setForeground(Color.BLACK);

            // Create a card for each listing
            for (SearchListingsState.ListingViewModel listing : results) {
                resultsPanel.add(createListingCard(listing));
            }
        }

        // Refresh UI to show new cards
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    /**
     * Creates a single visual card for a listing.
     */
    private JPanel createListingCard(SearchListingsState.ListingViewModel listing) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);

        // Fixed height ensures the grid rows stay uniform
        card.setPreferredSize(new Dimension(100, 200));

        // -- 1. Title --
        JLabel nameLabel = new JLabel(listing.getName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // -- 2. Categories --
        JLabel categoryLabel = new JLabel(listing.getCategorySummary());
        categoryLabel.setFont(new Font("SansSerif", Font.ITALIC, 10));
        categoryLabel.setForeground(Color.DARK_GRAY);
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // -- 3. Description --
        JTextArea descArea = new JTextArea(listing.getDescription());
        descArea.setFont(new Font("SansSerif", Font.PLAIN, 11));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setFocusable(false);
        descArea.setOpaque(false);

        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBorder(null);
        descScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        descScroll.getViewport().setOpaque(false);
        descScroll.setOpaque(false);


        // Assemble Card
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(categoryLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(descScroll);

        return card;
    }
}