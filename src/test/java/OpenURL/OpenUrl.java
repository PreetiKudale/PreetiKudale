package OpenURL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class OpenUrl {
        public static void main(String[] args) {
            //WebDriver driver = new ChromeDriver();
            WebDriver driver = new EdgeDriver();
            //WebDriver driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.get("https://pepsico.nano.camp/");
            String mainwindow= driver.getWindowHandle();
            String mainPage= driver.getTitle();
            // String mwindow = driver.getWindowHandle();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//div[contains(@class, 'baUaFkaB4')]")).click();

            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            //String mwindow = driver.getWindowHandle();
            Set<String> allwindows = driver.getWindowHandles();
            System.out.println(allwindows.size());
            for(String window:allwindows){
                if(!(window.equals(mainwindow))){
                    driver.switchTo().window(window);
                    System.out.println( driver.getTitle());
                }
            }


            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            int count= driver.findElements(By.xpath("//div[text()='Your answer']")).size();
            System.out.println(count);
            driver.findElement(By.xpath("(//textarea[@aria-label='Your answer'])")).sendKeys("preeti");
            driver.findElement(By.xpath("(//input[@jsname='YPqjbf'])[1]")).sendKeys("abcd");
            driver.findElement(By.xpath("(//input[@jsname='YPqjbf'])[2]")).sendKeys("123456");
            driver.findElement(By.xpath("(//input[@jsname='YPqjbf'])[3]")).sendKeys("testpage");
            // driver.findElement(By.xpath("//div[contains(@class,'kRy7qc')]")).sendKeys("abcd");


            //driver.quit();
        }
    }

