package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.InventoryPage;

public class LoginTest extends BaseTest {

    @Test
    public void testPositiveLogin() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isInventoryPageDisplayed(),
                "После успешного входа должна открыться страница Inventory");
    }

    @Test
    public void testNegativeLoginLockedUser() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.enterUsername("locked_out_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Epic sadface"),
                "Должно появиться сообщение об ошибке: Epic sadface...");
        Assert.assertTrue(errorMessage.contains("locked out"),
                "Сообщение должно содержать 'locked out'");
    }

    @Test
    public void testNegativeLoginEmptyPassword() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.enterUsername("standard_user");
        loginPage.clickLogin();

        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Password is required"),
                "Должно появиться сообщение 'Password is required'");
    }
}