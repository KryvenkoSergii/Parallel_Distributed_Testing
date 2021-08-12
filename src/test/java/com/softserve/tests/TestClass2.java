package com.softserve.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.softserve.business.SearchVideoActions;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class TestClass2 extends TestRunner {
    
    @DataProvider(parallel = true)
    public Object[][] searchSentenceData() {
        return new Object[][] {
                new Object[] { "popular places" },
                new Object[] { "popular people" },
                new Object[] { "popular websites" } 
        };
    }

    @Epic("Parallel DataProvider and Selenium Grid")
    @Feature(value = "Check if the search results contain the words")
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Go to TouTube, make a search by sentence, compare the title results with the search words.")
    @Story(value = "Go to TouTube, make a search by sentence, compare the title results with the search words.")
    @Test(dataProvider = "searchSentenceData")
    public void checkTitleResultsYouTube(final String searchText) {
        SearchVideoActions searchVideoActions = new SearchVideoActions(getDriver());
        searchVideoActions.searchVideo(searchText);
        Assert.assertTrue(searchVideoActions.isResultsListNotEmpty(), "search result is empty");
        Assert.assertTrue(searchVideoActions.isResultTitlesContainSomeWords(searchText),
                "result titles don't contain any search words: " + searchText);
    }
}
