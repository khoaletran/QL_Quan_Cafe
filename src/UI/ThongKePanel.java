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
    public ThongKePanel() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();


     // Tab biểu đồ đường
        JPanel panelLine = new JPanel(new BorderLayout());
        panelLine.add(createLineChartTheoNgay(), BorderLayout.CENTER);
        tabbedPane.addTab("Line Chart Theo Ngày", panelLine);


        // Tab thống kê theo tháng
        JPanel panelThang = new JPanel(new BorderLayout());
        panelThang.add(createBieuDoTheoThang(), BorderLayout.CENTER);
        tabbedPane.addTab("Số Lượng Sản Phẩm Theo tháng", panelThang);
        
     // Tab thống kê doanh thu theo tháng
        JPanel panelDoanhThu = new JPanel(new BorderLayout());
        panelDoanhThu.add(createBieuDoDoanhThuThang(), BorderLayout.CENTER);
        tabbedPane.addTab("Doanh thu theo tháng", panelDoanhThu);


        add(tabbedPane, BorderLayout.CENTER);

        createLineChartTheoNgay(); // Load mặc định cho ngày đầu
    }
    
    private ChartPanel createLineChartTheoNgay() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getConnection();

            Map<String, Map<String, Integer>> data = ThongKe_DAO.getSoLuongTungLoaiSanPhamTheoNgay(conn);

            for (Map.Entry<String, Map<String, Integer>> spEntry : data.entrySet()) {
                String tenSanPham = spEntry.getKey();
                Map<String, Integer> ngayData = spEntry.getValue();

                for (int i = 1; i <= 31; i++) {
                    String ngay = String.format("%02d", i);
                    int soLuong = ngayData.getOrDefault(ngay, 0);
                    dataset.addValue(soLuong, tenSanPham, ngay);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Số lượng sản phẩm bán ra theo từng ngày trong tháng",
                "Ngày", "Số lượng", dataset
        );
        
        

        return new ChartPanel(lineChart);
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
    
    private ChartPanel createBieuDoDoanhThuThang() {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getConnection();
            Map<String, Double> data = ThongKe_DAO.getDoanhThuTheoThang(conn);

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                dataset.addValue(entry.getValue(), "Doanh thu", entry.getKey());
            }

            JFreeChart barChart = ChartFactory.createBarChart(
                    "Doanh thu theo tháng trong năm " + LocalDate.now().getYear(),
                    "Tháng", "Doanh thu (VNĐ)", dataset
            );

            return new ChartPanel(barChart);

        } catch (Exception e) {
            e.printStackTrace();
            return new ChartPanel(null);
        }
    }

}









