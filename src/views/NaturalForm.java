package views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

import controller.NaturalFormAction;

public class NaturalForm {

    private JFrame parentFrame;
    private JTextField mathField, physicsField, chemistryField;
    private JButton btnCancel, btnSet, btnGet;
    private JTable table; // Bảng hiển thị dữ liệu

    public NaturalForm(JFrame frame) {
        this.parentFrame = frame;
        showForm(); // Hiển thị form ngay khi khởi tạo
    }

    public void showForm() {
        // Tạo các label và text field cho các môn học
        JLabel mathLabel = new JLabel("Toán:");
        JLabel physicsLabel = new JLabel("Lý:");
        JLabel chemistryLabel = new JLabel("Hóa:");

        // Thiết lập font cho các label
        Font labelFont = new Font("Arial", Font.BOLD, 18);
        mathLabel.setFont(labelFont);
        physicsLabel.setFont(labelFont);
        chemistryLabel.setFont(labelFont);

        // Khởi tạo các text field
        mathField = new JTextField();
        physicsField = new JTextField();
        chemistryField = new JTextField();

        // Tạo các button Cancel, Set và Get
        btnCancel = new JButton("Hủy bỏ");
        btnSet = new JButton("xét Điều Kiện");
        btnGet = new JButton("Hiển thị");

        // Thiết lập màu sắc cho button
        btnCancel.setBackground(Color.RED);
        btnCancel.setForeground(Color.WHITE);
        btnSet.setBackground(Color.GREEN);
        btnSet.setForeground(Color.BLACK);
        btnGet.setBackground(Color.BLUE);
        btnGet.setForeground(Color.WHITE);

        // Đặt kích thước cho button và text field
        Dimension buttonSize = new Dimension(100, 30);
        btnCancel.setPreferredSize(buttonSize);
        btnSet.setPreferredSize(buttonSize);
        btnGet.setPreferredSize(buttonSize);

        Dimension textFieldSize = new Dimension(200, 30);
        mathField.setPreferredSize(textFieldSize);
        physicsField.setPreferredSize(textFieldSize);
        chemistryField.setPreferredSize(textFieldSize);

        // Tạo panel và bố trí các thành phần
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tạo panel để vẽ hình nền
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Tải hình ảnh nền
                ImageIcon background = new ImageIcon("src/assets/BK1.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(panel, BorderLayout.NORTH);

        // Đăng ký sự kiện cho các button
        NaturalFormAction actionListener = new NaturalFormAction(this, parentFrame);
        btnCancel.addActionListener(actionListener);
        btnSet.addActionListener(actionListener);
        btnGet.addActionListener(actionListener);

        // Thêm các thành phần vào panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(mathLabel, gbc);

        gbc.gridx = 1;
        panel.add(mathField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(physicsLabel, gbc);

        gbc.gridx = 1;
        panel.add(physicsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(chemistryLabel, gbc);

        gbc.gridx = 1;
        panel.add(chemistryField, gbc);

        // Thêm các button vào panel
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(btnCancel, gbc);

        gbc.gridx = 1;
        panel.add(btnSet, gbc);

        gbc.gridx = 2;
        panel.add(btnGet, gbc);

        // Thay đổi nội dung của JFrame
        parentFrame.getContentPane().removeAll();
        parentFrame.add(backgroundPanel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    // Phương thức để hiển thị dữ liệu từ database
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

    // Getter cho các trường văn bản
    public JTextField getMathField() {
        return mathField;
    }

    public JTextField getPhysicsField() {
        return physicsField;
    }

    public JTextField getChemistryField() {
        return chemistryField;
    }

    // Getter cho các button
    public JButton getBtnCancel() {
        return btnCancel;
    }

    public JButton getBtnSet() {
        return btnSet;
    }

    public JButton getBtnGet() {
        return btnGet;
    }
}
