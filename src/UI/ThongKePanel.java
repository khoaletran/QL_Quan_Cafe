package UI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class ThongKePanel extends JPanel {

    public ThongKePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));  // Chọn màu nền cho panel
        createChartPanel();
    }

    private void createChartPanel() {
        // Dataset: Doanh thu theo tháng
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(12500000, "Doanh thu", "10/2024");
        dataset.addValue(11000000, "Doanh thu", "11/2024");
        dataset.addValue(10500000, "Doanh thu", "12/2024");
        dataset.addValue(11000000, "Doanh thu", "01/2025");
        dataset.addValue(8500000, "Doanh thu", "02/2025");
        dataset.addValue(9000000, "Doanh thu", "03/2025");
        dataset.addValue(10000000, "Doanh thu", "04/2025");
        dataset.addValue(2000000, "Doanh thu", "05/2025");
        dataset.addValue(0, "Doanh thu", "06/2025");
        dataset.addValue(0, "Doanh thu", "07/2025");
        dataset.addValue(0, "Doanh thu", "08/2025");

        // Tạo biểu đồ
        JFreeChart chart = ChartFactory.createBarChart(
                "Thống kê doanh thu theo tháng",
                "Tháng",
                "Doanh thu (VNĐ)",
                dataset
        );

        // Thêm biểu đồ vào một panel mới
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }
}
