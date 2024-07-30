package MagentoTestingBoard;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Verify_WhatsNew_Page_Title {
    private WebDriver driver;
    private String baseUrl = "https://magento.softwaretestingboard.com/what-is-new.html";
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
    public void testWhatsNewPageTitle() {
        try {
            driver.get(baseUrl);
            
            
            wait.until(driver -> driver.getTitle().toLowerCase().contains("what's new"));
            
          
            String actualTitle = driver.getTitle();
            
            // Expected title 
            String expectedTitle = "What's New";
            
            // Verify that the actual title contains the expected title
            Assert.assertTrue(actualTitle.contains(expectedTitle), 
                              "Page title does not match. Expected title to contain: " + expectedTitle + 
                              ", but got: " + actualTitle);
            
            System.out.println("Test completed successfully: What's New page title verified.");
            
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