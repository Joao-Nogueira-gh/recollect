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
import static ua.tqs.ReCollect.functionalTest.AddCommentSeleniumTest.COMENTARIO_TESTE_SELENIUM;

public class AddCommentStepsDefinition {

    private final WebDriver driver = new ChromeDriver();


    AnnouncePage announcePage;
    LoginPage loginPage;
    MyAdsPage myAdsPage;
    ProductPage productPage;
    ProductPage productPage1;

    @Given("Alexandra is logged in")
    public void alexandra_is_logged_in() {
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

    @Given("Alexandra is on an on-sale product page")
    public void alexandra_is_on_an_on_sale_product_page() {
        driver.get("http://localhost:8080/profile");
        myAdsPage = new MyAdsPage(driver);
        assertTrue(myAdsPage.isInitialized());

        myAdsPage.viewItem();
        productPage = new ProductPage(driver);
        assertTrue(productPage.isInitialized());
    }

    @When("Alexandra selects the reviews section")
    public void alexandra_selects_the_reviews_section() {
        productPage.clickReviewTab();
    }

    @When("Alexandra writes a comment in the text area")
    public void alexandra_writes_a_comment_in_the_text_area() {
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        assertTrue(productPage.isInitialized());
        productPage.writeComment(COMENTARIO_TESTE_SELENIUM);
    }

    @When("Alexandra clicks the submit button")
    public void alexandra_clicks_the_submit_button() {
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        assertTrue(productPage.isInitialized());
        productPage1 = productPage.submitComment();
        assertTrue(productPage1.isInitialized());
    }

    @Then("the comment is displayed in the platform")
    public void the_comment_is_displayed_in_the_platform() {
        assertThrows(NoSuchElementException.class, productPage1::existsErrorMessage);
        productPage1.clickReviewTab();
        assertTrue(productPage1.commentIsPresent(COMENTARIO_TESTE_SELENIUM));
    }

    @Then("the system shows an error message")
    public void the_system_shows_an_error_message() {
        assertTrue(productPage1.existsErrorMessage());
    }




}
