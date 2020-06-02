package ua.tqs.ReCollect.functionalTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FavItemsPage extends PageObject {

    public FavItemsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "favItemsTitle")
    private WebElement favItemsTitle;

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(id = "favorite_count")
    private WebElement favCount;

    @FindBy(id = "delete-item-button")
    private WebElement deleteButton;

    public boolean isInitialized(){
        return favItemsTitle.isDisplayed();
    }

    public boolean itemIsPresent(String itemTitle){
        String bodyText = body.getText();
        return bodyText.contains(itemTitle);
    }

    public void unFavouriteItem(){
        deleteButton.click();
    }

    public boolean favCountDecremented(int count){
        return Integer.parseInt(favCount.getText())==count-1;
    }

    public boolean favCountIncremented(int count){
        return Integer.parseInt(favCount.getText())==count+1;
    }

    public int getFavount() {
        return Integer.parseInt(favCount.getText());
    }

}
