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

public class Select_List_View {
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
    public void testSelectWatchesListView() {
        try {
            driver.get(baseUrl);
            
            // Navigating to Watches page
            WebElement gearMenu = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Gear")));
            Actions actions = new Actions(driver);
            actions.moveToElement(gearMenu).perform();
            
            WebElement watchesLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Watches")));
            watchesLink.click();
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products.wrapper")));
            
            
            WebElement listViewButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.mode-list")));
            listViewButton.click();
            
            
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".products.wrapper.list")));
            
          
            WebElement listViewActive = driver.findElement(By.cssSelector("a.mode-list.active"));
            Assert.assertNotNull(listViewActive, "List view is not active");
            
           
            WebElement productList = driver.findElement(By.cssSelector(".products.wrapper.list"));
            Assert.assertTrue(productList.isDisplayed(), "Products are not displayed in list format");
            
            System.out.println("Test completed successfully: Watches are displayed in list view.");
            
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