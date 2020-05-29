package ua.tqs.ReCollect.functionalTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AnnouncePage extends PageObject {


    @FindBy(id = "announceHeader")
    private WebElement announceHeader;

    @FindBy(id = "invalidItemMessage")
    private WebElement invalidItemMessage;

    @FindBy(id = "itemSubmittedMessage")
    private WebElement itemSubmittedMessage;

    @FindBy(id = "item-title")
    private WebElement itemTitle;

    @FindBy(id = "category")
    private WebElement category;

    @FindBy(id = "description")
    private WebElement description;

    @FindBy(id = "price")
    private WebElement price;

    @FindBy(id = "quantity")
    private WebElement quantity;

    @FindBy(id = "picture-input")
    private WebElement pictureInput;

    @FindBy(id = "post-item-button")
    private WebElement submitButton;

    public boolean isInitialized(){
        return announceHeader.isDisplayed();
    }

    public boolean existsErrorMessage(){
        return invalidItemMessage.isDisplayed();
    }

    public boolean submittedSuccess(){
        return itemSubmittedMessage.isDisplayed();
    }

    public AnnouncePage(WebDriver driver) {
        super(driver);
    }

    public void fillForm(String titulo, String descricao,
                         String preco, String quantidade, String imagem){
        this.itemTitle.clear();
        this.description.clear();
        this.price.clear();
        this.quantity.clear();
        this.pictureInput.clear();

        this.itemTitle.sendKeys(titulo);
        //this.category.selectByVisibleText(categoria);
        this.description.sendKeys(descricao);
        this.price.sendKeys(preco);
        this.quantity.sendKeys(quantidade);
        this.pictureInput.sendKeys(imagem);
    }

    public AnnouncePage postItem(){
        submitButton.click();
        return new AnnouncePage(driver);
    }


}
