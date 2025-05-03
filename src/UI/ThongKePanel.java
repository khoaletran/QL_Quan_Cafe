package UI;

import ConnectDB.ConnectDB;
import Dao.ThongKe_DAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Map;

public class ThongKePanel extends JPanel {

    private JComboBox<String> comboBoxThang;
    private JComboBox<String> comboBoxSanPham;
    private JPanel panelLineChartContainer;

    private JComboBox<String> comboBoxThangSanPhamThang;
    private JPanel panelPieChartContainer;

    public ThongKePanel() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab biểu đồ đường
        JPanel panelLine = new JPanel(new BorderLayout());
        JPanel filterPanel = new JPanel();
        comboBoxThang = new JComboBox<>();
        comboBoxSanPham = new JComboBox<>();

        for (int i = 1; i <= 12; i++) {
            comboBoxThang.addItem(String.format("%02d", i));
        }

        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getConnection();

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

        comboBoxThang.addActionListener(e -> updateLineChart());
        comboBoxSanPham.addActionListener(e -> updateLineChart());

        panelLineChartContainer = new JPanel(new BorderLayout());
        panelLineChartContainer.add(createLineChartTheoNgay(), BorderLayout.CENTER);

        panelLine.add(filterPanel, BorderLayout.NORTH);
        panelLine.add(panelLineChartContainer, BorderLayout.CENTER);

        tabbedPane.addTab("Line Chart Theo Ngày", panelLine);

        // Tab số lượng sản phẩm theo tháng
        JPanel panelThang = new JPanel(new BorderLayout());

        JPanel filterThangPanel = new JPanel();
        comboBoxThangSanPhamThang = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            comboBoxThangSanPhamThang.addItem(String.format("%02d", i));
        }
        filterThangPanel.add(new JLabel("Chọn tháng:"));
        filterThangPanel.add(comboBoxThangSanPhamThang);

        panelPieChartContainer = new JPanel(new BorderLayout());
        panelPieChartContainer.add(createBieuDoTheoThang(), BorderLayout.CENTER);

        comboBoxThangSanPhamThang.addActionListener(e -> updateBieuDoTheoThang());

        panelThang.add(filterThangPanel, BorderLayout.NORTH);
        panelThang.add(panelPieChartContainer, BorderLayout.CENTER);

        tabbedPane.addTab("Số Lượng Sản Phẩm Theo tháng", panelThang);

        // Tab doanh thu theo tháng
        JPanel panelDoanhThu = new JPanel(new BorderLayout());
        panelDoanhThu.add(createBieuDoDoanhThuThang(), BorderLayout.CENTER);
        tabbedPane.addTab("Doanh thu theo tháng", panelDoanhThu);

        add(tabbedPane, BorderLayout.CENTER);
    }

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

            int selectedThang = comboBoxThangSanPhamThang.getSelectedIndex() + 1;
            Map<String, Integer> data = ThongKe_DAO.getSoLuongSanPhamTheoThang(conn, selectedThang);

            DefaultPieDataset dataset = new DefaultPieDataset();
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                dataset.setValue(entry.getKey() + " - " + entry.getValue(), entry.getValue());
            }

            JFreeChart pieChart = ChartFactory.createPieChart(
                    "Tỉ lệ sản phẩm bán ra trong tháng " + selectedThang,
                    dataset, true, true, false
            );

            return new ChartPanel(pieChart);

        } catch (Exception e) {
            e.printStackTrace();
            return new ChartPanel(null);
        }
    }

    private void updateBieuDoTheoThang() {
        panelPieChartContainer.removeAll();
        panelPieChartContainer.add(createBieuDoTheoThang(), BorderLayout.CENTER);
        panelPieChartContainer.revalidate();
        panelPieChartContainer.repaint();
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

            // Format trục Y theo tiền Việt
            NumberAxis rangeAxis = (NumberAxis) barChart.getCategoryPlot().getRangeAxis();
            rangeAxis.setNumberFormatOverride(new DecimalFormat("#,### VNĐ"));

            return new ChartPanel(barChart);

        } catch (Exception e) {
            e.printStackTrace();
            return new ChartPanel(null);
        }
    }

}
