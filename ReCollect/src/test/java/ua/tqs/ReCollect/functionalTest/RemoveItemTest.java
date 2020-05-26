package ua.tqs.ReCollect.functionalTest;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertTrue;

public class RemoveItemTest extends FunctionalTest {

    public RemoveItemTest() {
        super();
        setUp();
    }

    @Test
    public void deleteItemFromMyAds(){
        //Fazer o login
        driver.get("http://localhost:8080/profile");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
        // logging in after trying to announce will redirect to announce
        MyAdsPage myAdsPage = loginPage.loginNormal();

        assertTrue(myAdsPage.isInitialized());
        int currentOnSaleCount = myAdsPage.getOnSaleCount();

        assertTrue(currentOnSaleCount!=0);

        myAdsPage.deleteItem();
        assertTrue(myAdsPage.onSaleCountDecremented(currentOnSaleCount));

    }

    @Test
    public void deleteItemFromSoldItems(){
        //Fazer o login
        driver.get("http://localhost:8080/profile");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
        // logging in after trying to announce will redirect to announce
        MyAdsPage myAdsPage = loginPage.loginNormal();

        assertTrue(myAdsPage.isInitialized());
        driver.get("http://localhost:8080/sold-items");
        SoldItemsPage soldItemsPage = new SoldItemsPage(driver);
        assertTrue(soldItemsPage.isInitialized());

        int soldCount = soldItemsPage.getSoldCount();

        assertTrue(soldCount!=0);

        soldItemsPage.deleteItem();
        assertTrue(soldItemsPage.soldCountDecremented(soldCount));

    }
}
