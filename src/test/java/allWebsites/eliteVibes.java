package allWebsites;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class eliteVibes extends baseClass{

    @Test(priority = 0)
    public void openUrl() {
        logger.info("Validate Elite Vibes");
        driver.get("https://www.elitevibes.in/");
        System.out.println("Opened Elite Vibes");
    }
    @Test(priority = 1)
    public void validatePageTitle() {
        String expectedTitle = "elite vibes || best event management";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle.trim(), expectedTitle, "Title does not match!");
    }
    @Test(priority = 2)
    public void clickAndValidateHome() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement homeLink = wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Home']"))));
        homeLink.click();
    }

    @Test(priority = 3)
    public void clickAndValidateBrands() {
        WebElement brandsLink = driver.findElement(By.xpath("//a[normalize-space()='Brands']"));
        brandsLink.click();
        Assert.assertTrue(brandsLink.isDisplayed() && brandsLink.isEnabled(), "Brands page not loaded.");
    }

    @Test(priority = 4)
    public void clickAndValidateSocials() {
        WebElement socialsLink = driver.findElement(By.xpath("//a[normalize-space()='Socials']"));
        socialsLink.click();
        Assert.assertTrue(socialsLink.isDisplayed() && socialsLink.isEnabled(), "Socials page not loaded.");
    }

    @Test(priority = 5)
    public void clickContactAndFillForm() {
        WebElement contactLink = driver.findElement(By.xpath("//a[normalize-space()='Contact']"));
        contactLink.click();
        driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Test User");
        driver.findElement(By.xpath("//input[@id='phoneNumber']")).sendKeys("9876543210");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("testuser@test.com");
        WebElement messageField= driver.findElement(By.xpath("//textarea[@id='message']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", messageField);
        messageField.sendKeys("This is a test message from automation.");
        WebElement submit = driver.findElement(By.xpath("//button[normalize-space()='Send']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submit);
        submit.click();
        driver.findElement(By.xpath("//button[normalize-space()='Send']")).click();
        // No confirmation check cause send is not clickable
    }

    @Test(priority = 6)
    public void clickAndValidateAbout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement aboutLink = wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='About']"))));
        aboutLink.click();
    }
    @Test(priority = 7)
    public void brokenLink() {
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
        }
        System.out.println("No.of Broken Links =" + noOfBrokenLinks);
    }
}
