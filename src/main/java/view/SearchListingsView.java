package view;

import interface_adapter.search.SearchListingsController;
import interface_adapter.search.SearchListingsState;
import interface_adapter.search.SearchListingsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class SearchListingsView extends JPanel implements PropertyChangeListener {
    public final String viewName = "search listings";
    private final SearchListingsViewModel viewModel;
    private SearchListingsController controller;

    private final JTextField keywordField = new JTextField(20);
    private final JComboBox<String> categoryBox;
    private final JButton searchButton = new JButton("Search");
    private final JButton backButton = new JButton("Back to Home");
    private final JList<String> resultsList = new JList<>();
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JLabel messageLabel = new JLabel("");

    public SearchListingsView(SearchListingsViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // --- Top Panel: Search Controls ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        categoryBox = new JComboBox<>();
        // Populate categories from ViewModel
        if (viewModel.getCategories() != null) {
            for (String cat : viewModel.getCategories()) {
                categoryBox.addItem(cat);
            }
        }

        topPanel.add(new JLabel("Keyword:"));
        topPanel.add(keywordField);
        topPanel.add(new JLabel("Category:"));
        topPanel.add(categoryBox);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        // --- Center Panel: Results ---
        resultsList.setModel(listModel);
        JScrollPane scrollPane = new JScrollPane(resultsList);

        JPanel centerPanel = new JPanel(new BorderLayout());
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(messageLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // --- Bottom Panel: Navigation ---
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Listeners ---
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

    private void updateView(SearchListingsState state) {
        // Update Results List
        listModel.clear();
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
                    ? "No keyword matches found. Showing results for category: " + state.getCategoryName()
                    : "Results found: " + results.size();
            messageLabel.setText(msg);
            messageLabel.setForeground(Color.BLACK);

            for (SearchListingsState.ListingViewModel listing : results) {
                listModel.addElement(listing.getName() + " [" + listing.getCategorySummary() + "]");
            }
        }
    }
}