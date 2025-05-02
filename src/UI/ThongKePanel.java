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
//package UI;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.category.DefaultCategoryDataset;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.*;
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
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//        String sql = "SELECT HH.TENHH, MONTH(HDBH.NGAYHDBH) AS THANG, SUM(CTHD.SOLUONG) AS SOLUONGBAN " +
//                "FROM HOADONBANHANG HDBH " +
//                "JOIN CHITIETHOADON CTHD ON HDBH.MAHDBH = CTHD.MAHDBH " +
//                "JOIN HANGHOA HH ON HH.MAHH = CTHD.MAHH " +
//                "WHERE HDBH.NGAYHDBH BETWEEN '2024-01-01' AND '2025-12-31' " +
//                "GROUP BY HH.TENHH, MONTH(HDBH.NGAYHDBH) " +
//                "ORDER BY THANG, SOLUONGBAN DESC";
//
//        try (Connection conn = DriverManager.getConnection(
//                "jdbc:sqlserver://localhost:1433;databaseName=QL_QuanCafe", "QLQuanCafe", "123");
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                String tenSP = rs.getString("TENHH");
//                int thang = rs.getInt("THANG");
//                int soLuong = rs.getInt("SOLUONGBAN");
//                dataset.addValue(soLuong, tenSP, String.format("%02d/2024", thang));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        JFreeChart chart = ChartFactory.createBarChart(
//                "Sản phẩm bán ra theo tháng",
//                "Tháng",
//                "Số lượng",
//                dataset
//        );
//
//        ChartPanel chartPanel = new ChartPanel(chart);
//        add(chartPanel, BorderLayout.CENTER);
//    }
//
//}

//test lần 3
//package UI;
//
//import ConnectDB.ConnectDB;
//import Dao.ThongKe_DAO;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.general.DefaultPieDataset;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.Connection;
//import java.util.Map;
//
//public class ThongKePanel extends JPanel {
//
//    public ThongKePanel() {
//        setLayout(new BorderLayout());
//        setBackground(Color.WHITE);
//        initChart();
//    }
//
//    private void initChart() {
//        try {
//            ConnectDB.getInstance().connect(); // Đảm bảo đã kết nối
//            Connection conn = ConnectDB.getConnection();
//
//            if (conn == null) {
//                add(new JLabel("⚠️ Không thể kết nối đến cơ sở dữ liệu"), BorderLayout.CENTER);
//                return;
//            }
//
//            Map<String, Integer> data = ThongKe_DAO.getSoLuongSanPhamBanRa(conn);
//            if (data.isEmpty()) {
//                add(new JLabel("🔍 Không có dữ liệu để thống kê"), BorderLayout.CENTER);
//                return;
//            }
//
//            // Tạo DefaultPieDataset cho biểu đồ
//            DefaultPieDataset dataset = new DefaultPieDataset();            
//
//            for (Map.Entry<String, Integer> entry : data.entrySet()) {
//                String label = entry.getKey() + " - " + entry.getValue();
//                dataset.setValue(label, entry.getValue());
//            }
//
//            // Tạo biểu đồ pie
//            JFreeChart chart = ChartFactory.createPieChart(
//                    "Tỉ lệ sản phẩm bán ra", dataset, true, true, false
//            );
//
//            // Thêm biểu đồ vào panel
//            ChartPanel chartPanel = new ChartPanel(chart);
//            chartPanel.setPreferredSize(new Dimension(getWidth(), getHeight() - 100)); // Chừa không gian cho ô chú thích
//            add(chartPanel, BorderLayout.CENTER);
//
//            // Tạo JPanel cho ô chú thích
//            JPanel legendPanel = new JPanel();
//            legendPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//
//            // Tạo JLabel cho chú thích
//            JLabel legendLabel = new JLabel("Tên hàng - Số lượng bán được");
//            legendLabel.setPreferredSize(new Dimension(200, 40));  // Kích thước ô vuông
//            legendLabel.setBackground(Color.LIGHT_GRAY);           // Màu nền
//            legendLabel.setOpaque(true);                            // Đảm bảo nền có màu
//            legendLabel.setHorizontalAlignment(SwingConstants.CENTER);
//            legendPanel.add(legendLabel);
//
//            // Thêm ô chú thích vào panel dưới biểu đồ
//            add(legendPanel, BorderLayout.SOUTH);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            add(new JLabel("⚠️ Lỗi khi tải dữ liệu thống kê"), BorderLayout.CENTER);
//        }
//    }
//}


//test lan 4
package UI;

import ConnectDB.ConnectDB;
import Dao.ThongKe_DAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Map;

