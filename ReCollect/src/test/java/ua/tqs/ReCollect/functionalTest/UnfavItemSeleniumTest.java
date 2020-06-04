package ua.tqs.ReCollect.functionalTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class UnfavItemSeleniumTest extends FunctionalTest {

    public UnfavItemSeleniumTest(){
        super();
        setUp();
    }

    @AfterEach
    void closeBrowser(){
        driver.close();
    }


    @Test
    void unFavItemFromProfile(){
        // add item to favourites first to be sure there's an item to unfavourite
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

        // if there's no item in favourites, add an item
        if(favCount==0){
            driver.get("http://localhost:8080/");
            HomePage homePage = new HomePage(driver);
            assertTrue(homePage.isInitialized());
            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            ProductPage productPage = homePage.viewItem();
            assertTrue(productPage.isInitialized());
            assertTrue(productPage.favouriteButtonIsDisplayed());
            ProductPage productPage2 = productPage.markItemAsFavourite();
            assertTrue(productPage2.unFavouriteButtonIsDisplayed());

            driver.get("http://localhost:8080/favourites");
            FavItemsPage favItemsPage2 = new FavItemsPage(driver);
            assertTrue(favItemsPage2.isInitialized());

            assertTrue(favItemsPage2.favCountIncremented(favCount));
            favCount = favItemsPage2.getFavount();
            assertNotEquals(0, favCount);

            favItemsPage2.unFavouriteItem();
            assertTrue(favItemsPage2.favCountDecremented(favCount));
        }
        else{
            favItemsPage.unFavouriteItem();
            assertTrue(favItemsPage.favCountDecremented(favCount));
        }

    }

    @Test
    void unFavItemFromProductPage(){
        // add item to favourites first to be sure there's an item to unfavourite
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

        driver.get("http://localhost:8080/");
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isInitialized());
        ProductPage productPage = homePage.viewItem();
        assertTrue(productPage.isInitialized());
        assertTrue(productPage.favouriteButtonIsDisplayed());
        // first, mark as favourite
        ProductPage productPage2 = productPage.markItemAsFavourite();
        // from the product page already, unmark as favourite
        assertTrue(productPage2.unFavouriteButtonIsDisplayed());
        ProductPage productPage3 = productPage2.unMarkItemAsFavourite();
        assertTrue(productPage3.favouriteButtonIsDisplayed());

        driver.get("http://localhost:8080/favourites");
        FavItemsPage favItemsPage2 = new FavItemsPage(driver);
        assertTrue(favItemsPage2.isInitialized());

        int favCount2 = favItemsPage2.getFavount();

        // after marking and removing the item from favourites, the total of favourites should be the same
        assertEquals(favCount, favCount2);

    }
}
