package Utility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String capture(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String destPath = "test-output/screenshots/" + testName + "_" + timestamp + ".png";

            File destFile = new File(destPath);
            destFile.getParentFile().mkdirs(); // Create directories if not exist
            Files.copy(src.toPath(), destFile.toPath());

            return destPath;
        } catch (IOException e) {
            System.err.println("Screenshot capture failed: " + e.getMessage());
            return null;
        }
    }
}
