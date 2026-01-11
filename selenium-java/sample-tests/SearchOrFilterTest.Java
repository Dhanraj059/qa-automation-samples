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
import java.util.List;

public class SearchOrFilterTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "https://example.com/products";

    private static final By SEARCH_BOX = By.id("searchInput");
    private static final By SEARCH_BUTTON = By.id("searchBtn");
    private static final By PRODUCT_LIST = By.cssSelector(".product-item");

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void search_shouldReturnRelevantResults() {
        driver.get(BASE_URL);

        // Search product
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_BOX))
                .sendKeys("Laptop");
        driver.findElement(SEARCH_BUTTON).click();

        // Validate results
        List<?> results = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_LIST)
        );

        Assert.assertTrue(results.size() > 0, "Search should return results");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}