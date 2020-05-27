package ua.tqs.ReCollect.functionalTest;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddItemSeleniumTest extends FunctionalTest {

    public AddItemSeleniumTest() {
        super();
        setUp();
    }

    @Test
    public void submittedValidItem(){
        //Fazer o login
        driver.get("http://localhost:8080/announce");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
        // logging in after trying to announce will redirect to announce
        AnnouncePage announcePage = loginPage.loginAfterAnnounce();

        //AnnouncePage announcePage = new AnnouncePage(driver);
        assertTrue(announcePage.isInitialized());

        announcePage.fillForm("Banda desenhada Marvel", "produto excelente", "5", "1", "https://cdn.catawiki.net/assets/marketing/stories-images/4757-7326c52efe4952575f17182fdd3944dcb079c2ba-og_image.jpg");
        AnnouncePage resultPage = announcePage.postItem();
        assertTrue(resultPage.isInitialized());
        assertTrue(resultPage.submittedSuccess());

        assertThrows(NoSuchElementException.class, resultPage::existsErrorMessage);

    }

    @Test
    public void submittedInvalidItem(){
        //Fazer o login
        driver.get("http://localhost:8080/announce");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
        // logging in after trying to announce will redirect to announce
        AnnouncePage announcePage = loginPage.loginAfterAnnounce();

        assertTrue(announcePage.isInitialized());

        announcePage.fillForm("", "produto excelente", "5", "1", "https://cdn.catawiki.net/assets/marketing/stories-images/4757-7326c52efe4952575f17182fdd3944dcb079c2ba-og_image.jpg");
        AnnouncePage resultPage = announcePage.postItem();
        assertTrue(resultPage.isInitialized());
        assertTrue(resultPage.existsErrorMessage());

        assertThrows(NoSuchElementException.class, resultPage::submittedSuccess);

    }

}
