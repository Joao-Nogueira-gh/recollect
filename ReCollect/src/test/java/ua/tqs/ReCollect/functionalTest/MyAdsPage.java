package ua.tqs.ReCollect.functionalTest;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAdsPage extends PageObject {

    @FindBy(id = "myAdsTitle")
    private WebElement myAdsTitle;

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(id = "on_sale_count")
    private WebElement onSaleCount;

    @FindBy(id = "sold_count")
    private WebElement soldCount;

    @FindBy(id = "delete-item-button")
    private WebElement deleteButton;

    @FindBy(id = "mark-sold-button")
    private WebElement markSoldButton;

    public MyAdsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInitialized(){
        return myAdsTitle.isDisplayed();
    }

    public boolean itemIsPresent(String itemTitle){
        String bodyText = body.getText();
        return bodyText.contains(itemTitle);
    }

    public void deleteItem(){
        deleteButton.click();
    }

    public boolean onSaleCountDecremented(int count){
        return Integer.parseInt(onSaleCount.getText())==count-1;
    }

    public boolean soldCountIncremented(int count){
        return Integer.parseInt(soldCount.getText())==count+1;
    }

    public int getOnSaleCount() {
        return Integer.parseInt(onSaleCount.getText());
    }

    public int getSoldCount() {
        return Integer.parseInt(soldCount.getText());
    }
}
