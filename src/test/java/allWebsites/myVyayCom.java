package allWebsites;

import org.apache.logging.log4j.core.net.Priority;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class myVyayCom extends baseClass {

    @Test(priority = 1)
    public void validateDashboardMenusAndContactForm() {
        logger.info("Validate myVyayCom");
        driver.get("https://myvyay.com/");
        System.out.println("Opened myvaya.com");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            WebElement rejectButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Reject All') or contains(text(),'Reject all')]")));
            rejectButton.click();
            System.out.println("Rejected cookies successfully.");
        } catch (TimeoutException e) {
            System.out.println("No cookie banner appeared.");
        }

        String actualTitle = driver.getTitle();
        if (actualTitle.contains("Botmatic Solution") || actualTitle.contains("1 new message")) {
            System.out.println("Home page title is correct: " + actualTitle);
        } else {
            System.out.println("Home page title validation failed. Found: " + actualTitle);
        }
    }

    @Test(priority = 2)
    public void validateProductLinks() {
        logger.info("Validate Product Link");
        Actions actions = new Actions(driver);
        WebElement productMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Product']")));
        actions.moveToElement(productMenu).perform();

        /*List<WebElement> productLinks = driver.findElements(By.xpath("//a[contains(@href,'/product')]"));
        for (WebElement link : productLinks) {
            Assert.assertTrue(link.isDisplayed(), "Link is not visible: " + link.getText());
            Assert.assertTrue(link.isEnabled(), "Link is not clickable: " + link.getText());
            System.out.println("Product Link Validated: " + link.getText());
        }*/
    }

    @Test(priority = 3)
    public void validateServicesPageImages() {
        String url = "https://myvyay.com/services/";
        driver.get(url);
        System.out.println("Opened URL: " + url);

        // Optionally validate page title or some unique element
        String title = driver.getTitle();
        System.out.println("Page title: " + title);

        validateImagesWithLinksOnPage(driver);
    }

    public void validateImagesWithLinksOnPage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Collect all <img> tags
        List<WebElement> imgTags = driver.findElements(By.tagName("img"));

        // 2. Collect elements that might use CSS background-image
        List<WebElement> bgImageElements = driver.findElements(By.xpath(
                "//*[contains(@style,'background-image') or contains(@class,'slider') or contains(@class,'banner')]"
        ));

        // Merge both lists, avoiding duplicates
        Set<WebElement> allElements = new LinkedHashSet<>();
        allElements.addAll(imgTags);
        allElements.addAll(bgImageElements);

        System.out.println("Total image / background image elements found: " + allElements.size());

        int brokenCount = 0;

        for (WebElement elem : allElements) {
            String src = "";
            try {
                // 3. Try get src (for <img>)
                src = elem.getAttribute("src");

                // If no src / empty, try CSS background-image
                if (src == null || src.isEmpty()) {
                    String bg = elem.getCssValue("background-image");
                    if (bg != null && bg.contains("url")) {
                        // Extract URL from e.g. url("…")
                        src = bg.substring(bg.indexOf("url(") + 4, bg.lastIndexOf(")")).replace("\"", "").replace("'", "");
                    }
                }

                // 4. Validate image is loaded if it's an <img>
                boolean imageOk = true;
                if ("img".equalsIgnoreCase(elem.getTagName())) {
                    imageOk = (Boolean) js.executeScript(
                            "return arguments[0].complete && " +
                                    "typeof arguments[0].naturalWidth != 'undefined' && " +
                                    "arguments[0].naturalWidth > 0;", elem);
                }
                // For background elements, we assume that if src is nonempty, it might be valid (or further HTTP check)

                if (src == null || src.isEmpty() || !imageOk) {
                    brokenCount++;
                    System.out.println("❌ Broken or missing image: " + src);
                } else {
                    System.out.println("✅ Loaded image: " + src);
                }

                // 5. If the image (or background) is inside a link, print the link href
                WebElement parent = null;
                try {
                    parent = elem.findElement(By.xpath("ancestor::a[1]"));
                } catch (NoSuchElementException nse) {
                    parent = null;
                }
                if (parent != null) {
                    String href = parent.getAttribute("href");
                    System.out.println("-- this image is inside link: " + href);
                }

            } catch (Exception e) {
                System.out.println("⚠ Exception checking element. src = " + src + " — " + e.getMessage());
            }
        }

        System.out.println("Total broken images (or invalid): " + brokenCount);
    }

    @Test(priority = 4)
    public void validateResourcesLinks() {
        logger.info("Validate Resource link myVyayCom");
        Actions actions = new Actions(driver);
        WebElement resourcesMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Resources']")));
        actions.moveToElement(resourcesMenu).perform();

        List<WebElement> resourceLinks = driver.findElements(By.xpath("//a[contains(@href,'/resources')]"));
        for (WebElement link : resourceLinks) {
            Assert.assertTrue(link.isDisplayed(), "Resource link not visible: " + link.getText());
            Assert.assertTrue(link.isEnabled(), "Resource link not clickable: " + link.getText());
            System.out.println("Resource Link Validated: " + link.getText());
        }
    }

    @Test(priority = 5)
    public void validateAboutUsPageImages() {
        logger.info("Validate all images on About Us page");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String url = "https://myvyay.com/about-us/";
        driver.get(url);
        System.out.println("Clicked on About Us menu");
        // 3️⃣ Wait until URL contains "about-us"
        wait.until(ExpectedConditions.urlContains("/about-us"));
        System.out.println("Navigated to: " + driver.getCurrentUrl());
        // 4️⃣ Validate all images on About Us page
        validateAllImagesOnPage(driver);
    }
    public void validateAllImagesOnPage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Collect all image and background elements
        List<WebElement> images = new ArrayList<>(driver.findElements(By.tagName("img")));
        images.addAll(driver.findElements(By.xpath(
                "//*[contains(@style,'background-image') or contains(@class,'slider') or contains(@class,'banner')]"
        )));

        System.out.println("🔍 Total image/background elements found: " + images.size());
        int brokenCount = 0;

        for (WebElement img : images) {
            String src = null;

            try {
                // Extract src or background-image URL
                src = img.getAttribute("src");
                if (src == null || src.isEmpty()) {
                    String bgImage = img.getCssValue("background-image");
                    if (bgImage != null && bgImage.contains("url")) {
                        src = bgImage.substring(bgImage.indexOf("url(") + 4, bgImage.lastIndexOf(")"))
                                .replace("\"", "")
                                .replace("'", "");
                    }
                }

                // JS validation for <img> tag
                boolean loaded = true;
                if ("img".equalsIgnoreCase(img.getTagName())) {
                    loaded = (Boolean) js.executeScript(
                            "return arguments[0].complete && " +
                                    "typeof arguments[0].naturalWidth != 'undefined' && " +
                                    "arguments[0].naturalWidth > 0;", img);
                }

                if (src == null || src.isEmpty() || !loaded) {
                    brokenCount++;
                    System.out.println("❌ Broken or missing image: " + src);
                } else {
                    System.out.println("✅ Image loaded successfully: " + src);
                }

            } catch (Exception e) {
                System.out.println("⚠️ Error validating image: " + e.getMessage() + " | Element: " + src);
            }
        }

        if (brokenCount == 0) {
            System.out.println("🎯 All images on the About Us page loaded successfully!");
        } else {
            System.out.println("⚠️ Total broken or missing images: " + brokenCount);
        }
    }


    @Test(priority = 6)
    public void fillContactFormAndValidate() throws InterruptedException {
        logger.info("Validate Contact form myVyayCom");
        WebElement contactUs = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Contact Us']")));
        contactUs.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='form_fields[name]']"))).sendKeys("Preeti k");
        driver.findElement(By.xpath("//input[@name='form_fields[email]']")).sendKeys("preet@example.com");
        driver.findElement(By.xpath("//input[@name='form_fields[field_a967fea]']")).sendKeys("9999999999");
        driver.findElement(By.xpath("//input[@name='form_fields[field_e44e24b]']")).sendKeys("Company Name");
        driver.findElement(By.xpath("//textarea[@name='form_fields[message]']")).sendKeys("This is a test message.");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");
        WebElement checkbox = driver.findElement(By.xpath("//input[@type='checkbox']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'successful')]")));
        Assert.assertTrue(successMessage.isDisplayed(), "Your submission was successful");
        driver.navigate().back();
    }
}
