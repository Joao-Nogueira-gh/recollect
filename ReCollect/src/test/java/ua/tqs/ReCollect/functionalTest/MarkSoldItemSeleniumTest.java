package ua.tqs.ReCollect.functionalTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

class MarkSoldItemSeleniumTest extends FunctionalTest{

    public MarkSoldItemSeleniumTest(){
        super();
        setUp();
    }

    @AfterEach
    void closeBrowser(){
        driver.close();
    }

    @Test
    void markItemAsSold(){
        //Fazer o login
        driver.get("http://localhost:8080/profile");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("carlos@mail.com", "carlos");
        // logging in after trying to announce will redirect to announce
        MyAdsPage myAdsPage = loginPage.loginNormal();

        assertTrue(myAdsPage.isInitialized());
        int currentOnSaleCount = myAdsPage.getOnSaleCount();
        int currentSoldCount = myAdsPage.getSoldCount();

        assertNotEquals(0, currentOnSaleCount);

        myAdsPage.markItemAsSold();

        assertTrue(myAdsPage.onSaleCountDecremented(currentOnSaleCount));
        assertTrue(myAdsPage.soldCountIncremented(currentSoldCount));

    }
}
