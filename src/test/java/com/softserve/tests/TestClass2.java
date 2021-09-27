package com.softserve.tests;

import static org.assertj.core.api.Assertions.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.softserve.business.SearchVideoActions;
import com.softserve.pages.SearchVideoResult;
import com.softserve.pages.VideoPage;

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
//                new Object[] { "popular places" },
//                new Object[] { "popular people" },
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
    
    @Test(dataProvider = "searchSentenceData")
    public void checkVideoRewind(final String searchText) {
        SearchVideoActions searchVideoActions = new SearchVideoActions(getDriver());
        searchVideoActions.searchVideo(searchText);
        SearchVideoResult searchVideoResult = searchVideoActions.getShortestVideoFromFirst10Results();
        assertThat(searchVideoActions.get10VideoResults()).hasSize(10).describedAs("Test failed because list doesn't contain 10 results");
        VideoPage shortestVideo = searchVideoActions.openVideo(searchVideoResult);
        // check shortestVideo (name, duration) = searchVideoResult (name, duration)
        assertThat(shortestVideo.getTitle()).isEqualTo(searchVideoResult.getTitle()).describedAs("Test failed opened video hasn't the title like the shortest video");
        assertThat(shortestVideo.getDuration()).isEqualTo(searchVideoResult.getDurationSec()).describedAs("Test failed opened video hasn't the shortest time");
        // check autoPlay is off
//        assertThat(shortestVideo.setAutoplayOff()).isEqualTo(true).describedAs("Test failed in opened video Autoplay is ON");
        shortestVideo.rewindToEnd();
        // check if next video available
        assertThat(shortestVideo.isNextRecommendationVideoAvailable()).isEqualTo(true).describedAs("Test failed next Recommendation Video doesn't Available");
        // check current timeMark = shortestVideo (duration) +/- 5sec
        assertThat(shortestVideo.getCurrentTime()>=shortestVideo.getDuration()-5).isEqualTo(true).describedAs("Test failed current Time mark doesn't equal duration");
    }
}
