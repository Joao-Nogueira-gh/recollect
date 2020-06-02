package ua.tqs.ReCollect.functionalTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

class PutBackOnSaleItemSeleniumTest extends FunctionalTest {


    public PutBackOnSaleItemSeleniumTest(){
        super();
        setUp();
    }

    @AfterEach
    void closeBrowser(){
        driver.close();
    }

    @Test
    void putItemBackOnSale(){
        //Fazer o login
        driver.get("http://localhost:8080/profile");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("carlos@mail.com", "carlos");
        // logging in after trying to announce will redirect to announce
        MyAdsPage myAdsPage = loginPage.loginNormal();

        assertTrue(myAdsPage.isInitialized());

        driver.get("http://localhost:8080/sold-items");
        SoldItemsPage soldItemsPage = new SoldItemsPage(driver);
        assertTrue(soldItemsPage.isInitialized());

        int currentOnSaleCount = soldItemsPage.getOnSaleCount();
        int currentSoldCount = soldItemsPage.getSoldCount();
        System.err.println("sale count -> " + currentOnSaleCount);
        System.err.println("sold count -> " + currentSoldCount);

        assertNotEquals(0, currentSoldCount);

        soldItemsPage.putBackOnSale();

        assertTrue(soldItemsPage.onSaleCountIncremented(currentOnSaleCount));
        assertTrue(soldItemsPage.soldCountDecremented(currentSoldCount));

    }
}
