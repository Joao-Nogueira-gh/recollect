package ua.tqs.ReCollect.functionalTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

class AddFavItemSeleniumTest extends FunctionalTest {

    public AddFavItemSeleniumTest(){
        super();
        setUp();
    }

    @AfterEach
    void closeBrowser(){
        driver.close();
    }


    @Test
    void addFavItem(){
        //Fazer o login
        driver.get("http://localhost:8080/login");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("carlos@mail.com", "carlos");
        // logging in after trying to announce will redirect to announce
        MyAdsPage myAdsPage = loginPage.loginNormal();
        assertTrue(myAdsPage.isInitialized());

        // just get the previous number of favourite items
        driver.get("http://localhost:8080/favourites");
        FavItemsPage favItemsPage = new FavItemsPage(driver);
        assertTrue(favItemsPage.isInitialized());

        int favCount = favItemsPage.getFavount();

        // remove all possible items in favourites
        if(favCount>0){
            for(int i=1; i==favCount; i++){
                favItemsPage.unFavouriteItem();
            }
        }

        favCount = favItemsPage.getFavount();

        driver.get("http://localhost:8080/");
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isInitialized());
        ProductPage productPage = homePage.viewItem();
        assertTrue(productPage.isInitialized());
        assertTrue(productPage.favouriteButtonIsDisplayed());
        ProductPage productPage2 = productPage.markItemAsFavourite();
        assertTrue(productPage2.unFavouriteButtonIsDisplayed());

        driver.get("http://localhost:8080/favourites");
        FavItemsPage favItemsPage2 = new FavItemsPage(driver);
        assertTrue(favItemsPage2.isInitialized());

        assertTrue(favItemsPage2.favCountIncremented(favCount));


    }

}
