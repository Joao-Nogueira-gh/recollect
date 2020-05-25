package ua.tqs.ReCollect.functionalTest;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertTrue;

public class RemoveItemTest extends FunctionalTest {

    public RemoveItemTest() {
        super();
        setUp();
    }

    @Test
    public void deleteItemSuccess(){
        //Fazer o login
        driver.get("http://localhost:8080/profile");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
        // logging in after trying to announce will redirect to announce
        MyAdsPage myAdsPage = loginPage.loginNormal();

        assertTrue(myAdsPage.isInitialized());
        int currentOnSaleCount = myAdsPage.getOnSaleCount();

        myAdsPage.deleteItem();
        assertTrue(myAdsPage.onSaleCountDecremented(currentOnSaleCount));

    }
}
