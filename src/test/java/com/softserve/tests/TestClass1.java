package com.softserve.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.softserve.business.SearchVideoActions;
import com.softserve.utils.Retry;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class TestClass1 extends TestRunner {

    @Epic("Parallel excution using TestNG Selenium Grid")
    @Feature(value = "Check if the search results contain the words: 'popular' and 'music'")
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Go to TouTube, make a search by sentence 'popular music', compare the title results with the search words.")
    @Story(value = "Go to TouTube, make a search by sentence, compare the title results with the search words.")
    @Test(retryAnalyzer = Retry.class)
    public void checkPopularMusic() {
        final String searchText = "popular music";
        SearchVideoActions searchVideoActions = new SearchVideoActions(getDriver());
        searchVideoActions.searchVideo(searchText);
        Assert.assertTrue(searchVideoActions.isResultsListNotEmpty(), "search result is empty");
        Assert.assertTrue(searchVideoActions.isResultTitlesContainSomeWords(searchText),
                "result titles don't contain any search words: " + searchText);
    }

    @Epic("Parallel excution using TestNG Selenium Grid")
    @Feature(value = "Check if the search results contain the words: 'popular' and 'movies'")
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Go to TouTube, make a search by sentence 'popular movies', compare the title results with the search words.")
    @Story(value = "Go to TouTube, make a search by sentence, compare the title results with the search words.")
    @Test(retryAnalyzer = Retry.class)
    public void checkPopularMovies() {
        final String searchText = "popular movies";
        SearchVideoActions searchVideoActions = new SearchVideoActions(getDriver());
        searchVideoActions.searchVideo(searchText);
        Assert.assertTrue(searchVideoActions.isResultsListNotEmpty(), "search result is empty");
        Assert.assertTrue(searchVideoActions.isResultTitlesContainSomeWords(searchText),
                "result titles don't contain any search words: " + searchText);
    }

    @Epic("Parallel excution using TestNG Selenium Grid")
    @Feature(value = "Check if the search results contain the words: 'popular' and 'books'")
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Go to TouTube, make a search by sentence 'popular books', compare the title results with the search words.")
    @Story(value = "Go to TouTube, make a search by sentence, compare the title results with the search words.")
    @Test
    public void checkPopularBooks() {
        final String searchText = "popular books";
        SearchVideoActions searchVideoActions = new SearchVideoActions(getDriver());
        searchVideoActions.searchVideo(searchText);
        Assert.assertTrue(searchVideoActions.isResultsListNotEmpty(), "search result is empty");
        Assert.assertTrue(searchVideoActions.isResultTitlesContainSomeWords(searchText),
                "result titles don't contain any search words: " + searchText);
    }
}
