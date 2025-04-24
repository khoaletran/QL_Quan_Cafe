package UI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class test extends JFrame {

    public test() {
        setTitle("Biểu đồ doanh thu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dataset: Doanh thu theo tháng
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(12000000, "Doanh thu", "Tháng 1");
        dataset.addValue(15000000, "Doanh thu", "Tháng 2");
        dataset.addValue(10000000, "Doanh thu", "Tháng 3");
        dataset.addValue(17000000, "Doanh thu", "Tháng 4");

        // Tạo biểu đồ
        JFreeChart chart = ChartFactory.createBarChart(
                "Thống kê doanh thu theo tháng",
                "Tháng",
                "Doanh thu (VNĐ)",
                dataset
        );

        // Thêm biểu đồ vào JFrame
        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            test frame = new test();
            frame.setVisible(true);
        });
    }
}