public class ThongKePanel extends JPanel {
    private JPanel chartContainer;
    private JComboBox<String> comboBoxNgay;

    public ThongKePanel() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab thống kê theo ngày
        JPanel panelNgay = new JPanel(new BorderLayout());
        chartContainer = new JPanel(new BorderLayout());

        comboBoxNgay = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            comboBoxNgay.addItem(String.format("%02d", i));
            // % để bắt đầu chuỗi định dạng,độ dài tối đa 2 chữ số, nếu số 1 chữ số thì thêm 0 dô cho đẹp, d là decimal
        }
        // gọi phương thức để xử lí
        comboBoxNgay.addActionListener(e -> loadBieuDoTheoNgay());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Chọn ngày:"));
        topPanel.add(comboBoxNgay);

        panelNgay.add(topPanel, BorderLayout.NORTH);
        panelNgay.add(chartContainer, BorderLayout.CENTER);
        tabbedPane.addTab("Theo ngày", panelNgay);

        // Tab thống kê theo tháng
        JPanel panelThang = new JPanel(new BorderLayout());
        panelThang.add(createBieuDoTheoThang(), BorderLayout.CENTER);
        tabbedPane.addTab("Theo tháng", panelThang);

        add(tabbedPane, BorderLayout.CENTER);

        loadBieuDoTheoNgay(); // Load mặc định cho ngày đầu
    }
    //bieu do ngay cot
//    private void loadBieuDoTheoNgay() {
//        chartContainer.removeAll();
//        try {
//            ConnectDB.getInstance().connect();
//            Connection conn = ConnectDB.getConnection();
//
//            int selectedDay = Integer.parseInt((String) comboBoxNgay.getSelectedItem());
//
//            Map<String, Integer> data = ThongKe_DAO.getSoLuongSanPhamTheoNgay(conn, selectedDay);
//            if (data.isEmpty()) {
//                chartContainer.add(new JLabel("🔍 Không có dữ liệu."), BorderLayout.CENTER);
//            } else {
//                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//                for (Map.Entry<String, Integer> entry : data.entrySet()) {
//                    dataset.addValue(entry.getValue(), entry.getKey(), "Ngày " + selectedDay);
//                }
//
//                JFreeChart barChart = ChartFactory.createBarChart(
//                        "Sản phẩm bán ra trong ngày " + selectedDay,
//                        "Sản phẩm", "Số lượng", dataset
//                );
//                chartContainer.add(new ChartPanel(barChart), BorderLayout.CENTER);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            chartContainer.add(new JLabel("⚠️ Lỗi khi tải biểu đồ ngày"), BorderLayout.CENTER);
//        }
//        chartContainer.revalidate();
//        chartContainer.repaint();
//    }
    //bieu do ngay tron
    private void loadBieuDoTheoNgay() {
        chartContainer.removeAll();
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getConnection();

            int selectedDay = Integer.parseInt((String) comboBoxNgay.getSelectedItem());

            Map<String, Integer> data = ThongKe_DAO.getSoLuongSanPhamTheoNgay(conn, selectedDay);
            if (data.isEmpty()) {
                chartContainer.add(new JLabel("🔍 Không có dữ liệu."), BorderLayout.CENTER);
            } else {
                // Sử dụng PieDataset
                DefaultPieDataset dataset = new DefaultPieDataset();
                for (Map.Entry<String, Integer> entry : data.entrySet()) {
                    dataset.setValue(entry.getKey() + " - " + entry.getValue(), entry.getValue());
                }

                JFreeChart pieChart = ChartFactory.createPieChart(
                        "Tỉ lệ sản phẩm bán ra trong ngày " + selectedDay,
                        dataset, true, true, false
                );
                chartContainer.add(new ChartPanel(pieChart), BorderLayout.CENTER);
            }

        } catch (Exception e) {
            e.printStackTrace();
            chartContainer.add(new JLabel("⚠️ Lỗi khi tải biểu đồ ngày"), BorderLayout.CENTER);
        }
        chartContainer.revalidate();
        chartContainer.repaint();
    }

    
    private ChartPanel createBieuDoTheoThang() {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getConnection();
            Map<String, Integer> data = ThongKe_DAO.getSoLuongSanPhamBanRa(conn);

            DefaultPieDataset dataset = new DefaultPieDataset();
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                dataset.setValue(entry.getKey() + " - " + entry.getValue(), entry.getValue());
            }

            JFreeChart pieChart = ChartFactory.createPieChart("Tỉ lệ sản phẩm bán ra theo tháng", dataset, true, true, false);
            return new ChartPanel(pieChart);

        } catch (Exception e) {
            e.printStackTrace();
            return new ChartPanel(null);
        }
    }
}








