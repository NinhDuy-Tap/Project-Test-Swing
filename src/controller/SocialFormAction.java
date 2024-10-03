package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                if (averageSocialScore >= 6.5 && civicScore >= 6.5
                		&& civicScore <=10 && historyScore >=0 && historyScore <=10
                		&& geographyScore >=0 && geographyScore<=10) {
                	
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
                        
                    } else if (civicScore > 10 || historyScore > 10 || geographyScore > 10) {
                        JOptionPane.showMessageDialog(parentFrame, "Điểm không được vượt quá 10!");
                    } 
                    
                    else {
                        JOptionPane.showMessageDialog(parentFrame, "Điểm trung bình các môn xã hội không đạt yêu cầu!");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parentFrame, "Vui lòng nhập điểm hợp lệ.");
            }
        } else if (e.getSource() ==  socialForm.getBtnGet()) {
            // Xử lý khi nhấn nút Get
          getDataFromDatabase();
        	
        	
        }
    }

    private void getDataFromDatabase() {
    	
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
             String sql = "SELECT geography, history, civic, average FROM social";
             pstmt = conn.prepareStatement(sql);

             rs = pstmt.executeQuery();

             // Tạo mô hình bảng (TableModel) để chứa dữ liệu
             DefaultTableModel tableModel = new DefaultTableModel();
             tableModel.addColumn("Điểm Địa lý ");
             tableModel.addColumn("Điểm Lịch sử ");
             tableModel.addColumn("Điểm công dân");
             tableModel.addColumn("Điểm Trung Bình");

             // Duyệt qua kết quả trả về từ ResultSet và thêm vào mô hình bảng
             while (rs.next()) {
                 double geography = rs.getDouble("geopraphy");
                 double history = rs.getDouble("hisory");
                 double civic = rs.getDouble("civic");
                 double average = rs.getDouble("average");
                 tableModel.addRow(new Object[]{geography, history, civic, average});
             }

             socialForm.displayTable(tableModel);

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
