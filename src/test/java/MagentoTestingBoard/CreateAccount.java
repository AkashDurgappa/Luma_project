package MagentoTestingBoard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateAccount {

    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
    	
    	WebDriverManager.chromedriver().setup();
       
        driver = new ChromeDriver();

        driver.get("https://magento.softwaretestingboard.com/");
    }

    @AfterClass
    public void tearDown() {
        
        if (driver != null) {
            driver.quit();
        }
    }
//5 TestCases
//1.Create an Account with Valid Data
    @Test()
    public void testCreateAccountWithValidData() {
      
        driver.findElement(By.linkText("Create an Account")).click();
        driver.findElement(By.id("firstname")).sendKeys("John");
        driver.findElement(By.id("lastname")).sendKeys("Doe");
        driver.findElement(By.id("email_address")).sendKeys("johndoe" + System.currentTimeMillis() + "@example.com");
        driver.findElement(By.id("password")).sendKeys("Password123!");
        driver.findElement(By.id("password-confirmation")).sendKeys("Password123!");

        driver.findElement(By.cssSelector("button[title='Create an Account']")).click();

        // Verifying that the account is created successfully
        String expectedUrl = "https://magento.softwaretestingboard.com/customer/account/";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Account creation failed!");
    }
//2.Verify Email Format Validation
    @Test
    public void testEmailFormatValidation() {
    
        driver.findElement(By.linkText("Create an Account")).click();

        driver.findElement(By.id("firstname")).sendKeys("John");
        driver.findElement(By.id("lastname")).sendKeys("Doe");
        driver.findElement(By.id("email_address")).sendKeys("invalidemail@.com");
        driver.findElement(By.id("password")).sendKeys("Password123!");
        driver.findElement(By.id("password-confirmation")).sendKeys("Password123!");

        driver.findElement(By.cssSelector("button[title='Create an Account']")).click();

        // Verifying that the error message is displayed
        String errorMessage = driver.findElement(By.id("email_address-error")).getText();
        Assert.assertTrue(errorMessage.contains("is not a valid email address"), "Email format validation failed!");
    }
//3.Password Strength Validation
    @Test
    public void testPasswordStrengthValidation() {
        // Navigate to the Create Account page
        driver.findElement(By.linkText("Create an Account")).click();

        // Fill in the account creation form with a weak password
        driver.findElement(By.id("firstname")).sendKeys("John");
        driver.findElement(By.id("lastname")).sendKeys("Doe");
        driver.findElement(By.id("email_address")).sendKeys("johndoe" + System.currentTimeMillis() + "@example.com");
        driver.findElement(By.id("password")).sendKeys("123");
        driver.findElement(By.id("password-confirmation")).sendKeys("123");

       
        driver.findElement(By.cssSelector("button[title='Create an Account']")).click();

        // Verifying that the error message is displayed for weak password
        String errorMessage = driver.findElement(By.id("password-error")).getText();
        Assert.assertTrue(errorMessage.contains("Please enter 8 or more characters"), "Password strength validation failed!");
    }
//4.Create an Account with Empty Fields
    @Test
    public void testCreateAccountWithEmptyFields() {
     
        driver.findElement(By.linkText("Create an Account")).click();
      
        driver.findElement(By.cssSelector("button[title='Create an Account']")).click();

        // Verifying error messages for all required fields
        Assert.assertTrue(driver.findElement(By.id("firstname-error")).isDisplayed(), "First name error message not displayed!");
        Assert.assertTrue(driver.findElement(By.id("lastname-error")).isDisplayed(), "Last name error message not displayed!");
        Assert.assertTrue(driver.findElement(By.id("email_address-error")).isDisplayed(), "Email error message not displayed!");
        Assert.assertTrue(driver.findElement(By.id("password-error")).isDisplayed(), "Password error message not displayed!");
    }
//5.Mismatched Password Confirmation
    @Test
    public void testMismatchedPasswordConfirmation() {
       
        driver.findElement(By.linkText("Create an Account")).click();

        driver.findElement(By.id("firstname")).sendKeys("John");
        driver.findElement(By.id("lastname")).sendKeys("Doe");
        driver.findElement(By.id("email_address")).sendKeys("johndoe" + System.currentTimeMillis() + "@example.com");
        driver.findElement(By.id("password")).sendKeys("Password123!");
        driver.findElement(By.id("password-confirmation")).sendKeys("DifferentPassword123!");

        driver.findElement(By.cssSelector("button[title='Create an Account']")).click();

        // Verifying the error message for mismatched passwords
        String errorMessage = driver.findElement(By.id("password-confirmation-error")).getText();
        Assert.assertTrue(errorMessage.contains("Please enter the same value again."), "Password confirmation mismatch error not displayed!");
    }
}
