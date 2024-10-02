package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
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
        if (e.getSource() == naturalForm.getBtnCancel()) {
            // Quay lại giao diện chính
            parentFrame.getContentPane().removeAll();
            new Form().showForm(parentFrame); // Hiển thị lại giao diện chính
            parentFrame.revalidate();
            parentFrame.repaint();
        } else if (e.getSource() == naturalForm.getBtnSet()) {
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
        } else if (e.getSource() == naturalForm.getBtnGet()) {
            // Xử lý khi nhấn nút Get
            retrieveDataFromDatabase();
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

    private void retrieveDataFromDatabase() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // Kết nối tới cơ sở dữ liệu PostgreSQL
            String url = "jdbc:postgresql://localhost:5432/dbtest"; // Chỉnh sửa URL kết nối PostgreSQL
            String username = "postgres"; // Thay bằng tên người dùng PostgreSQL của bạn
            String password = "dang123"; // Thay bằng mật khẩu của bạn

            conn = DriverManager.getConnection(url, username, password);

            // Câu lệnh SQL để lấy dữ liệu từ bảng
            String sql = "SELECT math, physics, chemistry, average FROM student_scores";
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            // Tạo mô hình bảng (TableModel) để chứa dữ liệu
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Toán");
            tableModel.addColumn("Lý");
            tableModel.addColumn("Hóa");
            tableModel.addColumn("Điểm Trung Bình");

            // Duyệt qua kết quả trả về từ ResultSet và thêm vào mô hình bảng
            while (rs.next()) {
                double math = rs.getDouble("math");
                double physics = rs.getDouble("physics");
                double chemistry = rs.getDouble("chemistry");
                double average = rs.getDouble("average");
                tableModel.addRow(new Object[]{math, physics, chemistry, average});
            }

            // Hiển thị dữ liệu ra bảng
            naturalForm.displayTable(tableModel);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Lỗi khi lấy dữ liệu từ cơ sở dữ liệu.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
