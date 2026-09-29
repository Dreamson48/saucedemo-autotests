package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.InventoryPage;
import pages.CartPage;

public class CartTest extends BaseTest {

    @Test
    public void testAddItemToCart() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        Assert.assertTrue(inventoryPage.isInventoryPageLoaded(),
                "Страница Inventory должна загрузиться");

        inventoryPage.addToCart(0);
        String badgeCount = inventoryPage.getCartBadgeCount();
        Assert.assertEquals(badgeCount, "1",
                "Счетчик корзины должен показывать 1");
    }

    @Test
    public void testViewCartItems() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        inventoryPage.addToCart(0);
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(driver, wait);
        Assert.assertTrue(cartPage.isCartPageLoaded(),
                "Страница корзины должна загрузиться");

        String itemTitle = cartPage.getFirstItemTitle();
        Assert.assertNotNull(itemTitle,
                "В корзине должен отображаться товар");
        Assert.assertTrue(itemTitle.contains("Sauce Labs"),
                "Название товара должно содержать 'Sauce Labs'");
    }
}