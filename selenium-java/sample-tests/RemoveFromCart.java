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

public class RemoveFromCartTest {

    private WebDriver driver;
    private WebDriverWait wait;

    // TODO: Replace with your app pages
    private static final String BASE_URL = "https://example.com/login";

    // TODO: Replace locators as per your application
    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("loginBtn");

    private static final By PRODUCTS_LINK = By.id("productsLink");     // or menu link
    private static final By FIRST_PRODUCT_ADD_BTN = By.cssSelector(".add-to-cart"); // first product add button
    private static final By CART_LINK = By.id("cartLink");

    private static final By CART_ITEM = By.cssSelector(".cart-item");          // any cart item row
    private static final By REMOVE_BTN = By.cssSelector(".cart-item .remove"); // remove button inside item
    private static final By EMPTY_CART_MSG = By.cssSelector(".cart-empty");    // "cart is empty" message

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void removeFromCart_shouldRemoveItemSuccessfully() {
        // Login
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME)).sendKeys("demoUser");
        driver.findElement(PASSWORD).sendKeys("demoPass");
        driver.findElement(LOGIN_BUTTON).click();

        // Go to products and add one item
        wait.until(ExpectedConditions.elementToBeClickable(PRODUCTS_LINK)).click();
        wait.until(ExpectedConditions.elementToBeClickable(FIRST_PRODUCT_ADD_BTN)).click();

        // Go to cart
        wait.until(ExpectedConditions.elementToBeClickable(CART_LINK)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(CART_ITEM));

        // Remove item
        wait.until(ExpectedConditions.elementToBeClickable(REMOVE_BTN)).click();

        // Validate cart is empty OR item count reduced
        // Option 1: check empty message
        boolean emptyShown = driver.findElements(EMPTY_CART_MSG).size() > 0;

        // Option 2: check no cart items
        boolean noItems = driver.findElements(CART_ITEM).isEmpty();

        Assert.assertTrue(emptyShown || noItems, "Item should be removed and cart should be empty (or items reduced).");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}