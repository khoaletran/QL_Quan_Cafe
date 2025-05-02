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

    // combobox và biểu đồ
    private JComboBox<String> comboBoxThang;
    private JComboBox<String> comboBoxSanPham;
    private JPanel panelLineChartContainer;

    public ThongKePanel() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        //Tab biểu đồ đường thêm ComboBox lọc
        JPanel panelLine = new JPanel(new BorderLayout());

        JPanel filterPanel = new JPanel();
        comboBoxThang = new JComboBox<>();
        comboBoxSanPham = new JComboBox<>();

        for (int i = 1; i <= 12; i++) {
            comboBoxThang.addItem(String.format("%02d", i));
        }

        try {
            // [SỬA] Chỉ kết nối DB 1 lần và load danh sách sản phẩm
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getConnection();

            // [SỬA] Thêm "Tất cả" vào đầu danh sách sản phẩm
            comboBoxSanPham.addItem("Tất cả");

            java.util.List<String> dsSanPham = ThongKe_DAO.getDanhSachTenSanPham(conn);
            for (String tenSP : dsSanPham) {
                comboBoxSanPham.addItem(tenSP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        filterPanel.add(new JLabel("Tháng:"));
        filterPanel.add(comboBoxThang);
        filterPanel.add(new JLabel("Sản phẩm:"));
        filterPanel.add(comboBoxSanPham);

        // [THÊM] Lắng nghe sự kiện thay đổi
        comboBoxThang.addActionListener(e -> updateLineChart());
        comboBoxSanPham.addActionListener(e -> updateLineChart());

        panelLineChartContainer = new JPanel(new BorderLayout());
        panelLineChartContainer.add(createLineChartTheoNgay(), BorderLayout.CENTER);

        panelLine.add(filterPanel, BorderLayout.NORTH);
        panelLine.add(panelLineChartContainer, BorderLayout.CENTER);

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
    }

    // [SỬA] Load biểu đồ theo lựa chọn combobox
    private ChartPanel createLineChartTheoNgay() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getConnection();

            String selectedThang = (String) comboBoxThang.getSelectedItem();
            String selectedSP = (String) comboBoxSanPham.getSelectedItem();

            if (selectedThang == null || selectedSP == null) return new ChartPanel(null);

            Map<String, Map<String, Integer>> data = ThongKe_DAO.getSoLuongTungLoaiSanPhamTheoNgay(conn, selectedThang);

            if (selectedSP.equals("Tất cả")) {
                // [SỬA] Vẽ toàn bộ sản phẩm nếu chọn "Tất cả"
                for (Map.Entry<String, Map<String, Integer>> entry : data.entrySet()) {
                    String tenSP = entry.getKey();
                    Map<String, Integer> ngayData = entry.getValue();

                    for (int i = 1; i <= 31; i++) {
                        String ngay = String.format("%02d", i);
                        int soLuong = ngayData.getOrDefault(ngay, 0);
                        dataset.addValue(soLuong, tenSP, ngay);
                    }
                }
            } else {
                // [SỬA] Chỉ vẽ sản phẩm được chọn
                if (data.containsKey(selectedSP)) {
                    Map<String, Integer> ngayData = data.get(selectedSP);
                    for (int i = 1; i <= 31; i++) {
                        String ngay = String.format("%02d", i);
                        int soLuong = ngayData.getOrDefault(ngay, 0);
                        dataset.addValue(soLuong, selectedSP, ngay);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Số lượng sản phẩm bán ra theo ngày",
                "Ngày", "Số lượng", dataset
        );

        return new ChartPanel(lineChart);
    }

    // [THÊM] Hàm cập nhật biểu đồ khi chọn ComboBox
    private void updateLineChart() {
        panelLineChartContainer.removeAll();
        panelLineChartContainer.add(createLineChartTheoNgay(), BorderLayout.CENTER);
        panelLineChartContainer.revalidate();
        panelLineChartContainer.repaint();
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
