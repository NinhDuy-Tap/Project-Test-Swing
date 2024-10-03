package views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.SocialFormAction;

import java.awt.*;

public class SocialForm {
    private JFrame parentFrame;
    private JTextField geographyField, historyField, civicField;
    public JButton btnCancel, btnSet, btnGet; // Public access for the buttons
    public JTable table;

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

        // Create Cancel, Set, and Get buttons
        btnCancel = new JButton("Hủy bỏ");
        btnSet = new JButton("xét điều Kiện");
        btnGet = new JButton("Hiển thị");

        // Set button colors
        btnCancel.setBackground(Color.RED);
        btnCancel.setForeground(Color.WHITE);
        btnSet.setBackground(Color.GREEN);
        btnSet.setForeground(Color.BLACK);
        btnGet.setForeground(Color.BLUE);

        // Set background color for text fields
        geographyField.setBackground(Color.LIGHT_GRAY);
        historyField.setBackground(Color.LIGHT_GRAY);
        civicField.setBackground(Color.LIGHT_GRAY);

        // Set sizes for buttons and text fields
        Dimension buttonSize = new Dimension(100, 30); // Size for buttons
        btnCancel.setPreferredSize(buttonSize);
        btnSet.setPreferredSize(buttonSize);
        btnGet.setPreferredSize(buttonSize);
        
        Dimension textFieldSize = new Dimension(200, 30); // Size for text fields
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
        btnGet.addActionListener(actionListener);

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

        gbc.gridy = 5; // Add the "Hiển thị" button in the next row
        panel.add(btnGet, gbc);

        // Update the JFrame content
        parentFrame.getContentPane().removeAll(); // Remove current components
        parentFrame.add(backgroundPanel); // Add background panel
        parentFrame.revalidate(); // Refresh frame
        parentFrame.repaint(); // Repaint frame
    }

    public void displayTable(DefaultTableModel tableModel) {
        if (table != null) {
            parentFrame.remove(new JScrollPane(table)); // Xóa bảng cũ nếu có
        }

        // Khởi tạo bảng mới với dữ liệu
        table = new JTable(tableModel);

        // Thiết lập chiều rộng các cột
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // Cột Toán
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Cột Lý
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // Cột Hóa
        table.getColumnModel().getColumn(3).setPreferredWidth(150); // Cột Điểm Trung Bình

        // Thiết lập kiểu cho các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Thêm bảng vào JScrollPane để có thể cuộn
        JScrollPane scrollPane = new JScrollPane(table);

        // Thêm bảng vào giữa JFrame
        parentFrame.add(scrollPane, BorderLayout.CENTER);
        parentFrame.revalidate();
        parentFrame.repaint();
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

    public JButton getBtnSet() {
        return btnSet;
    }

    public JButton getBtnGet() {
        return btnGet;
    }

}
