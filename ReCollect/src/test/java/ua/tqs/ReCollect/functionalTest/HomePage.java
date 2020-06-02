package ua.tqs.ReCollect.functionalTest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class HomePage extends PageObject {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "product-card-title-2") // get the last visible element of carousel, initially
    private WebElement productCard;

    @FindBy(id = "search_button_home")
    private WebElement searchButton;

    @FindBy(id = "home_header")
    private WebElement homeHeader;

    @FindBy(id = "button_TOYS")
    private WebElement toysButton;

    @FindBy(id = "button_MISC")
    private WebElement miscButton;

    public boolean isInitialized(){
        return homeHeader.isDisplayed();
    }

    public SearchResultsPage search(){
        searchButton.click();
        return new SearchResultsPage(driver);
    }

    public ProductPage viewItem(){
        //JavascriptExecutor jse2 = (JavascriptExecutor)driver;
        //jse2.executeScript("arguments[0].scrollIntoView()", productCard);
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //WebDriverWait wait = new WebDriverWait(driver, 10);
        //productCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("product-card-title")));
        productCard.click();
        return new ProductPage(driver);
    }

    public SearchResultsPage searchForToys(){
        toysButton.click();
        return new SearchResultsPage(driver);
    }

    public SearchResultsPage searchForMisc(){
        miscButton.click();
        return new SearchResultsPage(driver);
    }


}
