package sampletests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * AddToCartTest
 * ----------------
 * This test validates adding a product to cart
 * and verifying cart contents.
 *
 * Demo site used: https://www.saucedemo.com
 */
public class AddToCartTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");
    }

    @Test
    public void addProductToCart() {

        // Login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
                .sendKeys("standard_user");
        driver.findElement(By.id("password"))
                .sendKeys("secret_sauce");
        driver.findElement(By.id("login-button"))
                .click();

        // Verify login success
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "Login failed - inventory page not loaded");

        // Add product to cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        // Verify cart badge count
        String cartCount = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge"))
        ).getText();

        Assert.assertEquals(cartCount, "1", "Cart count is incorrect");

        // Go to cart
        driver.findElement(By.className("shopping_cart_link")).click();

        // Verify product in cart
        String productName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name"))
        ).getText();

        Assert.assertEquals(productName, "Sauce Labs Backpack",
                "Product not added correctly to cart");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}