//package UI;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.category.DefaultCategoryDataset;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class ThongKePanel extends JPanel {
//
//    public ThongKePanel() {
//        setLayout(new BorderLayout());
//        setBackground(new Color(245, 245, 245));  // Chọn màu nền cho panel
//        createChartPanel();
//    }
//
//    private void createChartPanel() {
//        // Dataset: Doanh thu theo tháng
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.addValue(12500000, "Doanh thu", "10/2024");
//        dataset.addValue(11000000, "Doanh thu", "11/2024");
//        dataset.addValue(9500000, "Doanh thu", "12/2024");
//        dataset.addValue(11000000, "Doanh thu", "01/2025");
//        dataset.addValue(8500000, "Doanh thu", "02/2025");
//        dataset.addValue(9000000, "Doanh thu", "03/2025");
//        dataset.addValue(10000000, "Doanh thu", "04/2025");
//        dataset.addValue(2000000, "Doanh thu", "05/2025");
//        dataset.addValue(0, "Doanh thu", "06/2025");
//        dataset.addValue(0, "Doanh thu", "07/2025");
//        dataset.addValue(0, "Doanh thu", "08/2025");
//
//        // Tạo biểu đồ
//        JFreeChart chart = ChartFactory.createBarChart(
//                "Thống kê doanh thu theo tháng",
//                "Tháng",
//                "Doanh thu (VNĐ)",
//                dataset
//        );
//
//        // Thêm biểu đồ vào một panel mới
//        ChartPanel chartPanel = new ChartPanel(chart);
//        add(chartPanel, BorderLayout.CENTER);
//    }
//}



///test lan 1
//package UI;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.category.DefaultCategoryDataset;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class ThongKePanel extends JPanel {
//
//    public ThongKePanel() {
//        setLayout(new BorderLayout());
//        setBackground(new Color(245, 245, 245));  // Chọn màu nền cho panel
//        createChartPanel();
//    }
//
//    private void createChartPanel() {
//        // Dataset: Sản phẩm bán chạy và ít bán nhất theo tháng
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        
//        // Sản phẩm bán chạy nhiều nhất trong từng tháng
//        dataset.addValue(150, "Sản phẩm bán chạy", "10/2024");
//        dataset.addValue(120, "Sản phẩm bán chạy", "11/2024");
//        dataset.addValue(180, "Sản phẩm bán chạy", "12/2024");
//        dataset.addValue(200, "Sản phẩm bán chạy", "01/2025");
//        dataset.addValue(100, "Sản phẩm bán chạy", "02/2025");
//        dataset.addValue(160, "Sản phẩm bán chạy", "03/2025");
//        dataset.addValue(140, "Sản phẩm bán chạy", "04/2025");
//        
//        // Sản phẩm bán ít nhất trong từng tháng
//        dataset.addValue(5, "Sản phẩm bán ít", "10/2024");
//        dataset.addValue(3, "Sản phẩm bán ít", "11/2024");
//        dataset.addValue(10, "Sản phẩm bán ít", "12/2024");
//        dataset.addValue(2, "Sản phẩm bán ít", "01/2025");
//        dataset.addValue(8, "Sản phẩm bán ít", "02/2025");
//        dataset.addValue(6, "Sản phẩm bán ít", "03/2025");
//        dataset.addValue(4, "Sản phẩm bán ít", "04/2025");
//
//        // Tạo biểu đồ với tiêu đề mới
//        JFreeChart chart = ChartFactory.createBarChart(
//                "Thống kê sản phẩm bán chạy và ít bán nhất theo tháng",
//                "Tháng",
//                "Số lượng sản phẩm",
//                dataset
//        );
//
//        // Thêm biểu đồ vào một panel mới
//        ChartPanel chartPanel = new ChartPanel(chart);
//        add(chartPanel, BorderLayout.CENTER);
//    }
//}



//test lan 2
package UI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ThongKePanel extends JPanel {

    public ThongKePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));  // Chọn màu nền cho panel
        createChartPanel();
    }

    private void createChartPanel() {
        // Dataset: Sản phẩm bán chạy nhất và ít bán nhất theo tháng
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Truy vấn cơ sở dữ liệu để lấy danh sách sản phẩm bán được trong tháng
        String sql = "SELECT P.TenSP, MONTH(H.NgayHD) AS Thang, SUM(CT.SL) AS SoLuongBan " +
                     "FROM HoaDon H " +
                     "JOIN ChiTietHoaDon CT ON H.MaHD = CT.MaHD " +
                     "JOIN SanPham P ON CT.MaSP = P.MaSP " +
                     "WHERE H.NgayHD BETWEEN '2024-01-01' AND '2025-12-31' " +
                     "GROUP BY P.TenSP, MONTH(H.NgayHD) " +
                     "ORDER BY SoLuongBan DESC";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=your_database", "username", "password");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Thêm dữ liệu vào dataset
            while (rs.next()) {
                String productName = rs.getString("TenSP");
                int quantitySold = rs.getInt("SoLuongBan");
                String month = rs.getString("Thang");  // Tháng của sản phẩm bán

                // Chỉ lấy sản phẩm bán chạy và ít bán nhất, ví dụ: chọn 5 sản phẩm bán chạy nhất và 5 sản phẩm ít bán nhất
                if (quantitySold > 0) {
                    dataset.addValue(quantitySold, productName, month);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tạo biểu đồ
        JFreeChart chart = ChartFactory.createBarChart(
                "Thống kê sản phẩm bán chạy và ít bán nhất theo tháng",
                "Tháng",
                "Số lượng sản phẩm",
                dataset
        );

        // Thêm biểu đồ vào một panel mới
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }
}


