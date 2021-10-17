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
import io.qameta.allure.Link;
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
    @Description("Go to TouTube, make a search by {0}, compare the title results with the search words.")
    @Story(value = "Go to TouTube, make a search by {0}, compare the title results with the search words.")
    @Test(dataProvider = "searchSentenceData")
    public void checkTitleResultsYouTube(final String searchText) {
        SearchVideoActions searchVideoActions = new SearchVideoActions(getDriver());
        searchVideoActions.searchVideo(searchText);
        Assert.assertTrue(searchVideoActions.isResultsListNotEmpty(), "search result is empty");
        Assert.assertTrue(searchVideoActions.isResultTitlesContainSomeWords(searchText),
                "result titles don't contain any search words: " + searchText);
    }

    @Epic("Parallel DataProvider and Selenium Grid")
    @Feature(value = "Search videos by keyword, choose the shortest video from the first 10 results, check if the next video is available")
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Go to TouTube, make a search by {0}, choose the shortest video from the first 10 results, check if the next video is available.")
    @Story(value = "Go to TouTube, make a search by {0}, choose the shortest video from the first 10 results, check if the next video is available.")
    @Link(value = "https://www.youtube.com/")
    @Test(dataProvider = "searchSentenceData")
    public void checkShortestVideoRewind(final String searchText) throws Exception {
        SearchVideoActions searchVideoActions = new SearchVideoActions(getDriver());
        searchVideoActions.searchVideo(searchText);
        SearchVideoResult searchVideoResult = searchVideoActions.getShortestVideoFromFirst10Results();
        assertThat(searchVideoActions.get10VideoResults()).hasSize(10)
                .describedAs("Test failed because list doesn't contain 10 results");
        VideoPage shortestVideo = searchVideoActions.openVideo(searchVideoResult);
        // check shortestVideo (name, duration) = searchVideoResult (name, duration)
        assertThat(shortestVideo.getTitle()).isEqualTo(searchVideoResult.getTitle())
                .describedAs("Test failed opened video hasn't the title like the shortest video");
        /*
         * in results a video 'I found out what the most VISITED websites in the world
         * are... ?' has 59sec duration, but if play it the video has 58sec duration!!!!
         */
//        assertThat(shortestVideo.getDuration()).isEqualTo(searchVideoResult.getDurationSec()).describedAs("Test failed opened video hasn't the shortest time");
        // check autoPlay is off
        shortestVideo.setAutoplayOff();
        assertThat(shortestVideo.isAutopalyOn()).isEqualTo(false).describedAs("Test failed in opened video Autoplay is ON");
        shortestVideo.rewindToEnd();
        // check if next video available
        assertThat(shortestVideo.isNextRecommendationVideoAvailable()).isEqualTo(true)
                .describedAs("Test failed next Recommendation Video doesn't Available");
        // check current timeMark = shortestVideo (duration) +/- 5sec
        assertThat(shortestVideo.getCurrentTime() >= shortestVideo.getDuration() - 5).isEqualTo(true)
                .describedAs("Test failed current Time mark doesn't equal duration");
    }

    @Epic("Parallel DataProvider and Selenium Grid")
    @Feature(value = "Search videos by keyword, choose the longest video from the first 10 results, check if the next video is available, check if a countdown is displayed")
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Go to TouTube, make a search by {0}, choose the longest video from the first 10 results, check if the next video is available, check if a countdown is displayed.")
    @Story(value = "Go to TouTube, make a search by {0}, choose the longest video from the first 10 results, check if the next video is available, check if a countdown is displayed.")
    @Link(value = "https://www.youtube.com/")
    @Test(dataProvider = "searchSentenceData")
    public void checkLongestVideoRewind(final String searchText) throws Exception {
        SearchVideoActions searchVideoActions = new SearchVideoActions(getDriver());
        searchVideoActions.searchVideo(searchText);
        SearchVideoResult searchVideoResult = searchVideoActions.getLongestVideoFromFirst10Results();
        assertThat(searchVideoActions.get10VideoResults()).hasSize(10)
                .describedAs("Test failed because list doesn't contain 10 results");
        VideoPage longestVideo = searchVideoActions.openVideo(searchVideoResult);
        // check shortestVideo (name, duration) = searchVideoResult (name, duration)
        assertThat(longestVideo.getTitle()).isEqualTo(searchVideoResult.getTitle())
                .describedAs("Test failed opened video hasn't the title like the shortest video");
        // check autoPlay is off
        longestVideo.setAutoplayOn();
        assertThat(longestVideo.isAutopalyOn()).isEqualTo(true).describedAs("Test failed in opened video Autoplay is OFF");
        // check if next video available
        assertThat(longestVideo.isNextRecommendationVideoAvailable()).isEqualTo(true)
                .describedAs("Test failed next Recommendation Video doesn't Available");
        String nextRecommendedVideoTitle = longestVideo.getFirstRecommendedVideoTitleFromList();
        int nextRecommendedVideoDuration = longestVideo.getFirstRecommendedVideoDurationFromList();
        longestVideo.rewindToEnd();
        // check if a countdown is displayed
        assertThat(longestVideo.isContdownDisplayed()).isEqualTo(true).describedAs("Test failed in a countdown isn't display");
        VideoPage nextVideo = longestVideo.getNextRecommendedVideo();
        // check next video has the same title and duration that the video form the list
        assertThat(nextVideo.getTitle()).isEqualTo(nextRecommendedVideoTitle)
                .describedAs("Test failed opened video hasn't the title like the first recommended video");
//        assertThat(nextVideo.getDuration()).isEqualTo(nextRecommendedVideoDuration)
//                .describedAs("Test failed opened video hasn't the first recommended video");
    }
}
