package views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form extends JFrame implements ActionListener {
    private static JFrame f;
    private static JButton b, b1;

    public static void main(String[] args) {
        f = new JFrame("Điều kiện tốt nghiệp đại học");
        b = new JButton("Tự nhiên");
        b1 = new JButton("Xã hội");

        b.setPreferredSize(new Dimension(200, 80));
        b1.setPreferredSize(new Dimension(200, 80));

        Form form = new Form();
        b.addActionListener(form);
        b1.addActionListener(form);

        JLabel label = new JLabel("Điều kiện tốt nghiệp", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 70));
        label.setForeground(Color.PINK);

        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("src/assets/BK.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);
        
        gbc.gridy = 1;
        panel.add(b, gbc);
        
        gbc.gridy = 2;
        panel.add(b1, gbc);

        f.add(panel);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void showForm(JFrame frame) {
        // Tạo JLabel để hiển thị dòng chữ "Điều kiện tốt nghiệp"
        JLabel label = new JLabel("Điều kiện tốt nghiệp", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 70)); // Tăng kích thước chữ
        label.setForeground(Color.PINK); // Đổi màu chữ nếu muốn
        
        // Tạo panel và thêm hình nền 
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("src/assets/BK.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        p.setLayout(new GridBagLayout()); // Sử dụng GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
        gbc.gridx = 0;
        gbc.gridy = 0;
        p.add(label, gbc);

        gbc.gridy = 1; // Thay đổi vị trí theo trục y để đặt nút dưới label
        p.add(b, gbc);

        gbc.gridy = 2; // Thêm nút Xã hội
        p.add(b1, gbc);

        // Thêm panel vào frame 
        frame.getContentPane().removeAll(); // Xóa tất cả các thành phần hiện tại
        frame.add(p); // Thêm panel với hình nền
        frame.revalidate(); // Cập nhật lại frame
        frame.repaint(); // Vẽ lại frame
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        f.getContentPane().removeAll(); // Xóa tất cả các thành phần hiện tại khỏi frame
        f.repaint(); // Làm mới frame để áp dụng thay đổi

        if (e.getSource() == b) {
            new NaturalForm(f); // Hiển thị form Tự nhiên trong cùng JFrame
        } else if (e.getSource() == b1) {
            new SocialForm(f); // Hiển thị form Xã hội trong cùng JFrame
        }
    }


}
