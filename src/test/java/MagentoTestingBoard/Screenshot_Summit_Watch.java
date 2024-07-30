package MagentoTestingBoard;

import java.time.Duration;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Screenshot_Summit_Watch {
    private WebDriver driver;
    private String baseUrl = "https://magento.softwaretestingboard.com/";
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testScreenshotSummitWatch() {
        try {
            driver.get(baseUrl);
            
          
            WebElement gearMenu = wait.until(ExpectedConditions.elementToBeClickable(By.id("ui-id-6")));
            Actions actions = new Actions(driver);
            actions.moveToElement(gearMenu).perform();
            
            WebElement watchesLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("ui-id-27")));
            watchesLink.click();
            
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products.wrapper")));
            
            WebElement summitWatch = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@class='product-item-link' and contains(text(), 'Summit Watch')]")));
            summitWatch.click();
            
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product-info-main")));
            
            // Take a screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("summit_watch_screenshot.png"));
            
            System.out.println("Test completed successfully: Screenshot of Summit Watch taken.");
            
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to an exception: " + e.getMessage(), e);
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}