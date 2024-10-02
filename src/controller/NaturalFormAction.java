package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import views.Form;
import views.NaturalForm;

public class NaturalFormAction implements ActionListener {
    private NaturalForm naturalForm;
    private JFrame parentFrame;

    public NaturalFormAction(NaturalForm naturalForm, JFrame parentFrame) {
        this.naturalForm = naturalForm;
        this.parentFrame = parentFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == naturalForm.btnCancel) {
            // Quay lại giao diện chính
            parentFrame.getContentPane().removeAll();
            new Form().showForm(parentFrame); // Hiển thị lại giao diện chính
            parentFrame.revalidate();
            parentFrame.repaint();
        } else if (e.getSource() == naturalForm.btnSet) {
            // Xử lý khi nhấn nút Set
            try {
                // Lấy dữ liệu từ các trường nhập liệu
                double mathScore = Double.parseDouble(naturalForm.getMathField().getText());
                double physicsScore = Double.parseDouble(naturalForm.getPhysicsField().getText());
                double chemistryScore = Double.parseDouble(naturalForm.getChemistryField().getText());

                // Tính điểm trung bình các môn tự nhiên
                double averageNaturalScore = (mathScore + physicsScore + chemistryScore) / 3;

                // Kiểm tra điều kiện môn tự nhiên
                if (averageNaturalScore >= 6.5 && mathScore >= 6.5) {
                    JOptionPane.showMessageDialog(parentFrame, 
                        "Điểm đã nhập:\nToán: " + mathScore + "\nLý: " + physicsScore + "\nHóa: " + chemistryScore + 
                        "\nĐiểm trung bình các môn tự nhiên: " + averageNaturalScore + "\nKết quả: Đạt");

                    // Lưu điểm vào cơ sở dữ liệu nếu đạt yêu cầu
                    saveToDatabase(mathScore, physicsScore, chemistryScore, averageNaturalScore);
                } else {
                    // Kiểm tra lý do không đạt
                    if (mathScore < 6.5) {
                        JOptionPane.showMessageDialog(parentFrame, "Điểm toán không đạt yêu cầu!");
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "Điểm trung bình các môn tự nhiên không đạt yêu cầu!");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parentFrame, "Vui lòng nhập điểm hợp lệ.");
            }
        }
    }

    private void saveToDatabase(double math, double physics, double chemistry, double average) {
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
            String sql = "INSERT INTO student_scores (math, physics, chemistry, average) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            // Thiết lập giá trị cho các tham số
            pstmt.setDouble(1, math);
            pstmt.setDouble(2, physics);
            pstmt.setDouble(3, chemistry);
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
