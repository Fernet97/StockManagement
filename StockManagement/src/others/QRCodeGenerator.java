/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.Logger;
import ui.StockManagement;

/**
 *
 * @author Fernet
 */
public class QRCodeGenerator {

    private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";

    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    public static void main(String[] args) {
        try {
            generateQRCodeImage("Miaoooooooo!!!", 350, 350, QR_CODE_IMAGE_PATH);

        } catch (WriterException e) {
            Logger.getLogger("genlog").warning("WriterException:Could not generate QR Code, WriterException:\n" +StockManagement.printStackTrace(e));

        } catch (IOException e) {
            Logger.getLogger("genlog").warning("IOException:Could not generate QR Code, IOException:\n" +StockManagement.printStackTrace(e));

        }
    }

}
