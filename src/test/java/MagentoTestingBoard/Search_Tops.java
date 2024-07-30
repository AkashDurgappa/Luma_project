package MagentoTestingBoard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

public class Search_Tops {

    private WebDriver driver;
    private String baseUrl = "https://magento.softwaretestingboard.com/";

    @BeforeClass
    public void setUp() {
        
        WebDriverManager.chromedriver().setup();

        // Initialize WebDriver and configure browser settings
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    
    @Test
    public void testSearchSpecificTop() throws InterruptedException {
       
        driver.get(baseUrl);

       
        WebElement searchBox = driver.findElement(By.id("search"));
        Thread.sleep(3000);
        searchBox.sendKeys("Gwyn Endurance Tee");

        
        WebElement searchButton = driver.findElement(By.cssSelector("button.action.search"));
        Thread.sleep(3000);
        searchButton.click();

        List<WebElement> products = driver.findElements(By.cssSelector(".product-item-link"));
        boolean productFound = products.stream()
                .anyMatch(product -> product.getText().equalsIgnoreCase("Gwyn Endurance Tee"));

        Assert.assertTrue(productFound, "Gwyn Endurance Tee not found in search results.");
    }

    @AfterClass
    public void tearDown() {
         if (driver != null) {
            driver.quit();
        }
    }
}
