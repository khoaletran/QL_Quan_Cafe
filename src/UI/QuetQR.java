package UI;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import Bien.BIEN;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class QuetQR {

    private static volatile boolean found = false;

    public static String scanQRCode() {
        Webcam webcam = Webcam.getDefault();
        if (webcam == null) {
            System.out.println("❌ Không tìm thấy webcam nào!");
            return null;
        }

        webcam.setViewSize(new Dimension(640, 480));
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setMirrored(true);

        JFrame frame = new JFrame("Quét mã QR - " + BIEN.TENQUAN);
        frame.setIconImage(BIEN.LOGO_QUAN.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        try {
            if (!webcam.isOpen()) {
                webcam.open();
            }

            System.out.println("📷 Webcam đã mở. Đưa mã QR vào camera...");

            long startTime = System.currentTimeMillis();
            long timeout = 10_000;

            while (!found && (System.currentTimeMillis() - startTime < timeout)) {
                BufferedImage image = webcam.getImage();
                if (image == null) continue;

                try {
                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result result = new MultiFormatReader().decode(bitmap);

                    if (result != null) {
                        System.out.println("✅ Mã QR đọc được: " + result.getText());
                        found = true;
                        return result.getText();
                    }
                } catch (NotFoundException e) {
                }
            }

            if (!found) {
                System.out.println("⌛ Hết thời gian! Không quét được mã QR trong 10 giây.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (webcam.isOpen()) webcam.close();
            frame.dispose();
            System.out.println("📷 Webcam đã đóng.");
            found = false;
        }

        return null;
    }

}
