package Utility;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class HtmlToPdfConverter {

    public static void convertToPdf(String htmlFilePath, String outputPdfPath) {
        try (OutputStream os = new FileOutputStream(outputPdfPath)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withUri(new File(htmlFilePath).toURI().toString());
            builder.toStream(os);
            builder.run();

            System.out.println("✅ PDF report created at: " + outputPdfPath);
        } catch (Exception e) {
            System.err.println("❌ PDF conversion failed: " + e.getMessage());
        }
    }
}
