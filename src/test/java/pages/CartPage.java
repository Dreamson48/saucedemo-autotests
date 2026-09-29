package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By cartItems = By.cssSelector(".cart_item");
    private By checkoutButton = By.id("checkout");
    private By pageTitle = By.cssSelector(".title");
    private By itemTitle = By.cssSelector("[data-test='inventory-item-name']");
    private By itemPrice = By.cssSelector("[data-test='inventory-item-price']");

    public CartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isCartPageLoaded() {
        try {
            // Ждем заголовок страницы, это надежнее, чем ждать список товаров
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<WebElement> getCartItems() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartItems));
        return driver.findElements(cartItems);
    }

    public String getFirstItemTitle() {
        List<WebElement> items = getCartItems();
        return items.get(0).findElement(itemTitle).getText();
    }

    public String getFirstItemPrice() {
        List<WebElement> items = getCartItems();
        return items.get(0).findElement(itemPrice).getText();
    }

    public void clickCheckout() {
        // Убеждаемся, что мы на странице корзины, прежде чем кликать
        wait.until(ExpectedConditions.urlContains("/cart.html"));
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        driver.findElement(checkoutButton).click();

        // ЖДЕМ перехода на следующую страницу (Checkout: Your Information)
        wait.until(ExpectedConditions.urlContains("/checkout-step-one.html"));
    }
}