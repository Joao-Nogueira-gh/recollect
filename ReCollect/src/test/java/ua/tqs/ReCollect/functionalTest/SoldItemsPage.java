package ua.tqs.ReCollect.functionalTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SoldItemsPage extends PageObject {

    @FindBy(id = "soldItemsTitle")
    private WebElement soldItemsTitle;

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(id = "on_sale_count")
    private WebElement onSaleCount;

    @FindBy(id = "sold_count")
    private WebElement soldCount;

    @FindBy(id = "delete-item-button")
    private WebElement deleteButton;

    @FindBy(id = "back-sale-item-button")
    private WebElement backSaleItemButton;

    public SoldItemsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInitialized(){
        return soldItemsTitle.isDisplayed();
    }

    public boolean itemIsPresent(String itemTitle){
        String bodyText = body.getText();
        return bodyText.contains(itemTitle);
    }

    public void deleteItem(){
        deleteButton.click();
    }

    public void putBackOnSale(){
        backSaleItemButton.click();
    }

    public boolean soldCountDecremented(int count){
        return Integer.parseInt(soldCount.getText())==count-1;
    }

    public boolean onSaleCountIncremented(int count){
        return Integer.parseInt(onSaleCount.getText())==count+1;
    }

    public int getOnSaleCount() {
        return Integer.parseInt(onSaleCount.getText());
    }

    public int getSoldCount() {
        return Integer.parseInt(soldCount.getText());
    }
}
