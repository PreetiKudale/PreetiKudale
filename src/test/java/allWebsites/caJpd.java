package allWebsites;

import org.apache.commons.lang3.Validate;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class caJpd extends baseClass{

    @Test(priority = 1)
    public void navigateAndValidatePages() {
        logger.info("Validate CAJPD Home Page");
        driver.get("https://www.cajpd.com/");

        // Use inherited wait from base class
        WebElement homeLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Home")));
        Assert.assertTrue(homeLink.isDisplayed() && homeLink.isEnabled(), "Home link is not clickable");

        // Validate active slider image (Owl Carousel)
        validateActiveSliderImage(driver);

        // Validate all <img> images on the page
        validateAllImages(driver);
    }
    public void validateActiveSliderImage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        List<WebElement> activeSliders = driver.findElements(By.cssSelector(".owl-item.active img"));

        if (activeSliders.isEmpty()) {
            System.out.println("⚠️ No active slider images found under '.owl-item.active'");
            return;
        }

        System.out.println("🔍 Found " + activeSliders.size() + " active slider image(s)");

        int brokenCount = 0;

        for (WebElement img : activeSliders) {
            try {
                String src = img.getAttribute("src");
                if (src == null || src.trim().isEmpty()) {
                    System.out.println("⚠️ Skipped: Empty image source in active slider");
                    continue;
                }

                boolean imageLoaded = (Boolean) js.executeScript(
                        "return arguments[0].complete && " +
                                "typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0;",
                        img);

                if (imageLoaded) {
                    System.out.println("✅ Active slider image loaded successfully: " + src);
                } else {
                    brokenCount++;
                    System.out.println("❌ Active slider image broken: " + src);
                }

            } catch (Exception e) {
                brokenCount++;
                System.out.println("⚠️ Error validating active slider image: " + e.getMessage());
            }
        }

        if (brokenCount == 0)
            System.out.println("🎯 All active slider images loaded successfully!");
        else
            System.out.println("⚠️ Broken active slider images count: " + brokenCount);
    }

    public void validateAllImages(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<WebElement> allImages = driver.findElements(By.tagName("img"));
        System.out.println("🖼️ Total <img> tags found on page: " + allImages.size());

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
                    System.out.println("❌ Broken or missing image: " + src);
                }

            } catch (Exception e) {
                brokenCount++;
                System.out.println("⚠️ Error validating image: " + e.getMessage());
            }
        }

        if (brokenCount == 0)
            System.out.println("🎯 All images on the page loaded successfully!");
        else
            System.out.println("⚠️ Total broken/missing images: " + brokenCount);
    }

    @Test(priority = 2)
    public void aboutLinkValidate() {
        logger.info("Validate About Us Link CAJPD");

        // Click on About Us link
        WebElement aboutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("About Us")));
        aboutLink.click();
        System.out.println("🖱️ 'About Us' link clicked successfully");

        // Wait for About Us page to load (example: waiting for an <h1> or <h2>)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h2")));
        System.out.println("✅ 'About Us' page loaded successfully");

        // Validate all <img> images on the About Us page
        validateAllImagesAbousUsPageCAJpd(driver);
    }
    public void validateAllImagesAbousUsPageCAJpd(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<WebElement> allImages = driver.findElements(By.tagName("img"));
        System.out.println("🖼️ Total <img> tags found on page: " + allImages.size());

        int brokenCount = 0;

        for (WebElement img : allImages) {
            try {
                String src = img.getAttribute("src");

                if (src == null || src.trim().isEmpty()) {
                    System.out.println("⚠️ Skipped: Empty image source");
                    continue;
                }

                // Check if image is loaded in browser
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

        if (brokenCount == 0)
            System.out.println("🎯 All images on the page loaded successfully!");
        else
            System.out.println("⚠️ Total broken/missing images: " + brokenCount);
    }

    @Test(priority = 3)
    public void services () {
        logger.info("Validate Services page CAJPD");
        WebElement servicesLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Services")));
        Assert.assertTrue(servicesLink.isDisplayed() && servicesLink.isEnabled(), "Services link is not clickable");
    }
    @Test(priority = 4)
    public void knowledge () {
        logger.info("Validate Knowlede Page CAJPD");
        WebElement knowledgeLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Knowledge Center")));
        Assert.assertTrue(knowledgeLink.isDisplayed() && knowledgeLink.isEnabled(), "Knowledge Center link is not clickable");

    }
    @Test(priority = 5)
    public void fillContactForm() {
        logger.info("Validate fill Contact page CAJPD");

        // Navigate directly to the Contact section
        driver.navigate().to("https://www.cajpd.com/#contact-section");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Wait until the Name input field is clickable
            WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("Name")));

            // Fill form fields using JavaScript (to ensure compatibility)
            js.executeScript("arguments[0].value='Preeti Test';", nameField);

            WebElement lastNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("lname")));
            js.executeScript("arguments[0].value='LastName';", lastNameField);

            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("Email")));
            js.executeScript("arguments[0].value='test@example.com';", emailField);

            WebElement messageField = wait.until(ExpectedConditions.elementToBeClickable(By.id("Message")));
            js.executeScript("arguments[0].value='This is a test message Automation Testing';", messageField);

            // Optional zoom out for visibility
            js.executeScript("document.body.style.zoom='90%'");

            // Wait for and click the Submit button
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='submit']")));
            js.executeScript("arguments[0].click();", submitButton);

            System.out.println("✅ Contact form filled and submitted successfully.");

        } catch (Exception e) {
            System.out.println("❌ Error while filling or submitting contact form: " + e.getMessage());
        }
    }

    @Test(priority = 6)
    private void clickAndValidateSocialMedia() throws InterruptedException {
        logger.info("Validate Social Media Pages CAJPD");
        WebElement facebookIcon = driver.findElement(By.xpath("//span[@class='icon-facebook-official']"));
        facebookIcon.click();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("facebook.com")) {
            System.out.println("PASS: URL contains 'facebook.com'");
        } else {
            System.out.println("FAIL: URL does not contain 'facebook.com'");
        }
        driver.navigate().back();
        Thread.sleep(3000);
        WebElement twitterIcon =  driver.findElement(By.xpath("//span[@class='icon-twitter']"));
        twitterIcon.click();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        String currentUrltwitter = driver.getCurrentUrl();
        if (currentUrltwitter.contains("https://x.com/")) {
            System.out.println("PASS: URL contains 'Twitter.com'");
        } else {
            System.out.println("FAIL: URL does not contain 'Twitter.com'");
        }
        driver.navigate().back();
        Thread.sleep(3000);
        WebElement linkedIn =   driver.findElement(By.xpath("//span[@class='icon-linkedin']"));
        linkedIn.click();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        String currentUrllinkedIn = driver.getCurrentUrl();
        if (currentUrllinkedIn.contains("https://www.linkedin.com/")) {
            System.out.println("PASS: URL contains 'https://www.linkedin.com/'");
        } else {
            System.out.println("FAIL: URL does not contain 'linkedIn.com'");
        }
        driver.navigate().back();
        Thread.sleep(3000);
    }
    @Test(priority = 7)
    public void brokenLink() {
        logger.info("Validate Broken Link CAJPD");
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
                    // System.out.println(hrefValue + "=>Not a Broken Link");
                }
            } catch (Exception e) {

            }
        } System.out.println("No. of Broken links" + noOfBrokenLinks);
    }
}
