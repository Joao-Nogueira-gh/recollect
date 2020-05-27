package ua.tqs.ReCollect.functionalTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchResultsPage extends PageObject {

    public SearchResultsPage(WebDriver driver){
        super(driver);
    }

    @FindBy(id = "searchResultsHeader")
    private WebElement searchResultsHeader;

    @FindBy(id = "search_button")
    private WebElement searchButton;

    @FindBy(id="category_text")
    private WebElement categoryText;

    @FindBy(id = "results_count")
    private WebElement resultsCount;


    public boolean isInitialized(){
        return searchResultsHeader.isDisplayed();
    }

    public SearchResultsPage search(){
        searchButton.click();
        return new SearchResultsPage(driver);
    }

    public boolean equalsCategory(String category){
        return this.categoryText.getText().equals(category);
    }

    public boolean hasResults(){
        return Integer.parseInt(this.resultsCount.getText())>0;
    }


}
