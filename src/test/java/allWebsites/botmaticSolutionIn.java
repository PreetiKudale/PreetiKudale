package allWebsites;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class botmaticSolutionIn extends baseClass {
    @Test(priority = 1)
    public void botmaticSolutionsInOpen() {
        logger.info("Validate Botmatic Solutions (India) Home Page");
        driver.get("https://www.botmaticsolution.in");
        System.out.println("Opened Botmatic Solutions website");

        // Validate Page Title
        String actualTitle = driver.getTitle();
        if (actualTitle.contains("Home - MyVyay") || actualTitle.contains("1 new message")) {
            System.out.println("✅ Home page title is correct: " + actualTitle);
        } else {
            System.out.println("❌ Home page title validation failed. Found: " + actualTitle);
        }

        // Validate All Images
        validateAllImagesOnPage(driver);
    }

    /**
     * Generic reusable method to validate all images (including slider/background images)
     */
    public void validateAllImagesOnPage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Collect all <img> elements
        List<WebElement> allImages = new ArrayList<>(driver.findElements(By.tagName("img")));

        // Include elements that may have background images (like sliders, banners, hero sections)
        allImages.addAll(driver.findElements(By.xpath(
                "//div[contains(@class,'slider') or contains(@class,'nivo-caption') or contains(@class,'banner') or contains(@style,'background-image')]"
        )));

        System.out.println("🔍 Total image/slider elements found: " + allImages.size());
        int brokenCount = 0;

        for (WebElement element : allImages) {
            try {
                String src = element.getAttribute("src");

                // Handle CSS background images
                if (src == null || src.isEmpty()) {
                    String bgImage = element.getCssValue("background-image");
                    if (bgImage != null && bgImage.contains("url")) {
                        src = bgImage.substring(bgImage.indexOf("url(") + 4, bgImage.lastIndexOf(")")).replace("\"", "");
                    }
                }

                // Check if <img> is fully loaded using JS
                Boolean imageLoaded = (Boolean) js.executeScript(
                        "if(arguments[0].tagName.toLowerCase() === 'img') {" +
                                "  return arguments[0].complete && " +
                                "  typeof arguments[0].naturalWidth != 'undefined' && " +
                                "  arguments[0].naturalWidth > 0;" +
                                "} else { return true; }", element);

                if (src == null || src.isEmpty() || !imageLoaded) {
                    brokenCount++;
                    System.out.println("❌ Broken or missing image: " + src);
                } else {
                    System.out.println("✅ Image loaded successfully: " + src);
                }

            } catch (Exception e) {
                System.out.println("⚠️ Error validating image: " + e.getMessage());
            }
        }

        if (brokenCount == 0) {
            System.out.println("🎯 All images on the homepage loaded successfully!");
        } else {
            System.out.println("⚠️ Total broken or missing images: " + brokenCount);
        }
    }
}
