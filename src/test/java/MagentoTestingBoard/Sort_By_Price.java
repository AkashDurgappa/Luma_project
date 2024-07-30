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

public class Sort_By_Price {

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
    public void testSortHoodiesAndSweatshirtsByPrice() {
        try {
            
            driver.get(baseUrl);

            // Navigating Men's Hoodies & Sweatshirts page
            driver.get(baseUrl + "men/tops-men/hoodies-and-sweatshirts-men.html");

            // Wait for the product list page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products.list.items.product-items")));

            
            WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-role='sorter']")));
            Select sortSelect = new Select(sortDropdown);
            sortSelect.selectByVisibleText("Price");

          
            wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector(".products.list.items.product-items"))));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products.list.items.product-items")));

            // Verifying that the products are sorted by price
            List<WebElement> productPrices = driver.findElements(By.cssSelector(".price-box .price"));
            boolean isSorted = true;
            double previousPrice = Double.parseDouble(productPrices.get(0).getText().replace("$", "").trim());

            for (int i = 1; i < productPrices.size(); i++) {
                double currentPrice = Double.parseDouble(productPrices.get(i).getText().replace("$", "").trim());
                if (currentPrice < previousPrice) {
                    isSorted = false;
                    break;
                }
                previousPrice = currentPrice;
            }

            Assert.assertTrue(isSorted, "Products are not sorted by price in ascending order.");

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