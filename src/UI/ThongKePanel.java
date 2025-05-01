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
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String sql = "SELECT HH.TENHH, MONTH(HDBH.NGAYHDBH) AS THANG, SUM(CTHD.SOLUONG) AS SOLUONGBAN " +
                "FROM HOADONBANHANG HDBH " +
                "JOIN CHITIETHOADON CTHD ON HDBH.MAHDBH = CTHD.MAHDBH " +
                "JOIN HANGHOA HH ON HH.MAHH = CTHD.MAHH " +
                "WHERE HDBH.NGAYHDBH BETWEEN '2024-01-01' AND '2025-12-31' " +
                "GROUP BY HH.TENHH, MONTH(HDBH.NGAYHDBH) " +
                "ORDER BY THANG, SOLUONGBAN DESC";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=QL_QuanCafe", "QLQuanCafe", "123");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String tenSP = rs.getString("TENHH");
                int thang = rs.getInt("THANG");
                int soLuong = rs.getInt("SOLUONGBAN");
                dataset.addValue(soLuong, tenSP, String.format("%02d/2024", thang));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Sản phẩm bán ra theo tháng",
                "Tháng",
                "Số lượng",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

}


