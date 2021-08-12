package com.softserve.business;

import org.openqa.selenium.WebDriver;

import com.softserve.pages.HomePage;

public class SearchVideoActions {

    private HomePage homePage;
    private String searchText;

    public SearchVideoActions(WebDriver driver) {
        homePage = new HomePage(driver);
    }

    public void searchVideo(String text) {
        this.searchText = text;
        if (homePage.isSearchFieldVisible()) {
            homePage.enterTextToSearchFieldAndClickSearch(searchText);
        }
    }

    public boolean isResultsListNotEmpty() {
        return homePage.isSearchResultsDisplayed();
    }

    public boolean isResultTitlesContainSomeWords(String text) {
        return homePage.isResultTitlesHaveWords(text);
    }

    public boolean isResultTitlesContainSearchWords() {
        return homePage.isResultTitlesHaveWords(searchText);
    }

}
