package allWebsites;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class sunriseFoundation extends baseClass {

    @Test
    public void openSunriseUrl() {
        logger.info("Validate sunrise Foundation");
        driver.get("https://mysunrisefoundation.org/");
    }
    @Test(priority = 5)
    public void validatePageTitle() {
        logger.info("Validate Home Page sunrise Foundation");
        String expectedTitle = "Sunrise Foundation – Foundation Blog";
        String actualTitle = driver.getTitle();
        if (actualTitle.equals(expectedTitle)) {
            System.out.println("Page title is correct: " + actualTitle);
        } else {
            System.out.println("Page title is incorrect. Expected: " + expectedTitle + ", but got: " + actualTitle);
        }
    }
    @Test
    public void validateHomeLink() {
        logger.info("Validate Home Link sunrise Foundation");
        WebElement homeLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='menu-item-24']//a[@class='menu-link'][normalize-space()='Home']")));
        Assert.assertTrue(homeLink.isDisplayed() && homeLink.isEnabled(), "Homelink link is clickable");
    }
    @Test
    public void validatecertificate() {
        logger.info("Validate Certificate sunrise Foundation");
        WebElement certificateLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='menu-item-451']//a[@class='menu-link'][normalize-space()='Certificates']")));
        Assert.assertTrue(certificateLink.isDisplayed() && certificateLink.isEnabled(), "Certificate link is clickable");
    }
    @Test
    public void validateGallery() {
        logger.info("Validate Gallery sunrise Foundation");
        WebElement galleryLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='menu-item-520']//a[@class='menu-link'][normalize-space()='Gallery']")));
        Assert.assertTrue(galleryLink.isDisplayed() && galleryLink.isEnabled(), "Certificate link is clickable");
    }
    @Test
    public void validateEducation() {
        logger.info("Validate Education sunrise Foundation");
        WebElement EductionLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='menu-item-1371']//a[@class='menu-link'][normalize-space()='Education']")));
        Assert.assertTrue(EductionLink.isDisplayed() && EductionLink.isEnabled(), "Certificate link is clickable");
    }
    @Test
    public void contactUs() {
        logger.info("Validate Contact Us sunrise Foundation");
        driver.get("https://mysunrisefoundation.org/contact/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='80%'");
        WebElement firstName = driver.findElement(By.xpath("//input[@id='wpforms-13-field_0']"));
        js.executeScript("arguments[0].scrollIntoView(true);", firstName);
        firstName.sendKeys("TestName");
        WebElement lastName = driver.findElement(By.xpath("//input[@id='wpforms-13-field_0-last']"));
        js.executeScript("arguments[0].scrollIntoView(true);", lastName);
        lastName.sendKeys("TestLastName");
        WebElement email = driver.findElement(By.xpath("//input[@id='wpforms-13-field_1']"));
        js.executeScript("arguments[0].scrollIntoView(true);", email);
        email.sendKeys("test@example.com");
        WebElement message = driver.findElement(By.xpath("//textarea[@id='wpforms-13-field_2']"));
        js.executeScript("arguments[0].scrollIntoView(true);", message);
        message.sendKeys("This is a test message");
        WebElement element = driver.findElement(By.xpath("//button[@id='wpforms-submit-13']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.submit();
    }
    @Test
    public void valiateAboutUs() throws InterruptedException {
        logger.info("Validate About us sunrise Foundation");
        driver.get("https://mysunrisefoundation.org/about/");
        Actions actions = new Actions(driver);
        By aboutUs = By.xpath("//li[@id='menu-item-1141']//a[normalize-space()='About Us']");
        By managingTrustee = By.xpath("//li[@id='menu-item-1372']//a[normalize-space()='Managing trustee']");
        By aboutFoundation = By.xpath("//li[@id='menu-item-449']//a[normalize-space()='About Foundation']");
        WebElement aboutUsLink = wait.until(ExpectedConditions.elementToBeClickable(aboutUs));
        aboutUsLink.click();
        Thread.sleep(2000);
        actions.moveToElement(aboutUsLink).pause(Duration.ofSeconds(2)).perform();
        WebElement managingTrusteeLink = wait.until(ExpectedConditions.elementToBeClickable(managingTrustee));
        Assert.assertTrue(managingTrusteeLink.isDisplayed(), "Managing Trustee link is not visible.");
        managingTrusteeLink.click();
        driver.navigate().back();
        aboutUsLink.click();
        Thread.sleep(4000);
        actions.moveToElement(aboutUsLink).pause(Duration.ofSeconds(2)).perform();
        WebElement aboutFoundationLink = wait.until(ExpectedConditions.elementToBeClickable(aboutFoundation));
        Assert.assertTrue(aboutFoundationLink.isDisplayed(), "About Foundation link is not visible.");
        aboutFoundationLink.click();
        driver.navigate().back();
    }

    @Test
    public void brokenLink() {
        logger.info("Validate Broken Links sunrise Foundation");
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
                    System.out.println(hrefValue + "=>Not a Broken Link");
                }
            } catch (Exception e) {

            }
        }
        System.out.println("No. of Broken links" + noOfBrokenLinks);
    }

    @Test
    public void donatePage() throws InterruptedException {
        logger.info("Validate Donate Page sunrise Foundation");
        driver.get("https://mysunrisefoundation.org/donate/");
        WebElement donateButton = driver.findElement(By.xpath("//a[@class='ast-custom-button-link']//div[@class='ast-custom-button'][normalize-space()='Donate']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", donateButton);
        WebElement element = driver.findElement(By.xpath("//div[@class='choices__item choices__placeholder choices__item--selectable']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500); // small wait before interacting
        element.click();
        WebElement selectElement = driver.findElement(By.xpath("//select[@id='wpforms-608-field_1']"));
        Select select = new Select(selectElement);
        List<WebElement> allOptions = select.getOptions();
        for (WebElement option : allOptions) {
            System.out.println("Available option: " + option.getAttribute("value"));
        }
        driver.findElement(By.xpath("//div[contains(@class, 'choices__list--dropdown')]//div[text()='Ms.']")).click();
        // Scroll and fill First Name
        WebElement fname = driver.findElement(By.xpath("//input[@id='wpforms-608-field_3']"));
        js.executeScript("arguments[0].scrollIntoView(true);", fname);
        fname.sendKeys("Preeti ");
        // Scroll and fill Last Name
        WebElement lname = driver.findElement(By.xpath("//input[@id='wpforms-608-field_3-last']"));
        js.executeScript("arguments[0].scrollIntoView(true);", lname);
        lname.sendKeys("test");
        // Phone Number
        WebElement number = driver.findElement(By.xpath("//input[@id='wpforms-608-field_7']"));
        js.executeScript("arguments[0].scrollIntoView(true);", number);
        number.sendKeys("9876543210");
        // Email
        WebElement email = driver.findElement(By.xpath("//input[@id='wpforms-608-field_4']"));
        js.executeScript("arguments[0].scrollIntoView(true);", email);
        email.sendKeys("test.preet@example.com");
        // State
        WebElement state = driver.findElement(By.xpath("//input[@id='wpforms-608-field_11']"));
        js.executeScript("arguments[0].scrollIntoView(true);", state);
        state.sendKeys("Maharashtra");
        // City
        WebElement city = driver.findElement(By.xpath("//input[@id='wpforms-608-field_9']"));
        js.executeScript("arguments[0].scrollIntoView(true);", city);
        city.sendKeys("Mumbai");
        // PAN Card
        WebElement pancard = driver.findElement(By.xpath("//input[@name='wpforms[fields][14]'][@placeholder='Enter Pan No.']"));
        js.executeScript("arguments[0].scrollIntoView(true);", pancard);
        pancard.sendKeys("ABCDE1234F");
        // Address
        WebElement address = driver.findElement(By.xpath("//textarea[@id='wpforms-608-field_6']"));
        js.executeScript("arguments[0].scrollIntoView(true);", address);
        address.sendKeys("123, Palm Street, Andheri West");
        // Submit
        WebElement submit = driver.findElement(By.xpath("//button[@id='wpforms-submit-608']"));
        js.executeScript("arguments[0].scrollIntoView(true);", submit);
        submit.submit();
        // Message
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String expectedError = "Form has not been submitted, please see the errors below.";
        try {
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='wpforms-error-container']//p[normalize-space()='" + expectedError + "']")));
            if (errorMsg.isDisplayed()) {
                System.out.println("Error message displayed correctly: " + errorMsg.getText());
            }
        } catch (Exception e) {
            System.out.println(" Error message not found.");
        } driver.navigate().back();
    }
    @Test
    public void emailboxatPageDown () {
        logger.info("Validate email Box Page sunrise Foundation");
        driver.get("https://mysunrisefoundation.org/donate/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        WebElement emailInput = driver.findElement(By.xpath("//input[@type='email'][@id='wpforms-11-field_1']"));
        emailInput.sendKeys("preeti@testmail.com");
        WebElement submitButton = driver.findElement(By.xpath("//button[@id='wpforms-submit-11'][@name='wpforms[submit]']"));
        submitButton.submit();
        driver.navigate().back();
    }
}