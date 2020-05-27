package ua.tqs.ReCollect.functionalTest;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SearchByCategorySeleniumTest extends FunctionalTest {

    public SearchByCategorySeleniumTest() {
        super();
        setUp();
    }

    @Test
    public void searchBooksFromHomeSearchBar(){
        driver.get("http://localhost:8080/");
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isInitialized());

        SearchResultsPage searchResultsPage = homePage.search();
        assertTrue(searchResultsPage.isInitialized());

        assertTrue(searchResultsPage.equalsCategory("BOOKS"));
    }

    @Test
    public void searchToysThenMiscFromHomeCategories(){
        driver.get("http://localhost:8080/");
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isInitialized());

        SearchResultsPage searchResultsPage = homePage.searchForToys();
        assertTrue(searchResultsPage.isInitialized());

        assertTrue(searchResultsPage.equalsCategory("TOYS"));

        driver.get("http://localhost:8080/");
        homePage = new HomePage(driver);
        assertTrue(homePage.isInitialized());

        searchResultsPage = homePage.searchForMisc();
        assertTrue(searchResultsPage.isInitialized());

        assertTrue(searchResultsPage.equalsCategory("MISC"));
    }

    @Test
    public void searchBooksFromHomeAfterAddingItem(){
        // login and add an item
        driver.get("http://localhost:8080/announce");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
        // logging in after trying to announce will redirect to announce
        AnnouncePage announcePage = loginPage.loginAfterAnnounce();

        //AnnouncePage announcePage = new AnnouncePage(driver);
        assertTrue(announcePage.isInitialized());

        announcePage.fillForm("Os Lusíadas", "Quarta edição ilustrada", "12,20", "1", "https://www.livrariafernandosantos.com/wp-content/uploads/2016/05/os-lusiadas-grande-edicao.jpg");
        AnnouncePage resultPage = announcePage.postItem();
        assertTrue(resultPage.isInitialized());
        assertTrue(resultPage.submittedSuccess());

        assertThrows(NoSuchElementException.class, resultPage::existsErrorMessage);


        driver.get("http://localhost:8080/");
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isInitialized());

        SearchResultsPage searchResultsPage = homePage.search();
        assertTrue(searchResultsPage.isInitialized());

        assertTrue(searchResultsPage.equalsCategory("BOOKS"));
        assertTrue(searchResultsPage.hasResults());
    }
}
