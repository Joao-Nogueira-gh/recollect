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
import ua.tqs.ReCollect.functionalTest.ProductPage;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.tqs.ReCollect.functionalTest.RemoveCommentSeleniumTest.COMENTARIO_TESTE_SELENIUM_DELETE;

public class RemoveCommentStepsDefinition {

    private final WebDriver driver = new ChromeDriver();

    AnnouncePage announcePage;
    LoginPage loginPage;
    MyAdsPage myAdsPage;
    ProductPage productPage;
    ProductPage productPage1;
    ProductPage productPage2;

    @Given("Francisco is logged in")
    public void francisco_is_logged_in() {
        //Fazer o login
        driver.get("http://localhost:8080/announce");
        loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.fillCredentials("carlos@mail.com", "carlos");
        // logging in after trying to announce will redirect to announce
        announcePage = loginPage.loginAfterAnnounce();
        assertTrue(announcePage.isInitialized());

        // just to guarantee that there's at least a product in the platform to leave a comment on
        announcePage.fillForm("Banda desenhada Marvel", "produto excelente", "5", "1", "https://cdn.catawiki.net/assets/marketing/stories-images/4757-7326c52efe4952575f17182fdd3944dcb079c2ba-og_image.jpg");
        AnnouncePage resultPage = announcePage.postItem();
        assertTrue(resultPage.isInitialized());
        assertTrue(resultPage.submittedSuccess());
    }

    @Given("Francisco is on an on-sale product page")
    public void francisco_is_on_an_on_sale_product_page() {
        driver.get("http://localhost:8080/profile");
        myAdsPage = new MyAdsPage(driver);
        assertTrue(myAdsPage.isInitialized());

        myAdsPage.viewItem();
        productPage = new ProductPage(driver);
        assertTrue(productPage.isInitialized());
    }

    @When("Francisco selects the reviews section")
    public void francisco_selects_the_reviews_section() {
        productPage.clickReviewTab();
    }

    @When("Francisco clicks the delete button on one of his comments")
    public void francisco_clicks_the_delete_button_on_one_of_his_comments() {
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        // write a comment to delete
        productPage.writeComment(COMENTARIO_TESTE_SELENIUM_DELETE);
        productPage1 = productPage.submitComment();
        assertTrue(productPage1.isInitialized());
        productPage1.clickReviewTab();
        assertTrue(productPage1.commentDeletedIsPresent(COMENTARIO_TESTE_SELENIUM_DELETE));

        productPage2 = productPage1.deleteComment();
    }

    @Then("the comment disappears from the platform")
    public void the_comment_disappears_from_the_platform() {
        assertThrows(NoSuchElementException.class, productPage2::commentDeletedIsPresent);
    }



}
