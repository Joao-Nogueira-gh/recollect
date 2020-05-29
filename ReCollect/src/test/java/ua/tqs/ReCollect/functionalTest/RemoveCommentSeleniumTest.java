package ua.tqs.ReCollect.functionalTest;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoveCommentSeleniumTest extends FunctionalTest {

    public static final String COMENTARIO_TESTE_SELENIUM_DELETE = "commentDelete";


    public RemoveCommentSeleniumTest() {
        super();
        setUp();
    }

    @Test
    public void removeComment(){
        //Fazer o login
        driver.get("http://localhost:8080/announce");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("alex@email.pt", "pass");
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
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        // write a comment to delete
        productPage.writeComment(COMENTARIO_TESTE_SELENIUM_DELETE);
        ProductPage productPage1 = productPage.submitComment();
        assertTrue(productPage1.isInitialized());
        assertThrows(NoSuchElementException.class, productPage1::existsErrorMessage);

        productPage1.clickReviewTab();
        assertTrue(productPage1.commentDeletedIsPresent(COMENTARIO_TESTE_SELENIUM_DELETE));

        ProductPage productPage2 = productPage1.deleteComment();
        assertThrows(NoSuchElementException.class, productPage2::commentDeletedIsPresent);
    }
}
