package ua.tqs.ReCollect.functionalTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageObject {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "product-card-title")
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
