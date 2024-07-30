package MagentoTestingBoard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class SignIn {

    private WebDriver driver;
    private String baseUrl = "https://magento.softwaretestingboard.com/";

    @BeforeClass
    public void setUp() {
        
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
   }
//8 Test Cases    
//1.testValidLogin
    @Test
    public void testValidLogin() {
        driver.get(baseUrl + "customer/account/login/");
        driver.findElement(By.id("email")).sendKeys("validemail@example.com");
        driver.findElement(By.id("pass")).sendKeys("validPassword");
        driver.findElement(By.id("send2")).click();

        WebElement accountDashboard = driver.findElement(By.cssSelector("div[class*='block-dashboard-info']"));
        Assert.assertTrue(accountDashboard.isDisplayed(), "User is not redirected to account dashboard after login.");
    }
//2.testInvalidLogin
    @Test(dataProvider = "invalidCredentials")
    public void testInvalidLogin(String email, String password) {
        driver.get(baseUrl + "customer/account/login/");
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(password);
        driver.findElement(By.id("send2")).click();

        WebElement errorMessage = driver.findElement(By.cssSelector("div.message-error"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid login attempt.");
    }
 //3.TestInvalidLoginWithNonExistentEmail
    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentialsProvider() {
        return new Object[][]{
            {"validemail@example.com", "wrongPassword"},     // Incorrect Password
            {"nonexistent@example.com", "anyPassword"},      // Non-existent Email
            {"", ""},                                        // Empty Email and Password
            {"validemail@example.com", ""},                  // Empty Password
            {"", "anyPassword"},                             // Empty Email
            {"invalidEmailFormat", "anyPassword"},           // Invalid Email Format 1
            {"anotherInvalidEmail@", "anyPassword"},         // Invalid Email Format 2
        };
    }
//4.testEmptyEmailAndPassword
    @Test
    public void testEmptyEmailAndPassword() {
        driver.get(baseUrl + "customer/account/login/");
        driver.findElement(By.id("send2")).click();

        WebElement emailValidationMessage = driver.findElement(By.id("advice-required-entry-email"));
        WebElement passwordValidationMessage = driver.findElement(By.id("advice-required-entry-pass"));
        Assert.assertTrue(emailValidationMessage.isDisplayed(), "Email validation message not displayed.");
        Assert.assertTrue(passwordValidationMessage.isDisplayed(), "Password validation message not displayed.");
    }
//5.testEmptyPassword
    @Test
    public void testEmptyPassword() {
        driver.get(baseUrl + "customer/account/login/");
        driver.findElement(By.id("email")).sendKeys("validemail@example.com");
        driver.findElement(By.id("send2")).click();

        WebElement passwordValidationMessage = driver.findElement(By.id("advice-required-entry-pass"));
        Assert.assertTrue(passwordValidationMessage.isDisplayed(), "Password validation message not displayed.");
    }
//6.testEmptyEmail
    @Test
    public void testEmptyEmail() {
        driver.get(baseUrl + "customer/account/login/");
        driver.findElement(By.id("pass")).sendKeys("anyPassword");
        driver.findElement(By.id("send2")).click();

        WebElement emailValidationMessage = driver.findElement(By.id("advice-required-entry-email"));
        Assert.assertTrue(emailValidationMessage.isDisplayed(), "Email validation message not displayed.");
    }
//7.testInvalidEmailFormat
    @Test
    public void testInvalidEmailFormat() {
        driver.get(baseUrl + "customer/account/login/");
        driver.findElement(By.id("email")).sendKeys("invalidEmailFormat");
        driver.findElement(By.id("pass")).sendKeys("anyPassword");
        driver.findElement(By.id("send2")).click();

        WebElement emailValidationMessage = driver.findElement(By.id("advice-validate-email-email"));
        Assert.assertTrue(emailValidationMessage.isDisplayed(), "Email format validation message not displayed.");
    }
//8.testRememberMeFunctionality
    @Test
    public void testRememberMeFunctionality() {
        driver.get(baseUrl + "customer/account/login/");
        driver.findElement(By.id("email")).sendKeys("validemail@example.com");
        driver.findElement(By.id("pass")).sendKeys("validPassword");
        driver.findElement(By.id("remember-me")).click();
        driver.findElement(By.id("send2")).click();

        WebElement accountDashboard = driver.findElement(By.cssSelector("div[class*='block-dashboard-info']"));
        Assert.assertTrue(accountDashboard.isDisplayed(), "User is not redirected to account dashboard after login.");

        driver.quit();

        // Reinitialize the driver for the next session
        setUp();  
        driver.get(baseUrl);

        WebElement accountLink = driver.findElement(By.cssSelector("a[href*='customer/account']"));
        Assert.assertTrue(accountLink.isDisplayed(), "User is not remembered after reopening the browser.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
