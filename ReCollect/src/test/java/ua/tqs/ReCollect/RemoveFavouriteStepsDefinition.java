package ua.tqs.ReCollect;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ua.tqs.ReCollect.functionalTest.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RemoveFavouriteStepsDefinition {

    private final WebDriver driver = new ChromeDriver();
    private LoginPage loginPage;
    private MyAdsPage myAdsPage;
    private FavItemsPage favItemsPage;
    private FavItemsPage favItemsPage2;
    private HomePage homePage;
    private ProductPage productPage;
    private ProductPage productPage2;
    private int favCount;
    private ProductPage productPage3;

    // SCENARIO 1

    @Given("that I’m a logged-in user_")
    public void that_I_m_a_logged_in_user_() {
        //Fazer o login
        driver.get("http://localhost:8080/login");
        loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("carlos@mail.com", "carlos");
        // logging in after trying to announce will redirect to announce
        myAdsPage = loginPage.loginNormal();
        assertTrue(myAdsPage.isInitialized());
    }

    @Given("I’m on my profile page")
    public void i_m_on_my_profile_page() {
        driver.get("http://localhost:8080/favourites");
        favItemsPage = new FavItemsPage(driver);
        assertTrue(favItemsPage.isInitialized());

        // add item to favourites first to be sure there's an item to unfavourite

        favCount = favItemsPage.getFavount();

    }

    @When("I click the delete Item button on my favourites list")
    public void i_click_the_delete_item_button_on_my_favorites_list() {
        // if there's no item in favourites, add an item
        if(favCount==0){
            driver.get("http://localhost:8080/");
            homePage = new HomePage(driver);
            assertTrue(homePage.isInitialized());
            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            productPage = homePage.viewItem();
            assertTrue(productPage.isInitialized());
            assertTrue(productPage.favouriteButtonIsDisplayed());
            productPage2 = productPage.markItemAsFavourite();
            assertTrue(productPage2.unFavouriteButtonIsDisplayed());

            driver.get("http://localhost:8080/favourites");
            favItemsPage2 = new FavItemsPage(driver);
            assertTrue(favItemsPage2.isInitialized());

            assertTrue(favItemsPage2.favCountIncremented(favCount));
            favCount = favItemsPage2.getFavount();
            assertNotEquals(favCount,0);

            favItemsPage2.unFavouriteItem();
            assertTrue(favItemsPage2.favCountDecremented(favCount));
        }
        else{
            favItemsPage.unFavouriteItem();
            assertTrue(favItemsPage.favCountDecremented(favCount));
        }
    }


    @Then("my product is removed from the list")
    public void my_product_is_removed_from_the_list() {
        assertTrue(favItemsPage.favCountDecremented(favCount));
        driver.close();
    }




    // SCENARIO 2

    @Given("I’m on an on-sale product page_")
    public void i_m_on_an_on_sale_product_page_() {
        // add item to favourites first to be sure there's an item to unfavourite
        // just get the previous number of favourite items
        driver.get("http://localhost:8080/favourites");
        favItemsPage = new FavItemsPage(driver);
        assertTrue(favItemsPage.isInitialized());

        favCount = favItemsPage.getFavount();

        driver.get("http://localhost:8080/");
        homePage = new HomePage(driver);
        assertTrue(homePage.isInitialized());
        productPage = homePage.viewItem();
        assertTrue(productPage.isInitialized());
    }

    @When("I click the remove from favourites button")
    public void i_click_the_remove_from_favourites_button() {
        assertTrue(productPage.favouriteButtonIsDisplayed());
        // first, mark as favourite
        productPage2 = productPage.markItemAsFavourite();
        // from the product page already, unmark as favourite
        assertTrue(productPage2.unFavouriteButtonIsDisplayed());
        productPage3 = productPage2.unMarkItemAsFavourite();
    }

    @Then("the button to mark as favourite appears")
    public void the_button_to_mark_as_favourite_appears() {
        assertTrue(productPage3.favouriteButtonIsDisplayed());
    }

    @Then("the product disappears from my favorite list")
    public void the_product_disappears_from_my_favorite_list() {
        driver.get("http://localhost:8080/favourites");
        favItemsPage2 = new FavItemsPage(driver);
        assertTrue(favItemsPage2.isInitialized());
        int favCount2 = favItemsPage2.getFavount();
        // after marking and removing the item from favourites, the total of favourites should be the same
        assertEquals(favCount, favCount2);
        driver.close();
    }




}
