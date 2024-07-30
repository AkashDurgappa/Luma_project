package MagentoTestingBoard;

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

import java.time.Duration;
import java.util.List;

public class Sort_By_ProductName {

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
    public void testSortMenJacketsByProductName() {
        try {
            // Navigating directly to the Men's Jackets page
            driver.get(baseUrl + "men/tops-men/jackets-men.html");

        
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products.list.items.product-items")));

            
            WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-role='sorter']")));
            Select sortSelect = new Select(sortDropdown);
            sortSelect.selectByVisibleText("Product Name");

            wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector(".products.list.items.product-items"))));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products.list.items.product-items")));

            // Verifying that the products are sorted by name
            List<WebElement> productNames = driver.findElements(By.cssSelector(".product-item-link"));
            boolean isSorted = true;
            String previousName = productNames.get(0).getText().trim().toLowerCase();

            for (int i = 1; i < productNames.size(); i++) {
                String currentName = productNames.get(i).getText().trim().toLowerCase();
                if (currentName.compareTo(previousName) < 0) {
                    isSorted = false;
                    break;
                }
                previousName = currentName;
            }

            Assert.assertTrue(isSorted, "Products are not sorted by name in ascending order.");

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