package ua.tqs.ReCollect;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ua.tqs.ReCollect.functionalTest.LoginPage;
import ua.tqs.ReCollect.functionalTest.MyAdsPage;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class MarkSoldStepsDefinition {

    private final WebDriver driver = new ChromeDriver();
    private MyAdsPage myAdsPage;
    private LoginPage loginPage;

    private int currentOnSaleCount;
    private int currentSoldCount;

    @Given("Alexandra is a logged in user__")
    public void alexandra_is_a_logged_in_user() {
        driver.get("http://localhost:8080/login");
        loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("carlos@mail.com", "carlos");
        // logging in after trying to announce will redirect to announce
        myAdsPage = loginPage.loginNormal();
    }

    @Given("Alexandra is on profile page_")
    public void alexandra_is_on_profile_page_() {
        assertTrue(myAdsPage.isInitialized());
        currentOnSaleCount = myAdsPage.getOnSaleCount();
        currentSoldCount = myAdsPage.getSoldCount();
    }

    @Given("has at least one item in sales list")
    public void has_at_least_one_item_in_sold_items_list() {
        assertNotEquals(0, currentOnSaleCount);
    }

    @When("clicks the mark as sold button")
    public void clicks_the_mark_as_sold_button() {
        myAdsPage.markItemAsSold();
    }

    @Then("The product is removed from the sales list")
    public void the_product_is_removed_from_the_sales_list() {
        assertTrue(myAdsPage.onSaleCountDecremented(currentOnSaleCount));
    }

    @Then("the product is added to the sold items list")
    public void the_product_is_added_to_the_sold_items_list() {
        assertTrue(myAdsPage.soldCountIncremented(currentSoldCount));
    }



}
