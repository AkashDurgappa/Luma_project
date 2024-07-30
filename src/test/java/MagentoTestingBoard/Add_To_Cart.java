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

public class Add_To_Cart {

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
        try {
           
            driver.get(baseUrl);

            // Searching for "Hero Hoodie"
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("search")));
            searchBox.sendKeys("Hero Hoodie");
            searchBox.submit();

            
            List<WebElement> productLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".product-item-link")));

           
            boolean productFound = false;
            for (WebElement productLink : productLinks) {
                if (productLink.getText().trim().equalsIgnoreCase("Hero Hoodie")) {
                    wait.until(ExpectedConditions.elementToBeClickable(productLink)).click();
                    productFound = true;
                    break;
                }
            }
            Assert.assertTrue(productFound, "Hero Hoodie not found in search results.");

          
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("product-addtocart-button")));

            // Selecting the size "M" for the Hero Hoodie
            WebElement sizeOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='M']")));
            sizeOption.click();

            
            Assert.assertTrue(sizeOption.getAttribute("class").contains("selected"), "Size M is not selected.");

            // Selecting the color "Black" for the Hero Hoodie
            WebElement colorOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Black']")));
            colorOption.click();

            
            Assert.assertTrue(colorOption.getAttribute("class").contains("selected"), "Color Black is not selected.");

            // Clicking on the "Add to Cart" button
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("product-addtocart-button")));
            addToCartButton.click();

            
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".message-success")));
            Assert.assertTrue(successMessage.isDisplayed(), "Success message not displayed after adding product to cart.");

      
            wait.until(ExpectedConditions.invisibilityOf(successMessage));

           
            WebElement miniCart = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".action.showcart")));
            miniCart.click();

            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".block-minicart")));
            List<WebElement> cartItems = driver.findElements(By.cssSelector(".product-item-name a"));

            boolean heroHoodieInCart = cartItems.stream()
                .anyMatch(item -> item.getText().trim().toLowerCase().contains("hero hoodie"));

            Assert.assertTrue(heroHoodieInCart, "Hero Hoodie not found in the cart.");

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