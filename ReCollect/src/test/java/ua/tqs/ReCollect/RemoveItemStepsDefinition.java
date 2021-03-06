package ua.tqs.ReCollect;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ua.tqs.ReCollect.functionalTest.AnnouncePage;
import ua.tqs.ReCollect.functionalTest.LoginPage;
import ua.tqs.ReCollect.functionalTest.MyAdsPage;
import ua.tqs.ReCollect.functionalTest.SoldItemsPage;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoveItemStepsDefinition {

    private final WebDriver driver = new ChromeDriver();
    private MyAdsPage myAdsPage;
    private LoginPage loginPage;

    private int currentOnSaleCount;
    private SoldItemsPage soldItemsPage;
    private int soldCount;


    @Given("Alexandra is a logged in user_")
    public void alexandra_is_a_logged_in_user() {
        driver.get("http://localhost:8080/login");
        loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("carlos@mail.com", "carlos");
        // logging in after trying to announce will redirect to announce
        myAdsPage = loginPage.loginNormal();
    }

    @Given("Alexandra has at least a product on sales")
    public void userHasAtLeastAProductOnList() {
        //Fazer o login
        driver.get("http://localhost:8080/announce");

        AnnouncePage announcePage = new AnnouncePage(driver);
        //AnnouncePage announcePage = new AnnouncePage(driver);
        assertTrue(announcePage.isInitialized());

        announcePage.fillForm("Banda desenhada Marvel", "produto excelente", "5", "1", "https://cdn.catawiki.net/assets/marketing/stories-images/4757-7326c52efe4952575f17182fdd3944dcb079c2ba-og_image.jpg");
        AnnouncePage addItemResultPage = announcePage.postItem();
        assertTrue(addItemResultPage.isInitialized());
        assertTrue(addItemResultPage.submittedSuccess());

        assertThrows(NoSuchElementException.class, addItemResultPage::existsErrorMessage);
    }


    @Given("Alexandra is on profile page")
    public void alexandra_is_on_his_her_profile_page() {
        driver.get("http://localhost:8080/profile");
        assertTrue(myAdsPage.isInitialized());
        currentOnSaleCount = myAdsPage.getOnSaleCount();
    }



    @When("clicks the delete Item button on sales")
    public void clicks_the_delete_Item_button_on_sales() {
        myAdsPage.deleteItem();
    }


    @Then("disappears from sales list")
    public void disappears_from_sales_list() {
        assertTrue(myAdsPage.onSaleCountDecremented(currentOnSaleCount));
    }


    // Scenario 2

    @Given("Francisco is a logged in user_")
    public void francisco_is_a_logged_in_user_() {
        driver.get("http://localhost:8080/login");
        loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("carlos@mail.com", "carlos");
        // logging in after trying to announce will redirect to announce
        myAdsPage = loginPage.loginNormal();
    }


    @Given("Francisco has at least a product on sold items")
    public void francisco_has_at_least_a_product_on_sold_items() {
        //Fazer o login
        driver.get("http://localhost:8080/announce");

        AnnouncePage announcePage = new AnnouncePage(driver);
        //AnnouncePage announcePage = new AnnouncePage(driver);
        assertTrue(announcePage.isInitialized());

        announcePage.fillForm("Banda desenhada Marvel", "produto excelente", "5", "1", "https://cdn.catawiki.net/assets/marketing/stories-images/4757-7326c52efe4952575f17182fdd3944dcb079c2ba-og_image.jpg");
        AnnouncePage addItemResultPage = announcePage.postItem();
        assertTrue(addItemResultPage.isInitialized());
        assertTrue(addItemResultPage.submittedSuccess());

        assertThrows(NoSuchElementException.class, addItemResultPage::existsErrorMessage);

        driver.get("http://localhost:8080/profile");
        MyAdsPage myAdsPage = new MyAdsPage(driver);

        assertTrue(myAdsPage.isInitialized());
        int currentOnSaleCount = myAdsPage.getOnSaleCount();
        int currentSoldCount = myAdsPage.getSoldCount();

        assertNotEquals(0, currentOnSaleCount);

        assertNotEquals(0, currentOnSaleCount);

        myAdsPage.markItemAsSold();
        assertTrue(myAdsPage.onSaleCountDecremented(currentOnSaleCount));
        assertTrue(myAdsPage.soldCountIncremented(currentSoldCount));
    }


    @Given("Francisco is on profile page")
    public void francisco_is_on_profile_page() {
        driver.get("http://localhost:8080/sold-items");
        soldItemsPage = new SoldItemsPage(driver);
        assertTrue(soldItemsPage.isInitialized());
        soldCount = soldItemsPage.getSoldCount();
    }

    @When("clicks the delete Item button on sold items")
    public void clicks_the_delete_Item_button_on_sold_items() {
        soldItemsPage.deleteItem();
    }


    @Then("disappears from sold items list")
    public void disappears_from_sold_items_list() {
        assertTrue(soldItemsPage.soldCountDecremented(soldCount));
    }


}
