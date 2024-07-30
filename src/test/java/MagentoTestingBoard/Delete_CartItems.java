package MagentoTestingBoard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

public class Delete_CartItems {

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
    public void testAddHeroHoodieToCart() {
        // ... (previous test case remains the same)
    }

    @Test
    public void testDeleteItemFromCart() {
        try {
            //Adding item to the cart
            driver.get(baseUrl);

            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("search")));
            searchBox.sendKeys("Hero Hoodie");
            searchBox.submit();

            List<WebElement> productLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".product-item-link")));

            for (WebElement productLink : productLinks) {
                if (productLink.getText().trim().equalsIgnoreCase("Hero Hoodie")) {
                    wait.until(ExpectedConditions.elementToBeClickable(productLink)).click();
                    break;
                }
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("product-addtocart-button")));

            WebElement sizeOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='M']")));
            sizeOption.click();

            WebElement colorOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Black']")));
            colorOption.click();

            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("product-addtocart-button")));
            addToCartButton.click();

            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".message-success")));
            Assert.assertTrue(successMessage.isDisplayed(), "Success message not displayed after adding product to cart.");

            // deleting the item from the cart
            WebElement miniCart = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".action.showcart")));
            miniCart.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".block-minicart")));

            
            WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".action.delete")));
            deleteButton.click();

           
            WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".action-primary.action-accept")));
            confirmButton.click();

          
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".block-minicart .product-item")));

            // Verifying that the cart is empty
            List<WebElement> cartItems = driver.findElements(By.cssSelector(".block-minicart .product-item"));
            Assert.assertTrue(cartItems.isEmpty(), "Cart is not empty after deleting the item.");

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