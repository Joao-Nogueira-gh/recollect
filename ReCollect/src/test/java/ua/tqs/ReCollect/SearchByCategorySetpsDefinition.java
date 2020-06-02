package ua.tqs.ReCollect;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ua.tqs.ReCollect.functionalTest.AnnouncePage;
import ua.tqs.ReCollect.functionalTest.HomePage;
import ua.tqs.ReCollect.functionalTest.LoginPage;
import ua.tqs.ReCollect.functionalTest.SearchResultsPage;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SearchByCategorySetpsDefinition {

    private final WebDriver driver = new ChromeDriver();

    HomePage homePage;
    SearchResultsPage searchResultsPage;
    LoginPage loginPage;
    AnnouncePage announcePage;
    AnnouncePage resultPage;

    @Given("Francisco is in the home page")
    public void francisco_is_in_the_home_page() {
        driver.get("http://localhost:8080/");
        homePage = new HomePage(driver);
        assertTrue(homePage.isInitialized());


    }


    @When("Francisco clicks the search button")
    public void francisco_clicks_the_search_button() {
        searchResultsPage = homePage.search();
    }


    @Then("he sees the search results page")
    public void he_sees_the_search_results_page() {
        assertTrue(searchResultsPage.isInitialized());
    }


    @Then("results are for BOOKS")
    public void results_are_for_BOOKS() {
        assertTrue(searchResultsPage.equalsCategory("BOOKS"));
    }


    // another scenario
    @Given("at least one BOOKS product exists")
    public void at_least_one_BOOKS_product_exists() {
        // login and add an item
        driver.get("http://localhost:8080/announce");
        loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("carlos@mail.com", "carlos");
        // logging in after trying to announce will redirect to announce
        announcePage = loginPage.loginAfterAnnounce();

        //AnnouncePage announcePage = new AnnouncePage(driver);
        assertTrue(announcePage.isInitialized());

        announcePage.fillForm("Os Lusíadas", "Quarta edição ilustrada", "12,20", "1", "https://www.livrariafernandosantos.com/wp-content/uploads/2016/05/os-lusiadas-grande-edicao.jpg");
        resultPage = announcePage.postItem();
        assertTrue(resultPage.isInitialized());
        assertTrue(resultPage.submittedSuccess());

        assertThrows(NoSuchElementException.class, resultPage::existsErrorMessage);
    }

    @Then("number of results is more than zero")
    public void number_of_results_is_more_than_zero() {
        assertTrue(searchResultsPage.hasResults());
    }


    // search from home buttons


    @When("Francisco clicks the search for TOYS button")
    public void francisco_clicks_the_search_for_TOYS_button() {
        searchResultsPage = homePage.searchForToys();
        assertTrue(searchResultsPage.isInitialized());
    }

    @Then("results are for TOYS")
    public void results_are_for_TOYS() {
        assertTrue(searchResultsPage.equalsCategory("TOYS"));
    }

    @When("Francisco clicks the search for MISC button")
    public void francisco_clicks_the_search_for_MISC_button() {
        searchResultsPage = homePage.searchForMisc();
        assertTrue(searchResultsPage.isInitialized());
    }

    @Then("results are for MISC")
    public void results_are_for_MISC() {
        assertTrue(searchResultsPage.equalsCategory("MISC"));
    }
}
