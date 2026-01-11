import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class InvalidLoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    // TODO: change to your application URL
    private static final String BASE_URL = "https://example.com/login";

    // TODO: change locators to match your app
    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("loginBtn");

    // Common options for error message:
    // - By.cssSelector(".error") or By.id("errorMessage") etc.
    private static final By ERROR_MESSAGE = By.cssSelector(".error");

    @BeforeMethod
    public void setUp() {
        // If running locally, make sure chromedriver is set up on your machine
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void invalidLogin_shouldShowErrorMessage() {
        driver.get(BASE_URL);

        // Enter invalid credentials
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME)).sendKeys("wrong_user");
        driver.findElement(PASSWORD).sendKeys("wrong_password");
        driver.findElement(LOGIN_BUTTON).click();

        // Validate: error message is displayed
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
        Assert.assertTrue(error.isDisplayed(), "Error message should be displayed for invalid login.");

        // Optional: validate the error text (adjust expected text)
        String errorText = error.getText().toLowerCase();
        Assert.assertTrue(
                errorText.contains("invalid") || errorText.contains("incorrect") || errorText.contains("failed"),
                "Error text should indicate invalid/incorrect login. Actual: " + error.getText()
        );
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}