package com.softserve.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class HomePage extends BasePage {

    @FindBy(id = "logo-icon")
    protected WebElement logo;

    @FindBy(css = "#container>#search-input #search")
    protected WebElement searchField;

    @FindBy(id = "search-icon-legacy")
    protected WebElement searchButton;

    @FindBy(css = "#contents>ytd-video-renderer")
    protected List<WebElement> searchResults;

    @FindBy(css = "ytd-app #content>#page-manager #header-container #filter-menu")
    protected WebElement filterMenu;

    public HomePage(WebDriver driver) {
        super(driver);
    }

//    @Step(value = "open URL address {0}")
    public void openHomePage(String url) {
        driver.get(url);
    }

//    @Step(value = "check if Search field is visible")
    public boolean isSearchFieldVisible() {
        logger.info("check if Search field is visible");
        return searchField.isDisplayed();
    }

//    @Step(value = "check if Search button is visible")
    public boolean isSearchButtonVisible() {
        logger.info("check if Search button is visible");
        return searchButton.isDisplayed();
    }

//    @Step(value = "enter text to SearchField: {0}")
    public void enterTextToSearchField(final String searchText) {
//        searchField.clear();
        logger.info("input in Serach field text " + searchText);
        searchField.sendKeys(searchText);
    }

//    @Step(value = "click to Search Button")
    public void clickSearchButton() {
        logger.info("click to Search Button");
        searchButton.click();
    }

//    @Step(value = "enter text to SearchField: {0} and click Search")
    public void enterTextToSearchFieldAndClickSearch(final String searchText) {
        logger.info("start Search text " + searchText);
        enterTextToSearchField(searchText);
        clickSearchButton();
    }

//    @Step(value = "check if Search results are displayed")
    public boolean isSearchResultsDisplayed() {
        logger.info("check if Search results are displayed");
        wait.visibilityOfWebElement(filterMenu);
        return searchResults.size() > 0;
    }

//    @Step(value = "check if Search result titles have words: {0}")
    public boolean isResultTitlesHaveWords(final String searchText) {
        logger.info("check if Search result titles have words: " + searchText);
        String[] words = searchText.split(" ");
        for (String word : words) {
            for (WebElement result : searchResults) {
                String title = result.findElement(By.cssSelector("#title-wrapper #video-title")).getText();
                if (title.toLowerCase().contains(word.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }
    
//    @Step(value = "get Result List Of Searching by keyword")
    public List<SearchVideoResult> getResultListOfSearch(){
        logger.info("get Result List Of Searching by keyword");
        wait.visibilityOfWebElement(filterMenu);
        List<SearchVideoResult> searchVideoResultList = new ArrayList<SearchVideoResult>();
        for (WebElement result : searchResults) {
            searchVideoResultList.add(new SearchVideoResult(result));
        }
        return searchVideoResultList;
    }
    
//    @Step(value = "get first 10 Results Of Searching by keyword")
    public List<SearchVideoResult> getFirst10ResultsOfSearch(){
        logger.info("get first 10 Results Of Searching by keyword");
//        wait.visibilityOfWebElement(filterMenu);
        wait.awaitFor(() -> searchResults.size()>=10); // Waiting with awaitillity
        List<SearchVideoResult> searchVideoResultList = new ArrayList<SearchVideoResult>();
        for (int i = 0; i < 10; i++) {
            searchVideoResultList.add(new SearchVideoResult(searchResults.get(i)));
        }
        return searchVideoResultList;
    }
    
//    @Step(value = "open chosen Video")
    public VideoPage openVideoPage(SearchVideoResult searchVideoResult) throws Exception {
        logger.info("start openVideoPage()");
        wait.visibilityOfWebElement(filterMenu);
        searchVideoResult.getLink().click();
        VideoPage videoPage = new VideoPage(driver);
        return videoPage;
    }

}