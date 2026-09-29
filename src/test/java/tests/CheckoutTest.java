package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.InventoryPage;
import pages.CartPage;
import pages.CheckoutPage;

public class CheckoutTest extends BaseTest {

    @Test
    public void testCompleteCheckout() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        inventoryPage.addToCart(0);
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(driver, wait);
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver, wait);
        checkoutPage.enterFirstName("John");
        checkoutPage.enterLastName("Doe");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        String completeHeader = checkoutPage.getCompleteHeader();
        Assert.assertEquals(completeHeader, "Thank you for your order!",
                "Заголовок должен быть 'Thank you for your order!'");

        String completeText = checkoutPage.getCompleteText();
        Assert.assertTrue(completeText.contains("Your order has been dispatched"),
                "Текст должен содержать 'Your order has been dispatched'");
    }
}