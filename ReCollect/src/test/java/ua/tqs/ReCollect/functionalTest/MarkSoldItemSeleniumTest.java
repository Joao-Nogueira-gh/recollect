package ua.tqs.ReCollect.functionalTest;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

public class MarkSoldItemSeleniumTest extends FunctionalTest{

    public MarkSoldItemSeleniumTest(){
        super();
        setUp();
    }

    @Test
    public void markItemAsSold(){
        //Fazer o login
        driver.get("http://localhost:8080/profile");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
        // logging in after trying to announce will redirect to announce
        MyAdsPage myAdsPage = loginPage.loginNormal();

        assertTrue(myAdsPage.isInitialized());
        int currentOnSaleCount = myAdsPage.getOnSaleCount();
        int currentSoldCount = myAdsPage.getSoldCount();

        assertTrue(currentOnSaleCount!=0);

        myAdsPage.markItemAsSold();

        assertTrue(myAdsPage.onSaleCountDecremented(currentOnSaleCount));
        assertTrue(myAdsPage.soldCountIncremented(currentSoldCount));

    }
}
