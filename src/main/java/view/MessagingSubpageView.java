package view;

import interface_adapter.messaging.MessagingViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class MessagingSubpageView extends JPanel implements ActionListener, PropertyChangeListener {
    private final MessagingViewModel viewModel;
    private final JLabel titleLabel;
    private final JLabel infoLabel;
    private final JLabel errorLabel;
    private final JButton opepnGmailButton;
    private final JButton backButton;

    /**
     * @param viewModel MessagingViewModel
     * @param onBack
     */
    public MessagingSubView(MessagingViewModel viewModel, Runnable onBack) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        //basic settings
        setBounds(100, 100, 450, 300);
        setBackground(new Color(255, 255, 255));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        //title
        titleLabel = new JLabel("Email");
        titleLabel.setFont(titileLabel.getFont().deriveFont(Font.BOLD, 20f));
        add(titleLabel, BorderLayout.NORTH);

        //middle
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        infoLabel = new JLabel("Write the message through Gmail");
        opepnGmailButton = new JButton("Opepn Gmail");
        opepnGmailButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        opepnGmailButton.addActionListener(this);

        centerPanel.add(infoLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(opepnGmailButton);

        add(centerPanel, BorderLayout.CENTER);

        //bottom
        JPanel bottomPanel = new JPanel();
        errorLabel = new JLabel();
        errorLabel.setFont(errorLabel.getFont().deriveFont(Font.BOLD));

        backButton = new JButton("Back");
        bottomPanel.add(errorLabel, BorderLayout.CENTER);
        bottomPanel.add(backButton, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        opepnGmailButton.addActionListener(e -> openGmail());

        backButton.addActionListener(e ->{
            if (onBack != null) {
                onBack.run();
            }
        });

        // actionlistener
        this.viewModel.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                refreshFromState();
            }
        });
    }
}
