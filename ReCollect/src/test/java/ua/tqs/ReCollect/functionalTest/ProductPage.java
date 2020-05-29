package ua.tqs.ReCollect.functionalTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends PageObject {
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "invalidCommentMessage")
    private WebElement invalidCommentMessage;

    @FindBy(id = "product-title-header")
    private WebElement productTitleHeader;

    @FindBy(id = "pills-reviews-tab")
    private WebElement reviewsTab;

    @FindBy(id = "submit-comment-button")
    private WebElement addCommentButton;

    @FindBy(id = "review-textarea")
    private WebElement reviewArea;

    @FindBy(id = "comment-text-commentTest")
    private WebElement comentarioTesteText;

    @FindBy(id = "comment-text-commentDelete")
    private WebElement comentarioDeleteText;

    @FindBy(id = "comment-delete-commentDelete")
    private WebElement deleteCommentButton;

    public boolean isInitialized(){
        return productTitleHeader.isDisplayed();
    }


    public boolean existsErrorMessage(){
        return invalidCommentMessage.isDisplayed();
    }

    public boolean commentIsPresent(String conteudo){
        return comentarioTesteText.getAttribute("innerText").equals(conteudo);
    }

    public boolean commentDeletedIsPresent(String conteudo){
        return comentarioDeleteText.getAttribute("innerText").equals(conteudo);
    }

    public void clickReviewTab(){
        reviewsTab.click();
    }

    public ProductPage submitComment(){
        addCommentButton.click();
        return new ProductPage(driver);
    }

    public void writeComment(String conteudo){
        this.reviewArea.click();
        this.reviewArea.clear();
        reviewArea.sendKeys(conteudo);
    }

    public ProductPage deleteComment(){
        deleteCommentButton.click();
        return new ProductPage(driver);
    }

    public boolean commentDeletedIsPresent() {
        return comentarioDeleteText.isDisplayed();
    }
}
