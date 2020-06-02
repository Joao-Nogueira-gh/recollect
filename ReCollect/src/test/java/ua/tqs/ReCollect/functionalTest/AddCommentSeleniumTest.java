package ua.tqs.ReCollect.functionalTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddCommentSeleniumTest extends FunctionalTest {

    public static final String COMENTARIO_TESTE_SELENIUM = "commentTest";

    public AddCommentSeleniumTest() {
        super();
        setUp();
    }

    @AfterEach
    void closeBrowser(){
        driver.close();
    }

    @Test
    void submittedValidComment(){

        //Fazer o login
        driver.get("http://localhost:8080/announce");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("carlos@mail.com", "carlos");
        // logging in after trying to announce will redirect to announce
        AnnouncePage announcePage = loginPage.loginAfterAnnounce();
        assertTrue(announcePage.isInitialized());

        announcePage.fillForm("Banda desenhada Marvel", "produto excelente", "5", "1", "https://cdn.catawiki.net/assets/marketing/stories-images/4757-7326c52efe4952575f17182fdd3944dcb079c2ba-og_image.jpg");
        AnnouncePage resultPage = announcePage.postItem();
        assertTrue(resultPage.isInitialized());
        assertTrue(resultPage.submittedSuccess());

        driver.get("http://localhost:8080/profile");
        MyAdsPage myAdsPage = new MyAdsPage(driver);
        assertTrue(myAdsPage.isInitialized());

        myAdsPage.viewItem();
        ProductPage productPage = new ProductPage(driver);
        assertTrue(productPage.isInitialized());
        productPage.clickReviewTab();
        productPage.writeComment(COMENTARIO_TESTE_SELENIUM);
        ProductPage productPage1 = productPage.submitComment();
        assertTrue(productPage1.isInitialized());
        assertThrows(NoSuchElementException.class, productPage1::existsErrorMessage);

        productPage1.clickReviewTab();
        assertTrue(productPage1.commentIsPresent(COMENTARIO_TESTE_SELENIUM));

    }

    @Test
    void submittedInvalidComment(){

        //Fazer o login
        driver.get("http://localhost:8080/announce");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("carlos@mail.com", "carlos");
        // logging in after trying to announce will redirect to announce
        AnnouncePage announcePage = loginPage.loginAfterAnnounce();
        assertTrue(announcePage.isInitialized());

        announcePage.fillForm("Banda desenhada Marvel", "produto excelente", "5", "1", "https://cdn.catawiki.net/assets/marketing/stories-images/4757-7326c52efe4952575f17182fdd3944dcb079c2ba-og_image.jpg");
        AnnouncePage resultPage = announcePage.postItem();
        assertTrue(resultPage.isInitialized());
        assertTrue(resultPage.submittedSuccess());

        driver.get("http://localhost:8080/profile");
        MyAdsPage myAdsPage = new MyAdsPage(driver);
        assertTrue(myAdsPage.isInitialized());

        myAdsPage.viewItem();
        ProductPage productPage = new ProductPage(driver);
        assertTrue(productPage.isInitialized());
        productPage.clickReviewTab();
        ProductPage productPage1 = productPage.submitComment();
        assertTrue(productPage1.isInitialized());
        assertTrue(productPage1.existsErrorMessage());

    }
}
