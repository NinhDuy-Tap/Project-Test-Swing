package views;

import javax.swing.*;
import controller.SocialFormAction;
import java.awt.*;

public class SocialForm {
    private JFrame parentFrame;
    private JTextField geographyField, historyField, civicField;
    public JButton btnCancel, btnSet; // Public access for the buttons

    public SocialForm(JFrame frame) {
        this.parentFrame = frame;
        showForm(); // Show the form immediately upon creation
    }

    public void showForm() {
        // Create labels and text fields for subjects
        JLabel geographyLabel = new JLabel("Địa Lý:");
        JLabel historyLabel = new JLabel("Lịch Sử:");
        JLabel civicLabel = new JLabel("GDCD:");

        // Set font size for the labels
        Font labelFont = new Font("Arial", Font.BOLD, 24); // Larger font size
        geographyLabel.setFont(labelFont);
        historyLabel.setFont(labelFont);
        civicLabel.setFont(labelFont);

        // Initialize text fields
        geographyField = new JTextField();
        historyField = new JTextField();
        civicField = new JTextField();

        // Create Cancel and Set buttons
        btnCancel = new JButton("Cancel");
        btnSet = new JButton("Set Điều Kiện");

        // Set button colors
        btnCancel.setBackground(Color.RED);
        btnCancel.setForeground(Color.WHITE);
        btnSet.setBackground(Color.GREEN);
        btnSet.setForeground(Color.BLACK);

        // Set background color for text fields
        geographyField.setBackground(Color.LIGHT_GRAY);
        historyField.setBackground(Color.LIGHT_GRAY);
        civicField.setBackground(Color.LIGHT_GRAY);

        // Set sizes for buttons and text fields
        Dimension buttonSize = new Dimension(300, 50); // Size for buttons
        btnCancel.setPreferredSize(buttonSize);
        btnSet.setPreferredSize(buttonSize);
        
        Dimension textFieldSize = new Dimension(300, 50); // Size for text fields
        geographyField.setPreferredSize(textFieldSize);
        historyField.setPreferredSize(textFieldSize);
        civicField.setPreferredSize(textFieldSize);

        // Create panel and layout components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Align components horizontally

        // Create background panel for drawing background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load background image
                ImageIcon background = new ImageIcon("src/assets/SocialBackground.jpg"); // Change to your image path
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new BorderLayout()); // Use BorderLayout
        backgroundPanel.add(panel, BorderLayout.CENTER);

        // Register action listeners for buttons
        SocialFormAction actionListener = new SocialFormAction(this, parentFrame);
        btnCancel.addActionListener(actionListener);
        btnSet.addActionListener(actionListener);

        // Adding components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(geographyLabel, gbc);

        gbc.gridx = 1;
        panel.add(geographyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(historyLabel, gbc);

        gbc.gridx = 1;
        panel.add(historyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(civicLabel, gbc);

        gbc.gridx = 1;
        panel.add(civicField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across both columns
        panel.add(btnCancel, gbc);

        gbc.gridy = 4; // Move to the next row
        panel.add(btnSet, gbc);

        // Update the JFrame content
        parentFrame.getContentPane().removeAll(); // Remove current components
        parentFrame.add(backgroundPanel); // Add background panel
        parentFrame.revalidate(); // Refresh frame
        parentFrame.repaint(); // Repaint frame
    }

    // Getter methods for text fields
    public JTextField getGeographyField() {
        return geographyField;
    }

    public JTextField getHistoryField() {
        return historyField;
    }

    public JTextField getCivicField() {
        return civicField;
    }
}
