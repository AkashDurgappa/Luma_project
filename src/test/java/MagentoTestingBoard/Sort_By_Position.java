
package MagentoTestingBoard;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Sort_By_Position {
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
    public void testSortPantsByPosition() {
        try {
            driver.get(baseUrl);
            
            // Navigating to Men's Pants page
            driver.get(baseUrl + "men/bottoms-men/pants-men.html");
          
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products.list.items.product-items")));
            
           
            WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-role='sorter']")));
            Select sortSelect = new Select(sortDropdown);
            sortSelect.selectByVisibleText("Position");
           
            wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector(".products.list.items.product-items"))));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products.list.items.product-items")));
            
            List<WebElement> productItems = driver.findElements(By.cssSelector(".product-item-info"));
            boolean isSorted = true;
            
            
            for (int i = 1; i < productItems.size(); i++) {
                String prevProductId = productItems.get(i-1).getAttribute("data-product-id");
                String currentProductId = productItems.get(i).getAttribute("data-product-id");
                if (Integer.parseInt(prevProductId) > Integer.parseInt(currentProductId)) {
                    isSorted = false;
                    break;
                }
            }
            
            Assert.assertTrue(isSorted, "Products are not sorted by position correctly.");
            
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