package allWebsites;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.openqa.selenium.support.ui.Select;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class claimMyShares extends baseClass {

    @Test(priority = 1)
    public void openHomePageAndValidateTitle() {
        logger.info("Validate Claim My Shares Home Page");
        driver.get("https://claimmyshares.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String expectedTitle = "Unclaimed Investment | IEPF Service";
        String actualTitle = driver.getTitle();
        if (actualTitle.contains(expectedTitle)) {
            System.out.println("✅ Home page title is correct: " + actualTitle);
        } else {
            System.out.println("❌ Home page title validation failed. Found: " + actualTitle);
        }
        List<WebElement> images = driver.findElements(By.tagName("img"));
        System.out.println("Total images found on page: " + images.size());

        int brokenImages = 0;

        for (WebElement img : images) {
            try {
                Boolean imageLoaded = (Boolean) js.executeScript(
                        "return arguments[0].complete && " +
                                "typeof arguments[0].naturalWidth != 'undefined' && " +
                                "arguments[0].naturalWidth > 0;", img);

                if (!imageLoaded) {
                    brokenImages++;
                    System.out.println("❌ Broken image found: " + img.getAttribute("src"));
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error checking image: " + img.getAttribute("src"));
            }
        }

        if (brokenImages == 0) {
            System.out.println("✅ All static images loaded successfully!");
        } else {
            System.out.println("⚠️ Total broken images found: " + brokenImages);
        }
        try {
            List<WebElement> sliderImages = driver.findElements(
                    By.xpath("//div[contains(@class,'elementor-repeater-item-5a02144') and contains(@class,'swiper-slide')]//img")
            );

            System.out.println("🎞️ Total slider images found: " + sliderImages.size());
            int brokenSliderImages = 0;

            for (WebElement sliderImg : sliderImages) {
                try {
                    Boolean imageLoaded = (Boolean) js.executeScript(
                            "return arguments[0].complete && " +
                                    "typeof arguments[0].naturalWidth != 'undefined' && " +
                                    "arguments[0].naturalWidth > 0;", sliderImg);

                    if (!imageLoaded) {
                        brokenSliderImages++;
                        System.out.println("❌ Broken slider image: " + sliderImg.getAttribute("src"));
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Error checking slider image: " + sliderImg.getAttribute("src"));
                }
            }

            if (brokenSliderImages == 0) {
                System.out.println("✅ All slider images loaded successfully!");
            } else {
                System.out.println("⚠️ Total broken slider images: " + brokenSliderImages);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error locating slider images: " + e.getMessage());
        }

        System.out.println("🏁 Home Page and Slider Image Validation Completed Successfully.");
    }

    @Test(priority = 2)
    public void validateHomeLinkClickable() {
        logger.info("Validate home link Claim My Shares");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Validate and click home link
        WebElement homeLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[normalize-space()='HOME'])")));
        Assert.assertTrue(homeLink.isDisplayed() && homeLink.isEnabled(), "Home link not clickable");
        homeLink.click();
    }
    @Test(priority = 3)
    public void validateAllImagesOnServicePages() {
        logger.info("Validating all images on multiple service pages");

        String[] serviceUrls = {
                "https://claimmyshares.com/demat-of-shares/",
                "https://claimmyshares.com/transmission-of-shares/",
                "https://claimmyshares.com/address-change/",
                "https://claimmyshares.com/succession-certificate/",
                "https://claimmyshares.com/duplicate-shares/",
                "https://claimmyshares.com/probate/",
                "https://claimmyshares.com/iepf/"
        };

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String url : serviceUrls) {
            System.out.println("\n🔍 Checking page: " + url);
            driver.get(url);

            try {
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("img")));
                List<WebElement> imagesList = driver.findElements(By.tagName("img"));
                System.out.println("Total images found on " + url + ": " + imagesList.size());

                for (WebElement imgElement : imagesList) {
                    String imageURL = imgElement.getAttribute("src");

                    if (imageURL == null || imageURL.isEmpty()) {
                        System.out.println("❌ Image source missing on: " + url);
                        continue;
                    }

                    try {
                        HttpURLConnection connection = (HttpURLConnection) (new URL(imageURL)).openConnection();
                        connection.setRequestMethod("HEAD");
                        connection.connect();
                        int responseCode = connection.getResponseCode();

                        if (responseCode >= 400) {
                            System.out.println("❌ Broken image: " + imageURL + " | Response Code: " + responseCode);
                        } else {
                            System.out.println("✅ Valid image: " + imageURL);
                        }
                    } catch (Exception e) {
                        System.out.println("⚠️ Error checking image: " + imageURL + " | " + e.getMessage());
                    }
                }

            } catch (Exception e) {
                System.out.println("⚠️ Exception while loading " + url + ": " + e.getMessage());
            }
        }
    }

   /* @Test(priority = 4)
    public void fillContactForm() throws InterruptedException {
        logger.info("Validate Contact Form claim My Shares");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement contactUsLink = driver.findElement(By.xpath("//a[normalize-space()='CONTACT US']"));
        contactUsLink.click();
        Thread.sleep(3000);
        WebElement nameField = driver.findElement(By.xpath("//input[@id='form-field-name']"));
        js.executeScript("arguments[0].scrollIntoView(true);", nameField);
        nameField.sendKeys("Preeti Test");
        WebElement emailField = driver.findElement(By.xpath("//input[@id='form-field-email']"));
        js.executeScript("arguments[0].scrollIntoView(true);", emailField);
        emailField.sendKeys("test@example.com");
        WebElement subjectDropdown = driver.findElement(By.xpath("(//select[@id='form-field-field_4d59bc2'])[1]"));
        js.executeScript("arguments[0].scrollIntoView(true);", subjectDropdown);
        Select select = new Select(subjectDropdown);
        select.selectByVisibleText("Lost Shares");
        WebElement message = driver.findElement(By.xpath("//textarea[@id='form-field-message']"));
        js.executeScript("arguments[0].scrollIntoView(true);", message);
        message.sendKeys("This is a test message.");
        WebElement submit = driver.findElement(By.xpath("//span[contains(text(),'Submit')]"));
        js.executeScript("arguments[0].scrollIntoView(true);", submit);
        submit.submit();

    }*/
    @Test(priority = 5)
    public void validateAboutUsLinkClickable() throws InterruptedException {
        logger.info("Validate About Us in Claim My Shares");

        driver.get("https://claimmyshares.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait and click on About Us link
        WebElement aboutUsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[normalize-space()='ABOUT US']")));
        aboutUsLink.click();
        logger.info("Clicked on About Us link");

        // Wait for page load
        Thread.sleep(5000);

        // Validate all visible images
        List<WebElement> images = driver.findElements(By.tagName("img"));
        logger.info("Total images found on About Us page: " + images.size());

        for (WebElement img : images) {
            String imgSrc = img.getAttribute("src");

            // Skip null or empty src attributes
            if (imgSrc == null || imgSrc.trim().isEmpty()) {
                System.out.println("⚠️ Skipping image with empty src attribute.");
                continue;
            }

            // Skip hidden or offscreen images (like cookie plugin icons)
            if (!img.isDisplayed()) {
                System.out.println("ℹ️ Skipping hidden image: " + imgSrc);
                continue;
            }

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(imgSrc).openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();

                if (responseCode != 200) {
                    Assert.fail("❌ Broken image: " + imgSrc + " | Response Code: " + responseCode);
                } else {
                    System.out.println("✅ Valid image: " + imgSrc);
                }

                connection.disconnect();
            } catch (Exception e) {
                Assert.fail("⚠️ Exception while validating image: " + imgSrc + " | " + e.getMessage());
            }
        }

        logger.info("✅ All visible images on About Us page are valid and displayed properly.");
    }

    @Test(priority = 6)
    public void validateFAQLinkClickable() {
        logger.info("Validate FAQ link clickability and page title on Claim My Shares");
        try {
            WebElement faqLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[normalize-space()='FAQ']")));
            Assert.assertTrue(faqLink.isDisplayed() && faqLink.isEnabled(),
                    "❌ FAQ link is not visible or not enabled");
            faqLink.click();
            logger.info("Clicked on FAQ link.");
            wait.until(ExpectedConditions.titleIs("FAQ"));
            String expectedTitle = "FAQ";
            String actualTitle = driver.getTitle();
            Assert.assertEquals(actualTitle, expectedTitle,
                    "❌ Page title mismatch after clicking FAQ link!");

            System.out.println("✅ FAQ link clicked successfully, and page title validated: " + actualTitle);
        } catch (Exception e) {
            System.out.println("⚠️ Exception while validating FAQ link: " + e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }


    @Test(priority = 7)
    public void brokenLink() {
        logger.info("Validate Broken Link claim My Shares");
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("Number of Links" + links.size());
        int noOfBrokenLinks = 0;
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
                    //System.out.println(hrefValue + "=>Not a Broken Link");
                }
            } catch (Exception e) {

            }
        }
        System.out.println("No. of Broken links" + noOfBrokenLinks);
    }
}