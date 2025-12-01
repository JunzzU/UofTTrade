package view;

import entity.Category;
import interface_adapter.view_listing.ViewListingController;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class ListingPreviewPanel extends JPanel {

    private static final int TITLE_FONT_SIZE = 14;
    private static final int OTHER_FONT_SIZE = 10;
    private static final int PANEL_WIDTH = 150;
    private static final int PANEL_HEIGHT = 300;
    private static final String FONT = "Rubik";
    private final JSONObject listing;
    private final ViewListingController viewListingController;

    public ListingPreviewPanel(JSONObject listing, ViewListingController viewListingController) {
        this.listing = listing;
        this.viewListingController = viewListingController;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        final JLabel nameLabel = new JLabel((String) listing.get("Name"));
        nameLabel.setFont(new Font(FONT, Font.BOLD, TITLE_FONT_SIZE));
        final StringBuilder categories = new StringBuilder();
        final JSONArray categoryList = (JSONArray) listing.get("Categories");
        for (int i = 0; i < categoryList.length() - 1; i++) {
            final JSONObject category = categoryList.getJSONObject(i);
            categories.append((String) category.get("name")).append(", ");
        }
        categories.append((String) categoryList.getJSONObject(categoryList.length() - 1).get("name"));
        final JLabel categoryLabel = new JLabel("Categories: " + categories);
        categoryLabel.setFont(new Font(FONT, Font.PLAIN, OTHER_FONT_SIZE));

        final JLabel ownerLabel = new JLabel("Owner: " + listing.get("Owner"));
        ownerLabel.setFont(new Font(FONT, Font.PLAIN, OTHER_FONT_SIZE));

        this.add(nameLabel);
        this.add(categoryLabel);
        this.add(ownerLabel);

        final MouseListener ml = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    viewListingController.execute((String) listing.get("Name"), (String) listing.get("Owner"));
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        this.addMouseListener(ml);

    }

}
