package MagentoTestingBoard;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Shopping_Options {
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
    public void testSelectLaptopBags() {
        try {
            driver.get(baseUrl);
            
            // Navigating  to Gear to select Bags
            WebElement gearMenu = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Gear")));
            Actions actions = new Actions(driver);
            actions.moveToElement(gearMenu).perform();
            
            WebElement bagsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Bags")));
            bagsLink.click();
            
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products.wrapper")));
            
            
            WebElement styleFilter = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='filter-options-title' and contains(text(), 'Style')]")));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", styleFilter);
            
         
            if (!driver.findElement(By.xpath("//div[@class='filter-options-content' and ./preceding-sibling::div[contains(text(), 'Style')]]")).isDisplayed()) {
                styleFilter.click();
            }
            
            // Selecting "Laptop" by style
            WebElement laptopCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='filter-options-content']//a[contains(text(), 'Laptop')]")));
            laptopCheckbox.click();
            
            
            wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector(".products.wrapper"))));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products.wrapper")));
            
            
            WebElement appliedFilter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='filter-value' and contains(text(), 'Laptop')]")));
            Assert.assertTrue(appliedFilter.isDisplayed(), "Laptop filter is not applied");
            
            System.out.println("Test completed successfully: Laptop bags filter applied.");
            
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