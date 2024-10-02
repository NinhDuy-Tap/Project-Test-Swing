package views;

import javax.swing.*;
import controller.NaturalFormAction;
import java.awt.*;

public class NaturalForm {

    private JFrame parentFrame;
    private JTextField mathField, physicsField, chemistryField;
    public JButton btnCancel, btnSet; // Make buttons public

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
        Font labelFont = new Font("Arial", Font.BOLD, 24); // Kích thước font lớn hơn
        mathLabel.setFont(labelFont);
        physicsLabel.setFont(labelFont);
        chemistryLabel.setFont(labelFont);

        // Khởi tạo các text field
        mathField = new JTextField();
        physicsField = new JTextField();
        chemistryField = new JTextField();

        // Tạo các button Cancel và Set
        btnCancel = new JButton("Cancel");
        btnSet = new JButton("Set Điều Kiện");

        // Thiết lập màu sắc cho button
        btnCancel.setBackground(Color.RED);
        btnCancel.setForeground(Color.WHITE);
        btnSet.setBackground(Color.GREEN);
        btnSet.setForeground(Color.BLACK);

        // Thiết lập màu nền cho các text field
        mathField.setBackground(Color.LIGHT_GRAY);
        physicsField.setBackground(Color.LIGHT_GRAY);
        chemistryField.setBackground(Color.LIGHT_GRAY);

        // Đặt kích thước cho button và text field
        Dimension buttonSize = new Dimension(50, 50); // Kích thước cho button
        btnCancel.setPreferredSize(buttonSize);
        btnSet.setPreferredSize(buttonSize);
        
        Dimension textFieldSize = new Dimension(300, 50); // Kích thước cho text field
        mathField.setPreferredSize(textFieldSize);
        physicsField.setPreferredSize(textFieldSize);
        chemistryField.setPreferredSize(textFieldSize);

        // Tạo panel và bố trí các thành phần
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Căn chỉnh theo chiều ngang

        // Tạo panel để vẽ hình nền
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Tải hình ảnh nền
                ImageIcon background = new ImageIcon("src/assets/TN.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new BorderLayout()); // Sử dụng BorderLayout
        backgroundPanel.add(panel, BorderLayout.CENTER);

        // Đăng ký sự kiện cho các button
        NaturalFormAction actionListener = new NaturalFormAction(this, parentFrame);
        btnCancel.addActionListener(actionListener);
        btnSet.addActionListener(actionListener);

        // Adding components to the panel
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

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Chiếm toàn bộ chiều rộng
        panel.add(btnCancel, gbc);

        gbc.gridy = 4; // Xuống dòng mới
        panel.add(btnSet, gbc);

        // Thay đổi nội dung của JFrame
        parentFrame.getContentPane().removeAll(); // Xóa tất cả các thành phần hiện tại
        parentFrame.add(backgroundPanel); // Thêm panel với hình nền
        parentFrame.revalidate(); // Cập nhật lại frame
        parentFrame.repaint(); // Vẽ lại frame
    }

    // Getter methods for the text fields
    public JTextField getMathField() {
        return mathField;
    }

    public JTextField getPhysicsField() {
        return physicsField;
    }

    public JTextField getChemistryField() {
        return chemistryField;
    }
}
