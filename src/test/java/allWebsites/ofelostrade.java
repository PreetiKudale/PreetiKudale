package allWebsites;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class ofelostrade extends baseClass {

    @Test(priority = 1)
    public void openUrl() {
        logger.info("Validate ofelostrade");
        driver.get("https://ofelostrade.com/");
        System.out.println("Opened OFELOS TRADING PVT LTD Website");
    }

    @Test(priority = 2)
    public void validateTeamLink() {
        logger.info("Validate Team ofelostrade");
        WebElement teamLink = driver.findElement(By.xpath("//li[@id='menu-item-1264']//a[@class='menu-link'][normalize-space()='Team']"));
        Assert.assertTrue(teamLink.isDisplayed() && teamLink.isEnabled(), "Team link is not clickable");
        teamLink.click();
    }

    @Test(priority = 3)
    public void validateCertificate() {
        logger.info("Validate Certificate ofelostrade");
        WebElement certificate = driver.findElement(By.xpath("//li[@id='menu-item-1265']//a[@class='menu-link'][normalize-space()='Certificates']"));
        Assert.assertTrue(certificate.isDisplayed() && certificate.isEnabled(), "certificate link is not clickable");
        certificate.click();
    }

    @Test(priority = 4)
    public void validateContactUs() {
        logger.info("Validate Contact us ofelostrade");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='80%'");
        WebElement contact = driver.findElement(By.xpath("//li[@id='menu-item-1263']//a[@class='menu-link'][normalize-space()='Contact Us']"));
        contact.click();
        WebElement nameField = driver.findElement(By.xpath("//input[@id='wpforms-54-field_0']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nameField);
        nameField.sendKeys("Preeti Test");

        WebElement lastNameField = driver.findElement(By.xpath("//input[@name='wpforms[fields][0][last]']"));
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lastNameField);
        lastNameField.sendKeys("LastName");

        WebElement emailField = driver.findElement(By.xpath("//input[@id='wpforms-54-field_1']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", emailField);
        emailField.sendKeys("preeti@test.com");

        WebElement messageField = driver.findElement(By.xpath("//textarea[@id='wpforms-54-field_2']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", messageField);
        messageField.sendKeys("Test Message Box");

        WebElement submit = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));
        submit.click();

        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(),'Form has not been submitted, please see the errors')]")));
        System.out.println(errorMsg.getText());
        Assert.assertTrue(errorMsg.isDisplayed(), "Expected error message not displayed after form submission");
        driver.navigate().back();
        //Google reCAPTCHA verification failed, please try again later.
        // Form has not been submitted, please see the errors below.
    }
    @Test(priority = 5)
    public void brokenLink() {
        logger.info("Validate Broken Links ofelostrade");
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