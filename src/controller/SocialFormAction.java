package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import views.Form;
import views.SocialForm;

public class SocialFormAction implements ActionListener {
    private SocialForm socialForm;
    private JFrame parentFrame;

    public SocialFormAction(SocialForm socialForm, JFrame parentFrame) {
        this.socialForm = socialForm;
        this.parentFrame = parentFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == socialForm.btnCancel) {
            // Quay lại giao diện chính
            parentFrame.getContentPane().removeAll();
            new Form().showForm(parentFrame); // Hiển thị lại giao diện chính
            parentFrame.revalidate();
            parentFrame.repaint();
        } else if (e.getSource() == socialForm.btnSet) {
            // Xử lý khi nhấn nút Set
            try {
                // Lấy dữ liệu từ các trường nhập liệu
                double geographyScore = Double.parseDouble(socialForm.getGeographyField().getText());
                double historyScore = Double.parseDouble(socialForm.getHistoryField().getText());
                double civicScore = Double.parseDouble(socialForm.getCivicField().getText());

                // Tính điểm trung bình các môn xã hội
                double averageSocialScore = (geographyScore + historyScore + civicScore) / 3;

                // Kiểm tra điều kiện môn xã hội
                if (averageSocialScore >= 6.5 && civicScore >= 6.5 ) {
                    JOptionPane.showMessageDialog(parentFrame, 
                        "Điểm đã nhập:\nĐịa Lý: " + geographyScore + 
                        "\nLịch Sử: " + historyScore + 
                        "\nGDCD: " + civicScore + 
                        "\nĐiểm trung bình các môn xã hội: " + averageSocialScore + 
                        "\nKết quả: Đạt");

                    // Lưu điểm vào cơ sở dữ liệu nếu đạt yêu cầu
                    saveToDatabase(geographyScore, historyScore, civicScore, averageSocialScore);
                } else {
                    // Kiểm tra lý do không đạt
                    if (civicScore < 6.5) {
                        JOptionPane.showMessageDialog(parentFrame, "Điểm GDCD không đạt yêu cầu!");
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "Điểm trung bình các môn xã hội không đạt yêu cầu!");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parentFrame, "Vui lòng nhập điểm hợp lệ.");
            }
        }
    }

    private void saveToDatabase(double geography, double history, double civic, double average) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Kết nối tới cơ sở dữ liệu PostgreSQL
            String url = "jdbc:postgresql://localhost:5432/dbtest"; // Chỉnh sửa URL kết nối PostgreSQL
            String username = "postgres"; // Thay bằng tên người dùng PostgreSQL của bạn
            String password = "dang123"; // Thay bằng mật khẩu của bạn

            // Kết nối với cơ sở dữ liệu PostgreSQL
            conn = DriverManager.getConnection(url, username, password);

            // Câu lệnh SQL để chèn dữ liệu
            String sql = "INSERT INTO social (geography, history, civic, average) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            // Thiết lập giá trị cho các tham số
            pstmt.setDouble(1, geography);
            pstmt.setDouble(2, history);
            pstmt.setDouble(3, civic);
            pstmt.setDouble(4, average);

            // Thực thi câu lệnh SQL
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(parentFrame, "Lưu điểm thành công vào cơ sở dữ liệu.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Lỗi khi lưu vào cơ sở dữ liệu.");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
