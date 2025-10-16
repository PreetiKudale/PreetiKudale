package allWebsites;

import Utility.ExtentReportManager;
import Utility.HtmlToPdfConverter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

public class baseClass {
        public WebDriver driver;
        public WebDriverWait wait;
        public ExtentReports extent;
        public ExtentTest test;
        public Logger logger;

        @BeforeSuite
        public void clearLogFile() {
            try {
                java.io.File logDir = new java.io.File("logs");
                if (!logDir.exists()) {
                    logDir.mkdirs();
                }
                FileWriter fw = new FileWriter("logs/allWebsites.log", false);
                fw.write("");
                fw.close();
                System.out.println("Log4j log file cleared.");
            } catch (IOException e) {
                System.err.println("Failed to clear log file: " + e.getMessage());
            }
        }
        @BeforeMethod
        public void startTest(Method method) {
            test = extent.createTest(method.getName());  // 💡 Log test name dynamically
        }

        @AfterMethod
        public void logResult(ITestResult result) {
            if (result.getStatus() == ITestResult.FAILURE) {
                test.fail(result.getThrowable());
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                test.pass("Test Passed");
            } else {
                test.skip("Test Skipped");
            }
        }
        @BeforeClass
        @Parameters({"os", "Browser"})
        public void setUp(String os, String br) {
            logger = LogManager.getLogger(this.getClass());
            extent = ExtentReportManager.getInstance();

            switch (br.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless=new"); // For Chrome v109+, use "--headless=new"
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--headless=new"); // New headless mode for Edge Chromium
                    edgeOptions.addArguments("--disable-gpu");
                    edgeOptions.addArguments("--window-size=1920,1080");
                    driver = new EdgeDriver(edgeOptions);
                    break;

                default:
                    System.out.println("Invalid Browser");
                    return;
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.manage().deleteAllCookies();
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        }

        @AfterClass
        public void tearDown() {
            driver.quit();
        }

        @AfterSuite
        public void tearDownReport() {
            ExtentReportManager.getInstance().flush();
            try { Thread.sleep(3000); } catch (InterruptedException e) {}
            HtmlToPdfConverter.convertToPdf(
                    "test-output/ExtentReport.html",
                    "test-output/ExtentReport.pdf"
            );
        }
    }
