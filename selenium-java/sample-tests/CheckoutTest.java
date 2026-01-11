import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class CheckoutTest {

    private WebDriver driver;
    private WebDriverWait wait;

    // Replace URLs & locators as per your app
    private static final String BASE_URL = "https://example.com/login";

    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("loginBtn");

    private static final By ADD_TO_CART = By.cssSelector(".add-to-cart");
    private static final By CART_LINK = By.id("cartLink");
    private static final By CHECKOUT_BUTTON = By.id("checkoutBtn");
    private static final By ORDER_SUCCESS = By.cssSelector(".order-success");

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void checkout_shouldCompleteSuccessfully() {
        // Login
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME)).sendKeys("demoUser");
        driver.findElement(PASSWORD).sendKeys("demoPass");
        driver.findElement(LOGIN_BUTTON).click();

        // Add item
        wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART)).click();

        // Go to cart and checkout
        wait.until(ExpectedConditions.elementToBeClickable(CART_LINK)).click();
        wait.until(ExpectedConditions.elementToBeClickable(CHECKOUT_BUTTON)).click();

        // Validate order success
        Assert.assertTrue(
                wait.until(ExpectedConditions.visibilityOfElementLocated(ORDER_SUCCESS)).isDisplayed(),
                "Order should be placed successfully"
        );
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}