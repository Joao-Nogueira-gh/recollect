package ua.tqs.ReCollect;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ua.tqs.ReCollect.functionalTest.LoginPage;
import ua.tqs.ReCollect.functionalTest.MyAdsPage;
import ua.tqs.ReCollect.functionalTest.SoldItemsPage;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PutBackOnSaleStepsDefinition  {

    private final WebDriver driver = new ChromeDriver();
    private MyAdsPage myAdsPage;

    private SoldItemsPage soldItemsPage;

    private int currentOnSaleCount;
    private int currentSoldCount;


    @Given("Alexandra is a logged in user___")
    public void alexandra_is_a_logged_in_user___() {
        //Fazer o login
        driver.get("http://localhost:8080/profile");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
        // logging in after trying to announce will redirect to announce
        myAdsPage = loginPage.loginNormal();
    }

    @Given("Alexandra is on profile page__")
    public void alexandra_is_on_profile_page__() {
        assertTrue(myAdsPage.isInitialized());
        driver.get("http://localhost:8080/sold-items");
        soldItemsPage = new SoldItemsPage(driver);
        assertTrue(soldItemsPage.isInitialized());

        currentOnSaleCount = soldItemsPage.getOnSaleCount();
        currentSoldCount = soldItemsPage.getSoldCount();
    }

    @Given("has at least one item in sold items list")
    public void has_at_least_one_item_in_sold_items_list() {
        assertNotEquals(0, currentSoldCount);
    }

    @Given("clicks the put back on sale button")
    public void clicks_the_put_back_on_sale_button() {
        soldItemsPage.putBackOnSale();
    }

    @Then("The product is removed from the sold items list")
    public void the_product_is_removed_from_the_sold_items_list() {
        assertTrue(soldItemsPage.soldCountDecremented(currentSoldCount));
    }

    @Then("the product is added to the on sale items list")
    public void the_product_is_added_to_the_on_sale_items_list() {
        assertTrue(soldItemsPage.onSaleCountIncremented(currentOnSaleCount));
    }

}
