package UI;

import java.awt.*;
import java.awt.print.*;
import javax.swing.*;

public class InHoaDon {

    public static void printPanel(JPanel panel) {
        PrinterJob printerJob = PrinterJob.getPrinterJob();

        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

                Dimension panelSize = panel.getPreferredSize();

                Paper paper = new Paper();
                double width = panelSize.getWidth();
                double height = panelSize.getHeight();

                paper.setSize(width, height); 
                paper.setImageableArea(0, 0, width, height); 
                pageFormat.setPaper(paper);

 
                Graphics2D g2d = (Graphics2D) g;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                panel.printAll(g2d);

                return Printable.PAGE_EXISTS;
            }
        });

        boolean doPrint = printerJob.printDialog();
        if (doPrint) {
            try {
                printerJob.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panel, "In thất bại: " + ex.getMessage());
            }
        }
    }
}