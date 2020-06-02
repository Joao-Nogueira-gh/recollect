package ua.tqs.ReCollect.functionalTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class RemoveItemSeleniumTest extends FunctionalTest {

    public RemoveItemSeleniumTest() {
        super();
        setUp();
    }

    @AfterEach
    void closeBrowser(){
        driver.close();
    }

    @Test
    void deleteItemFromMyAds(){
        //Fazer o login
        driver.get("http://localhost:8080/profile");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("carlos@mail.com", "carlos");
        // logging in after trying to announce will redirect to announce
        MyAdsPage myAdsPage = loginPage.loginNormal();

        assertTrue(myAdsPage.isInitialized());
        int currentOnSaleCount = myAdsPage.getOnSaleCount();

        assertTrue(currentOnSaleCount!=0);

        myAdsPage.deleteItem();
        assertTrue(myAdsPage.onSaleCountDecremented(currentOnSaleCount));

    }

    @Test
    void deleteItemFromSoldItems(){
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

        int soldCount = soldItemsPage.getSoldCount();

        // add an item to sold list to delete it
        if(soldCount==0){
            driver.get("http://localhost:8080/profile");
            MyAdsPage myAdsPage2 = new MyAdsPage(driver);
            assertTrue(myAdsPage2.isInitialized());
            int currentOnSaleCount = myAdsPage2.getOnSaleCount();
            int currentSoldCount = myAdsPage2.getSoldCount();
            assertNotEquals(0, currentOnSaleCount);
            myAdsPage2.markItemAsSold();
            soldCount = myAdsPage2.getSoldCount();
            System.err.println("soldCount 2 -> " + soldCount);
            assertTrue(myAdsPage.onSaleCountDecremented(currentOnSaleCount));
            assertTrue(myAdsPage.soldCountIncremented(currentSoldCount));
            // return to sold items list
            driver.get("http://localhost:8080/sold-items");
            soldItemsPage = new SoldItemsPage(driver);
        }

        assertNotEquals(0, soldCount);
        soldItemsPage.deleteItem();
        assertTrue(soldItemsPage.soldCountDecremented(soldCount));

    }
}
