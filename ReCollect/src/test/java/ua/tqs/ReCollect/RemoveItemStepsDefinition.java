package ua.tqs.ReCollect;
import cucumber.api.java.en.And;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ua.tqs.ReCollect.functionalTest.AnnouncePage;
import ua.tqs.ReCollect.functionalTest.LoginPage;
import ua.tqs.ReCollect.functionalTest.MyAdsPage;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoveItemStepsDefinition {

    private final WebDriver driver = new ChromeDriver();
    private MyAdsPage myAdsPage;
    private LoginPage loginPage;
    private AnnouncePage addItemResultPage;
    private MyAdsPage resultPage;
    private int currentOnSaleCount;

    public void addItem(){

    }

    @Given("Alexandra is a logged in user_")
    public void alexandra_is_a_logged_in_user() {
        driver.get("http://localhost:8080/login");
        loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
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

        announcePage.fillForm("Banda desenhada Marvel", "BOOKS", "produto excelente", "5", "1", "https://cdn.catawiki.net/assets/marketing/stories-images/4757-7326c52efe4952575f17182fdd3944dcb079c2ba-og_image.jpg");
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



    @When("clicks the delete Item button")
    public void clicks_the_delete_Item_button() {
        myAdsPage.deleteItem();
    }


    @Then("disappears from sales list")
    public void disappears_from_sales_list() {
        assertTrue(myAdsPage.onSaleCountDecremented(currentOnSaleCount));
    }


}
