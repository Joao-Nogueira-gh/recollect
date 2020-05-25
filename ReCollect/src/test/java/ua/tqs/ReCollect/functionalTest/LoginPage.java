package ua.tqs.ReCollect.functionalTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject {

    @FindBy(id = "login-header")
    private WebElement loginHeader;

    @FindBy(id = "login-error-message")
    private WebElement loginErrorMessage;

    @FindBy(id = "user_name")
    private WebElement userNameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;


    @FindBy(id = "login-button")
    private WebElement loginButton;

    public boolean isInitialized(){
        return loginHeader.isDisplayed();
    }

    public boolean existsErrorMessage(){
        return loginErrorMessage.isDisplayed();
    }


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void fillCredentials(String email, String password){
        this.userNameInput.clear();
        this.passwordInput.clear();

        this.userNameInput.sendKeys(email);
        this.passwordInput.sendKeys(password);
    }

    public AnnouncePage loginAfterAnnounce(){
        loginButton.click();
        return new AnnouncePage(driver);
    }

    public MyAdsPage loginNormal(){
        loginButton.click();
        return new MyAdsPage(driver);
    }
}
