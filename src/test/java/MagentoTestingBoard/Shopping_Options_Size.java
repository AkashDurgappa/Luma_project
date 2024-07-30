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

public class Shopping_Options_Size {
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
    public void testSelectMenTeesSize() {
        try {
            driver.get(baseUrl);
            
            // Navigating to Men < Tops < Tees
            WebElement menMenu = wait.until(ExpectedConditions.elementToBeClickable(By.id("ui-id-5")));
            Actions actions = new Actions(driver);
            actions.moveToElement(menMenu).perform();
            
            WebElement topsMenu = wait.until(ExpectedConditions.elementToBeClickable(By.id("ui-id-17")));
            actions.moveToElement(topsMenu).perform();
            
            WebElement teesLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("ui-id-21")));
            teesLink.click();
            
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products.wrapper")));
            
            // Find and expand the Size filter if it's not already expanded
            WebElement sizeFilter = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='filter-options-title' and contains(text(), 'Size')]")));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sizeFilter);
            
            if (!driver.findElement(By.xpath("//div[@class='filter-options-content' and ./preceding-sibling::div[contains(text(), 'Size')]]")).isDisplayed()) {
                sizeFilter.click();
            }
            
            // Selecting size 'S'
            WebElement sizeSCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='filter-options-content']//a[contains(@href, 'size=167')]")));
            sizeSCheckbox.click();
            
         
            wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector(".products.wrapper"))));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products.wrapper")));
            
         
            WebElement appliedFilter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='filter-value' and contains(text(), 'S')]")));
            Assert.assertTrue(appliedFilter.isDisplayed(), "Size S filter is not applied");
            
            System.out.println("Test completed successfully: Men's Tees size S filter applied.");
            
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