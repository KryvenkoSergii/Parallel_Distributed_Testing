package com.softserve.business;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.softserve.pages.HomePage;
import com.softserve.pages.SearchVideoResult;
import com.softserve.pages.VideoPage;

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

    public SearchVideoResult getShortestVideoFromFirst10Results() {
        List<SearchVideoResult> searchVideoResultList = homePage.getFirst10ResultsOfSearch();
        SearchVideoResult shortestVideo = searchVideoResultList.get(0);
        for (SearchVideoResult searchVideoResult : searchVideoResultList) {
            if(shortestVideo.getDurationSec()>searchVideoResult.getDurationSec()) {
                shortestVideo = searchVideoResult;
            }
        }
//        System.out.println("shortestVideo Title - " + shortestVideo.getTitle());
//        System.out.println("shortestVideo Duration - " + shortestVideo.getDurationSec());
        return shortestVideo;
    }
    
    public SearchVideoResult getLongestVideoFromFirst10Results() {
        List<SearchVideoResult> searchVideoResultList = homePage.getFirst10ResultsOfSearch();
        SearchVideoResult longestVideo = searchVideoResultList.get(0);
        for (SearchVideoResult searchVideoResult : searchVideoResultList) {
            if(longestVideo.getDurationSec()<searchVideoResult.getDurationSec()) {
                longestVideo = searchVideoResult;
            }
        }
//        System.out.println("shortestVideo Title - " + shortestVideo.getTitle());
//        System.out.println("shortestVideo Duration - " + shortestVideo.getDurationSec());
        return longestVideo;
    }
    
    public List<SearchVideoResult> get10VideoResults(){
        return homePage.getFirst10ResultsOfSearch();
    }
    
    public VideoPage openVideo(SearchVideoResult searchVideoResult) throws Exception {
        return homePage.openVideoPage(searchVideoResult);
    }
}
