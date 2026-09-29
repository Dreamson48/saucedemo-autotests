package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class InventoryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By inventoryItems = By.cssSelector(".inventory_item");
    private By cartBadge = By.cssSelector(".shopping_cart_badge");
    private By cartLink = By.cssSelector("[data-test='shopping-cart-link']");

    public InventoryPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isInventoryPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryItems));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<WebElement> getInventoryItems() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(inventoryItems));
        return driver.findElements(inventoryItems);
    }

    public void addToCart(int itemIndex) {
        List<WebElement> items = getInventoryItems();
        WebElement item = items.get(itemIndex);

        By addButton = By.xpath(".//button[contains(text(), 'Add to cart')]");
        List<WebElement> addButtons = item.findElements(addButton);

        if (!addButtons.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(addButtons.get(0)));
            addButtons.get(0).click();
        }
    }

    public String getCartBadgeCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
        return driver.findElement(cartBadge).getText();
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink));
        WebElement cartElement = driver.findElement(cartLink);

        // Используем JavaScript для надёжного клика
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartElement);

        // Ждём перехода на страницу корзины
        wait.until(ExpectedConditions.urlContains("/cart.html"));
    }
}