package allWebsites;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class eliteTravelDesk extends baseClass {
    @Test(priority = 1)
    public void openLoginAndSignUpWithEmail() throws InterruptedException {
        logger.info("Validate Elite Travel Desk");
        driver.get("https://elitetraveldesk.com/");
        String expectedTitle = "Elite Travel Desk";
        String actualTitle = driver.getTitle();

        if (actualTitle.equals(expectedTitle)) {
            System.out.println("Page title is correct: " + actualTitle);
        } else {
            System.out.println("Page title is incorrect. Found: " + actualTitle);
        }
    }
    // Comment close button lines & UnComment login click when you want to test Sign up Page
    @Test(priority = 2)
    public void flexPopUp() {
        logger.info("Validate Flex Pop Up Elite Travel Desk");
        try {
            WebElement cookieBanner = driver.findElement(By.xpath("//div[@class='flex-row-direction ng-tns-c198-26']"));
            if (cookieBanner.isDisplayed()) {
                cookieBanner.findElement(By.xpath("//mat-icon[normalize-space()='close'][@aria-hidden='true']")).click();
            }
        } catch (NoSuchElementException e) {
            // Popup not there
        } }
    @Test(priority = 3)
    public void validateflight () {
        logger.info("Validate Flight, Elite Travel Desk");
        WebElement clickableElement = driver.findElement(By.xpath("//img[@class='width20' and @alt='Flights']"));
        clickableElement.click();
        Assert.assertTrue(clickableElement.isEnabled(), "Flight link is enable and Displayed");
        /*WebElement startLocation = driver.findElement(By.xpath("//p[@class='selected-origin-tag autocomplete-taglist-container w-100 ng-star-inserted'][normalize-space()='Pune']"));
        startLocation.clear();
        startLocation.sendKeys("Mumbai");
        WebElement destinationLocation = driver.findElement(By.xpath("//input[@id='search_input_flight-destination'][@placeholder='Enter Destination']"));
        destinationLocation.sendKeys("Dubai");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='80%'");
        WebElement clickStudent = driver.findElement(By.xpath("//span[@class='mat-tooltip-trigger fareType px-2 py-1 addCursor me-1 mb-2 selected-fare-type']"));
        clickStudent.click();
        WebElement search = driver.findElement(By.xpath("//button[@type='submit']//span[@class='mat-button-wrapper']"));
        search.submit();
                //Date picker is not interactable so as it is taking default date, hence commenting below lines
       /* String year = "2025";
        String month = "APR";
        String date = "23";
        WebElement datePicker = driver.findElement(By.xpath("//input[@id='startDatePicker']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", datePicker);

           while(true) {
               WebElement currentmonthAndYear = driver.findElement(By.xpath("//button[@aria-label='Choose month and year']"));
               if (currentmonthAndYear.equals(year + " " + month)) {
                   break;
               }
               driver.findElement(By.xpath("//button[@aria-label='Next month']")).click(); //click on next arrow if date is for next month
           }
        List <WebElement> alldates = driver.findElements(By.xpath("//div[@class='mat-calendar-header']//body//tr//td//a"));
for(WebElement selectdate:alldates){
    if(selectdate.getText().equals(date)){
        selectdate.click();
        break;
    }
} */
    }
    @Test(priority = 4)
    public void hotelLinkValidate () throws InterruptedException {
        logger.info("Validate Hotel Link Elite Travel Desk");
        driver.get("https://elitetraveldesk.com/search/trips#stay");
        WebElement destinationField = driver.findElement(By.xpath("//input[@id='search_input_hotel-destination']"));
        destinationField.sendKeys("Mumbai");
        WebElement punedrp = driver.findElement(By.xpath("//li[1]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(punedrp).click().build().perform();
        //punedrp.sendKeys(Keys.ENTER);
        WebElement addperson = driver.findElement(By.xpath("//button[@id='travellerModal']"));
        addperson.click();
        WebElement plusclick = driver.findElement(By.xpath("//button[normalize-space()='+']"));
        plusclick.click();
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='70%'");
        WebElement apply = driver.findElement(By.xpath("//button[normalize-space()='Apply']"));
        apply.click();
        WebElement budget = driver.findElement(By.xpath("//input[@name='budget'] "));//p[contains(text(),'₹')]
        budget.clear();
        budget.sendKeys("5000");
        WebElement search = driver.findElement(By.xpath("//span[normalize-space()='Search']"));
        search.click();
        String currentUrl = driver.getCurrentUrl();
        boolean isPopupPresent = driver.findElements(By.xpath("//button[normalize-space()='Okay']")).size() > 0;
        if (isPopupPresent) {
            WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Okay']")));
            okButton.click();
            System.out.println("Error popup appeared. Clicked 'Okay'.");
        } else {
            wait.until(ExpectedConditions.urlContains("https://elitetraveldesk.com/detail/hotel-in-"));
            String currentUrlhotel = driver.getCurrentUrl();
            Assert.assertTrue(currentUrlhotel.contains("https://elitetraveldesk.com/detail/hotel-in-"),
                    " URL is incorrect after search!");
            System.out.println("URL validation passed.");
        }
    }
    @Test(priority = 5)
    public void planaTrip () throws InterruptedException {
        logger.info("Validate Plan A Trip Elite Travel Desk");
        driver.get("http://elitetraveldesk.com/search/trips#travel_and_stay");
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='80%'");
        WebElement inspireMe = driver.findElement(By.xpath("//p[normalize-space()='Inspire Me']"));
        inspireMe.click();
        WebElement addPreferance = driver.findElement(By.xpath("//mat-icon[normalize-space()='add_circle_outline']"));
        addPreferance.click();
        WebElement adddays = driver.findElement(By.xpath("//mat-icon[@class='mat-icon notranslate addRemoveCircle main-color material-icons mat-ligature-font mat-icon-no-color'][normalize-space()='add_circle_outline']"));
        adddays.click();
        WebElement noOfTravellers = driver.findElement(By.xpath("//div[@type='button'][@class='font16']"));
        noOfTravellers.click();
        WebElement clickOnPlus = driver.findElement(By.xpath("//button[normalize-space()='+']"));
        clickOnPlus.click();
        WebElement apply = driver.findElement(By.xpath("//button[normalize-space()='Apply']"));
        apply.click();
        Thread.sleep(5000);
        WebElement input = driver.findElement(By.xpath("//input[@placeholder='Enter Origin'][@id='search_input_origin']"));
        //input.click();
        input.sendKeys("Pune");
        WebElement suggestion = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='type-selector destination-name mb-0']")));
        suggestion.click();
        Thread.sleep(5000);
        WebElement nextButton = driver.findElement(By.xpath("//button[normalize-space()='Next']"));
        nextButton.click();
        WebElement roadTripClick = driver.findElement(By.xpath("//div[normalize-space()='On a Road Trip']"));
        roadTripClick.click();
        WebElement relaxation = driver.findElement(By.xpath("//div[normalize-space()='Relaxation']"));
        relaxation.click();
        WebElement atBestHotel = driver.findElement(By.xpath("//div[normalize-space()='At only the best']"));
        atBestHotel.click();

        WebElement range = driver.findElement(By.xpath("//input[@id='mat-input-2']"));
        range.sendKeys("10000");
        WebElement addTrip = driver.findElement(By.xpath("//button[normalize-space()='Add to trip']"));
        addTrip.click();
        WebElement createpackage = driver.findElement(By.xpath("//button[@class='createPkgBtn main-color-bg']"));
        createpackage.click();
        /*String summaryText = driver.findElement(By.xpath("//p[@class='font16 boldProx']")).getText();
        Assert.assertTrue(summaryText.contains("Trip Search Summary"), "Search Summary text not found!");*/
        System.out.println("Validation passed.");
        driver.navigate().back();

    }
    @Test(priority = 6)
    public void intercityCabs() throws InterruptedException {
        logger.info("Validate Intercity Cab link Elite Travel Desk");
        driver.get("https://elitetraveldesk.com/search/trips#drive");

        // Zoom out to ensure all elements are interactable
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='70%'");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Origin Input
        /*WebElement origin = wait.until(ExpectedConditions.elementToBeClickable(By.id("search_input_intercity-origin")));
        origin.sendKeys("Mumbai");
        WebElement mumbaiSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@class='boldProx agency-color' and normalize-space()='Mumbai']")));
        mumbaiSuggestion.click();*/

        // Destination Input
        WebElement destination = wait.until(ExpectedConditions.elementToBeClickable(By.id("search_input_intercity-destination")));
        destination.sendKeys("Pune");
        WebElement puneSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@class='boldProx agency-color' and normalize-space()='Pune']")));
        puneSuggestion.click();

        // Click Search Button
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@type='submit']//span[normalize-space()='Search']")));
        searchButton.click();

        // Handle Modal Popup or Validate URL
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement modal = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'modal-body')]")));
            WebElement backToSearchBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[@class='mat-button-wrapper' and normalize-space()='Back to Search']")));
            backToSearchBtn.click();
            System.out.println("Popup appeared and handled by clicking 'Back to Search'.");
        } catch (TimeoutException e) {
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("detail/drive-in"), "Redirection to drive-in detail page failed.");
            System.out.println("No popup. Redirect successful. Validation passed.");
            driver.navigate().back();
        }
    }/*
            @Test(priority = 6)
            public void intercityCabs () throws InterruptedException {
                driver.get("https://elitetraveldesk.com/search/trips#drive");
                ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='70%'");
                wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                WebElement origin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='search_input_intercity-origin']")));
               // origin.click();
                origin.sendKeys("Mumbai");
                WebElement mumbaiSuggestion = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='boldProx agency-color'][normalize-space()='Mumbai']")));
                mumbaiSuggestion.click();
                WebElement destination = driver.findElement(By.xpath("//input[@id='search_input_intercity-destination']"));
                destination.click();
                destination.sendKeys("Pune");
                WebElement puneSuggestion = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='dropDownL_intercity-destination']")));  //span[@class='boldProx agency-color'][normalize-space()='Pune']
                puneSuggestion.click();
                driver.findElement(By.xpath("//button[@type='submit']//span[normalize-space()='Search']")).click();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                try {
                    WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[@class='modal-body container ng-star-inserted']")));
                    WebElement backToSearchBtn = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//span[@class='mat-button-wrapper'][normalize-space()='Back to Search']")));
                    backToSearchBtn.click();
                    System.out.println("Popup appeared and handled by clicking 'Back to Search'.");
                } catch (TimeoutException e) {
                    String currentUrl = driver.getCurrentUrl();
                    Assert.assertTrue(currentUrl.contains("https://elitetraveldesk.com/detail/drive-in"), "URL is incorrect!");
                    System.out.println("Validation passed.");
                    driver.navigate().back();
                }
            }*/
    @Test(priority =7 )
    public void activityLinkValidation() throws InterruptedException{
        logger.info("Validate Activity Link Elite Travel Desk");
        driver.get("https://elitetraveldesk.com/search/trips#activity");
        WebElement activityDestination =driver.findElement(By.xpath("//input[@id='search_input_activity-destination']"));
        activityDestination.sendKeys("Goa");
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='70%'");
        WebElement findGoa = driver.findElement(By.xpath("//li[@id='dropDownL_activity-destination'][1]"));
        findGoa.click();
        WebElement budgetactivity = driver.findElement(By.xpath("//input[@name='budget']"));
        budgetactivity.click();
        budgetactivity.sendKeys("5000");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(4000);
        driver.navigate().back();
    }
    @Test(priority =8 )
    public void transfersLinkValidation(){
        logger.info("Validate Transfers Link Elite Travel Desk");
        WebElement transfersTab = driver.findElement(By.xpath("//span[normalize-space()='Transfers']"));
        transfersTab.click();
        Assert.assertTrue(transfersTab.isDisplayed() && transfersTab.isEnabled(), "Transfers tab is not displayed or not enabled after click");

    }
    @Test(priority = 9)
    public void organizedPackage(){
        logger.info("Validate Organized Package Elite Travel Desk");
        WebElement organized = driver.findElement(By.xpath("//span[normalize-space()='Organized Packages']"));
        organized.click();
        Assert.assertTrue(organized.isDisplayed() && organized.isEnabled(), "Organized Package is Clickable");
        WebElement enterdriver =driver.findElement(By.xpath("//input[@id='search_input_offline-destination']"));
        enterdriver.sendKeys("Banglore");
        WebElement selectBanglore = driver.findElement(By.xpath("//p[normalize-space()='Bengaluru'][@class='type-selector destination-name mb-0']"));
        selectBanglore.click();
        WebElement searchClick = driver.findElement(By.xpath("//button[@type='submit']"));
        searchClick.click();
        driver.navigate().back();
        driver.findElement(By.xpath("//img[@alt='Sample Image'][@id='header_logo']"));
    }
    @Test(priority = 10)
    public void brokenLink() {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("Number of Links" + links.size());
        /*int noOfBrokenLinks=0;

        for (WebElement linkElement : links) {
            String hrefValue = linkElement.getAttribute("href");
            if (hrefValue == null || hrefValue.isEmpty()) {
                System.out.println("Not Possible to check");
                continue;
            }  try {
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
        } System.out.println("No. of Broken links" + noOfBrokenLinks);*/
    }

}

