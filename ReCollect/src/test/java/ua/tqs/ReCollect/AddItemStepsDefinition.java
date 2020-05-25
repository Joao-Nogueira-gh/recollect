package ua.tqs.ReCollect;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ua.tqs.ReCollect.functionalTest.AnnouncePage;
import ua.tqs.ReCollect.functionalTest.LoginPage;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddItemStepsDefinition {

    private final WebDriver driver = new ChromeDriver();
    private AnnouncePage announcePage;
    private LoginPage loginPage;
    private AnnouncePage resultPage;

    @Given("Alexandra is a logged in user")
    public void alexandra_is_a_logged_in_user() {
        driver.get("http://localhost:8080/announce");
        loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
        // loggin in after trying to announce will redirect to announce
        announcePage = loginPage.loginAfterAnnounce();
    }

    @Given("Alexandra is on the Announce page")
    public void alexandra_is_on_the_Announce_page() {
        assertTrue(announcePage.isInitialized());
    }

    @When("Alexandra fills in all the fields with Alexandras product information")
    public void alexandra_fills_in_all_the_fields_with_Alexandras_product_information() {
        announcePage.fillForm("Banda desenhada Marvel", "BOOKS", "produto excelente", "5", "1", "https://cdn.catawiki.net/assets/marketing/stories-images/4757-7326c52efe4952575f17182fdd3944dcb079c2ba-og_image.jpg");
    }

    @When("clicks the Submit Item button")
    public void clicks_the_Submit_Item_button() {
        resultPage = announcePage.postItem();
    }

    @Then("The product should go on sale on the platform")
    public void the_product_should_go_on_sale_on_the_platform() {
        assertTrue(resultPage.isInitialized());
        assertTrue(resultPage.submittedSuccess());

        assertThrows(NoSuchElementException.class, resultPage::existsErrorMessage);
    }

    @After()
    public void closeBrowser() {
        driver.quit();
    }




    @Given("Francisco is a logged in user")
    public void roberto_is_a_logged_in_user() {
        driver.get("http://localhost:8080/announce");
        loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
        // loggin in after trying to announce will redirect to announce
        announcePage = loginPage.loginAfterAnnounce();
    }

    @Given("Francisco is on the Announce page")
    public void roberto_is_on_the_Announce_page() {
        assertTrue(announcePage.isInitialized());
    }

    @When("Francisco fills in some of the fields with Franciscos product information")
    public void roberto_fills_in_some_of_the_fields_with_Franciscos_product_information() {
        announcePage.fillForm("", "BOOKS", "produto excelente", "5", "1", "https://cdn.catawiki.net/assets/marketing/stories-images/4757-7326c52efe4952575f17182fdd3944dcb079c2ba-og_image.jpg");

    }

    @Then("The product should not go on sale on the platform")
    public void the_product_should_not_go_on_sale_on_the_platform() {
        assertTrue(resultPage.isInitialized());
        assertTrue(resultPage.existsErrorMessage());

        assertThrows(NoSuchElementException.class, resultPage::submittedSuccess);
    }

}
