package allWebsites;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class botmaticSolutionCom extends baseClass {
    @Test(priority = 1)
    public void validateMenuItemsAreClickable() {
        logger.info("Validate Botmatic Solutions Home Page");
        driver.get("https://www.botmaticsolutions.com");
        System.out.println("Opened Botmatic Solutions website");

        // Validate Page Title
        String actualTitle = driver.getTitle();
        if (actualTitle.contains("Botmatic Solution") || actualTitle.contains("1 new message")) {
            System.out.println("✅ Home page title is correct: " + actualTitle);
        } else {
            System.out.println("❌ Home page title validation failed. Found: " + actualTitle);
        }

        // Validate all images (including sliders)
        validateAllImagesOnPage(driver);
    }

    /**
     * Validates all <img> and background-image elements (sliders, banners, etc.)
     * Detects broken links, lazy-loaded images, and slider content backgrounds.
     */
    public void validateAllImagesOnPage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1️⃣ Gather all <img> elements and slider containers
        List<WebElement> imageElements = new ArrayList<>();
        imageElements.addAll(driver.findElements(By.tagName("img")));
        imageElements.addAll(driver.findElements(By.xpath(
                "//div[contains(@class,'slider')]" )));
        System.out.println("🔍 Total image or slider elements found: " + imageElements.size());
        int brokenCount = 0;
        for (WebElement element : imageElements) {
            try {
                // Detect src, data-src, or CSS background
                String src = element.getAttribute("src");
                if (src == null || src.isEmpty()) src = element.getAttribute("data-src");
                if (src == null || src.isEmpty()) src = element.getAttribute("data-bg");

                // Handle CSS background images (sliders, banners)
                if ((src == null || src.isEmpty())) {
                    String bgImage = element.getCssValue("background-image");
                    if (bgImage != null && bgImage.contains("url")) {
                        src = bgImage.substring(bgImage.indexOf("url(") + 4, bgImage.lastIndexOf(")")).replace("\"", "");
                    }
                }

                if (src == null || src.trim().isEmpty()) {
                    System.out.println("⚠️ Skipped: No image source found for element -> " + element);
                    continue;
                }
                // Check if image loaded successfully via JS
                boolean imageLoaded = (Boolean) js.executeScript(
                        "if(arguments[0].tagName.toLowerCase() === 'img') {" +
                                "  return arguments[0].complete && " +
                                "  typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0;" +
                                "} else { return true; }", element);

                // Check via HTTP connection
                int statusCode = getHttpStatusCode(src);

                if (!imageLoaded || statusCode >= 400) {
                    brokenCount++;
                    System.out.println("❌ Broken or missing image: " + src + " [HTTP " + statusCode + "]");
                } else {
                    System.out.println("✅ Image loaded successfully: " + src);
                }

            } catch (Exception e) {
                brokenCount++;
                System.out.println("⚠️ Error validating image: " + e.getMessage());
            }
        }

        System.out.println(brokenCount == 0
                ? "🎯 All images (including sliders) loaded successfully!"
                : "⚠️ Total broken or missing images: " + brokenCount);
    }

    /**
     * Utility to check HTTP response code for a given image URL.
     */
    private int getHttpStatusCode(String imageUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            return connection.getResponseCode();
        } catch (Exception e) {
            return 999; // custom code for "unknown error"
        }
    }

    @Test(priority = 2)
    public void homeLinkValidation() {
        logger.info("Validate Home link in botmatic Solution Com");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement homeLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//a[@class='page-scroll'][normalize-space()='Home']")));
        System.out.println("'Home' is clickable");
    }
    @Test(priority = 3)
    public void AboutUsLinkValidate() {
        logger.info("Validate About Us Link in Botmatic Solution website");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement aboutUsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@class='page-scroll'][normalize-space()='About Us']")));
        aboutUsLink.click();
        System.out.println("🖱️ 'About Us' link clicked successfully");

        // Wait for About Us page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h2")));
        System.out.println("✅ 'About Us' page loaded successfully");

        // Validate all <img> tags on About Us page
        validateAllImagesAboutUsPage(driver);
    }

    /**
     * Generic reusable method to validate all <img> tags on a page.
     * (No status code check — only browser-side validation)
     */
    public void validateAllImagesAboutUsPage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Get all <img> elements
        List<WebElement> allImages = driver.findElements(By.tagName("img"));
        System.out.println("🔍 Total <img> tags found on page: " + allImages.size());

        int brokenCount = 0;

        for (WebElement img : allImages) {
            try {
                String src = img.getAttribute("src");

                // Skip empty src attributes
                if (src == null || src.trim().isEmpty()) {
                    System.out.println("⚠️ Skipped: Empty image source for element " + img);
                    continue;
                }

                // Check if image is properly loaded in browser
                boolean imageLoaded = (Boolean) js.executeScript(
                        "return arguments[0].complete && " +
                                "typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0;",
                        img);

                if (imageLoaded) {
                    System.out.println("✅ Image loaded successfully: " + src);
                } else {
                    brokenCount++;
                    System.out.println("❌ Broken or missing image: " + src);
                }

            } catch (Exception e) {
                brokenCount++;
                System.out.println("⚠️ Error validating image: " + e.getMessage());
            }
        }

        if (brokenCount == 0) {
            System.out.println("🎯 All images on the page loaded successfully!");
        } else {
            System.out.println("⚠️ Total broken/missing images: " + brokenCount);
        }
    }

    @Test(priority = 4)
    public void rpaUseCasesLinkValidation() {
        logger.info("Validate RPA Use Cases Link on Botmatic Solutions website");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Wait for and click on "RPA Use Cases" link
        WebElement rpaUseCasesLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[normalize-space()='RPA Use Cases']")));
        rpaUseCasesLink.click();
        System.out.println("🖱️ 'RPA Use Cases' link clicked successfully");

        // Wait for the RPA Use Cases page to load completely
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h2")));
        System.out.println("✅ 'RPA Use Cases' page loaded successfully");

        // Validate all images on the RPA Use Cases page
        validateAllImagesRPALinkPage(driver);
    }

    /**
     * Generic reusable method to validate all <img> tags on a page.
     * Only checks if images are loaded properly in the browser (no HTTP status check).
     */
    public void validateAllImagesRPALinkPage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Find all <img> elements
        List<WebElement> allImages = driver.findElements(By.tagName("img"));
        System.out.println("🔍 Total <img> tags found on page: " + allImages.size());

        int brokenCount = 0;

        for (WebElement img : allImages) {
            try {
                String src = img.getAttribute("src");

                // Skip empty or null image sources
                if (src == null || src.trim().isEmpty()) {
                    System.out.println("⚠️ Skipped: Empty image source for element " + img);
                    continue;
                }

                // Check image loaded in browser
                boolean imageLoaded = (Boolean) js.executeScript(
                        "return arguments[0].complete && " +
                                "typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0;",
                        img);

                if (imageLoaded) {
                    System.out.println("✅ Image loaded successfully: " + src);
                } else {
                    brokenCount++;
                    System.out.println("❌ Broken or missing image: " + src);
                }

            } catch (Exception e) {
                brokenCount++;
                System.out.println("⚠️ Error validating image: " + e.getMessage());
            }
        }

        // Final summary message
        if (brokenCount == 0) {
            System.out.println("🎯 All images on the page loaded successfully!");
        } else {
            System.out.println("⚠️ Total broken/missing images: " + brokenCount);
        }
    }

 /*  @Test(priority = 5)
    public void contactUsLinkValidation() {
        logger.info("Validate Contact Us link botmatic Solution Com");
        WebElement contactUsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//a[@class='page-scroll'][normalize-space()='Contact Us']")));
        System.out.println("'Contact Us' is clickable");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("Test Name");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("testemail@test.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message"))).sendKeys("Automation test Script message");
        WebElement sendMessage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Send Message']")));
        sendMessage.click();
        WebElement careersLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//a[@class='page-scroll'][normalize-space()='Careers']")));
        System.out.println("'Careers' is clickable");
    }*/

    @Test(priority = 6)
    public void validateProductOpensInNewTab() {
        logger.info("Validate Product Opens in Botmatic Solutions website");

        String originalWindow = driver.getWindowHandle();

        // Wait for and click on 'Products' menu item
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[normalize-space()='Products']")));
        productLink.click();
        System.out.println("🖱️ Clicked on 'Products' link");

        // Wait for new tab to open
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        // Switch to new tab
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        // Validate the URL in the new tab
        String expectedUrl = "https://www.botmaticsolutions.com/Home/TEMS";
        String actualUrl = driver.getCurrentUrl();
        if (actualUrl.equals(expectedUrl)) {
            System.out.println("✅ New tab URL is correct: " + actualUrl);
        } else {
            System.out.println("❌ New tab URL is incorrect: " + actualUrl);
        }

        // Validate all images and videos on this new tab
        validateAllImagesProductTab(driver);
        validateAllVideos(driver);
    }

    public void validateAllImagesProductTab(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<WebElement> allImages = driver.findElements(By.tagName("img"));
        System.out.println("🖼️ Total <img> tags found: " + allImages.size());

        int brokenCount = 0;

        for (WebElement img : allImages) {
            try {
                String src = img.getAttribute("src");
                if (src == null || src.trim().isEmpty()) {
                    System.out.println("⚠️ Skipped: Empty image source");
                    continue;
                }

                boolean imageLoaded = (Boolean) js.executeScript(
                        "return arguments[0].complete && " +
                                "typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0;",
                        img);

                if (imageLoaded) {
                    System.out.println("✅ Image loaded successfully: " + src);
                } else {
                    brokenCount++;
                    System.out.println("❌ Broken image: " + src);
                }

            } catch (Exception e) {
                brokenCount++;
                System.out.println("⚠️ Error validating image: " + e.getMessage());
            }
        }

        if (brokenCount == 0)
            System.out.println("🎯 All images loaded successfully!");
        else
            System.out.println("⚠️ Total broken images: " + brokenCount);
    }

    /**
     * 🎥 Generic method to validate all <video> tags on a page.
     * Checks if the video source is present and playable.
     */
    public void validateAllVideos(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<WebElement> allVideos = driver.findElements(By.tagName("video"));
        System.out.println("🎥 Total <video> tags found: " + allVideos.size());

        int brokenCount = 0;

        for (WebElement video : allVideos) {
            try {
                String src = video.getAttribute("src");
                if (src == null || src.trim().isEmpty()) {
                    // Try <source> child if video src not directly set
                    List<WebElement> sources = video.findElements(By.tagName("source"));
                    if (!sources.isEmpty()) {
                        src = sources.get(0).getAttribute("src");
                    }
                }

                if (src == null || src.trim().isEmpty()) {
                    System.out.println("⚠️ Skipped: No video source found");
                    continue;
                }

                // Check if video metadata is loaded
                boolean videoLoaded = (Boolean) js.executeScript(
                        "return arguments[0].readyState >= 2;", video);

                if (videoLoaded) {
                    System.out.println("✅ Video loaded successfully: " + src);
                } else {
                    brokenCount++;
                    System.out.println("❌ Video failed to load: " + src);
                }

            } catch (Exception e) {
                brokenCount++;
                System.out.println("⚠️ Error validating video: " + e.getMessage());
            }
        }

        if (brokenCount == 0)
            System.out.println("🎯 All videos loaded successfully!");
        else
            System.out.println("⚠️ Total broken videos: " + brokenCount);
    }

    @Test(priority = 7)
    public void brokenLink() {
        logger.info("Validate Broken Link botmatic Solution Com");
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("Number of Links" + links.size());
        int noOfBrokenLinks=0;
        for (WebElement linkElement : links) {
            String hrefValue = linkElement.getAttribute("href");
            if (hrefValue == null || hrefValue.isEmpty()) {
                System.out.println("Not Possible to check");
                continue;
            }
            try {
                URL linkUrl = new URL(hrefValue);
                HttpURLConnection conn = (HttpURLConnection) linkUrl.openConnection();
                conn.connect();
                if (conn.getResponseCode() >= 400) {
                    System.out.println(hrefValue + "===> Link is Broken");
                    noOfBrokenLinks++;
                } else {
                    System.out.println(hrefValue + "=>Not a Broken Link");
                }
            } catch (Exception e) {

            }
        } System.out.println("No. of Broken links" + noOfBrokenLinks);
    }
}