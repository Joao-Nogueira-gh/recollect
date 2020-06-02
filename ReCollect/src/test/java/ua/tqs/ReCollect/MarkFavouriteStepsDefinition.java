package ua.tqs.ReCollect;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ua.tqs.ReCollect.functionalTest.*;

import static org.junit.Assert.assertTrue;

public class MarkFavouriteStepsDefinition {

    private final WebDriver driver = new ChromeDriver();
    private LoginPage loginPage;
    private MyAdsPage myAdsPage;
    private FavItemsPage favItemsPage;
    private HomePage homePage;
    private ProductPage productPage;
    private int favCount;
    private ProductPage productPage2;
    private FavItemsPage favItemsPage2;

    @Given("that I’m a logged-in user")
    public void that_I_m_a_logged_in_user() {
        //Fazer o login
        driver.get("http://localhost:8080/login");
        loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("carlos@mail.com", "carlos");
        // logging in after trying to announce will redirect to announce
        myAdsPage = loginPage.loginNormal();
        assertTrue(myAdsPage.isInitialized());
    }

    @Given("I’m on an on-sale product page")
    public void i_m_on_an_on_sale_product_page() {
        // just get the previous number of favourite items
        driver.get("http://localhost:8080/favourites");
        favItemsPage = new FavItemsPage(driver);
        assertTrue(favItemsPage.isInitialized());

        favCount = favItemsPage.getFavount();

        // remove all possible items in favourites
        if(favCount>0){
            for(int i=1; i==favCount; i++){
                favItemsPage.unFavouriteItem();
            }
        }

        favCount = favItemsPage.getFavount();

        driver.get("http://localhost:8080/");
        homePage = new HomePage(driver);
        assertTrue(homePage.isInitialized());
        productPage = homePage.viewItem();
        assertTrue(productPage.isInitialized());
        assertTrue(productPage.favouriteButtonIsDisplayed());
    }

    @When("I click in add to favorites button")
    public void i_click_in_add_to_favorites_button() {
        productPage2 = productPage.markItemAsFavourite();
    }

    @Then("the button to remove from favourites appears")
    public void the_button_to_remove_from_favourites_appears() {
        assertTrue(productPage2.unFavouriteButtonIsDisplayed());
    }

    @Then("I can see it on my favorites list")
    public void i_can_see_it_on_my_favorites_list() {
        driver.get("http://localhost:8080/favourites");
        favItemsPage2 = new FavItemsPage(driver);
        assertTrue(favItemsPage2.isInitialized());
        assertTrue(favItemsPage2.favCountIncremented(favCount));
        driver.close();
    }


}
