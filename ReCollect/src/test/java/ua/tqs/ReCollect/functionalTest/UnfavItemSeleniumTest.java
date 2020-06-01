package ua.tqs.ReCollect.functionalTest;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

public class UnfavItemSeleniumTest extends FunctionalTest {

    public UnfavItemSeleniumTest(){
        super();
        setUp();
    }


    @Test
    void unFavItemFromProfile(){
        //Fazer o login
        driver.get("http://localhost:8080/login");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
        // logging in after trying to announce will redirect to announce
        MyAdsPage myAdsPage = loginPage.loginNormal();
        assertTrue(myAdsPage.isInitialized());

        driver.get("http://localhost:8080/favourites");
        FavItemsPage favItemsPage = new FavItemsPage(driver);
        assertTrue(favItemsPage.isInitialized());

        int favCount = favItemsPage.getFavount();
        assertTrue(favCount!=0);

        favItemsPage.deleteItem();
        assertTrue(favItemsPage.favCountDecremented(favCount));

    }

    @Test
    void unFavItemFromProductPage(){
        //Fazer o login
        driver.get("http://localhost:8080/login");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
        // logging in after trying to announce will redirect to announce
        MyAdsPage myAdsPage = loginPage.loginNormal();
        assertTrue(myAdsPage.isInitialized());

        // just get the previous number of favourite items
        driver.get("http://localhost:8080/favourites");
        FavItemsPage favItemsPage = new FavItemsPage(driver);
        assertTrue(favItemsPage.isInitialized());

        int favCount = favItemsPage.getFavount();

        driver.get("http://localhost:8080/");
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isInitialized());
        ProductPage productPage = homePage.viewItem();
        assertTrue(productPage.isInitialized());
        assertTrue(productPage.unFavouriteButtonIsDisplayed());
        ProductPage productPage2 = productPage.unMarkItemAsFavourite();
        assertTrue(productPage2.favouriteButtonIsDisplayed());

        driver.get("http://localhost:8080/favourites");
        FavItemsPage favItemsPage2 = new FavItemsPage(driver);
        assertTrue(favItemsPage2.isInitialized());

        assertTrue(favItemsPage2.favCountDecremented(favCount));

    }
}
