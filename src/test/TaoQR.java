package test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class TaoQR {

    public static void main(String[] args) {
        try {
            String content = "MGG50";  // Nội dung QR bạn muốn tạo
            String filePath = "MGG50.png";  // Đường dẫn lưu ảnh QR

            generateQRCode(content, filePath);
            System.out.println("QR code generated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateQRCode(String content, String filePath) throws Exception {
        // Đặt các tùy chọn mã hóa
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");


        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);


        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        // Lưu ảnh vào file
        ImageIO.write(image, "PNG", new File(filePath));
    }
}
