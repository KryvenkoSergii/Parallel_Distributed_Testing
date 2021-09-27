package com.softserve.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.softserve.utils.ExplicitWaitUtil;
import com.softserve.utils.RegexUtils;

public class VideoPage extends BasePage {
    
    // duration time
    @FindBy(css = ".ytp-time-display .ytp-time-duration")
    protected WebElement durationTime;
    
    // current time
    @FindBy(css = ".ytp-time-display .ytp-time-current")
    protected WebElement currentTime;
    
    // next recommendation video
    @FindBy(css = ".ytd-watch-next-secondary-results-renderer")
    protected List<WebElement> recommendationVideoResults;
    
    // autopaly
    @FindBy(css = ".ytp-autonav-toggle-button")
    protected WebElement autopaly;
    
    // rewind
    @FindBy(css = ".video-stream")
    protected WebElement videoStream;
    
    // ytp-ad-preview-container if has css style: display: none
    @FindBy(css = ".ytp-ad-preview-container")
    protected WebElement adVideo;
    
    // .ytp-ad-skip-button-icon
    @FindBy(css = ".ytp-ad-skip-button-icon")
    protected WebElement skipAdVideo;
    
    // title
    @FindBy(css = "h1.title.ytd-video-primary-info-renderer")
    protected WebElement title;
    
    // youtube premium pop-up
    @FindBy(css = "tp-yt-paper-dialog.ytd-popup-container")
    protected WebElement youtubePremiumPopUp;
    
    // youtube premium pop-up Skip trial button
    @FindBy(css = "tp-yt-paper-dialog ytd-button-renderer#dismiss-button #button")
    protected WebElement skipTrialButton;
    
    // play button
    @FindBy(css = ".ytp-play-button")
    protected WebElement playButton;
    
    ExplicitWaitUtil wait;
    
    public VideoPage(WebDriver driver) {
        super(driver);
        wait = new ExplicitWaitUtil(driver);
//        skipTrial();
        skipAdVideo();
    }
    
    public void skipAdVideo() {
        if(driver.findElement(By.cssSelector(".ytp-ad-preview-container")).isDisplayed()) {
            String selectorXPath = "//span[contains(@class,'ytp-ad-preview-container')]";
            wait.elementHasStyleDisplayNone(selectorXPath);
            skipAdVideo.click();
        }
    }
    
    public void skipTrial() {
        if(youtubePremiumPopUp.isDisplayed()) {
            skipTrialButton.click();
        }
    }
    
    public void rewindToEnd() {
//        int currentVideoTime = RegexUtils.getTime(currentTime.getText());
        int durationVideoTime = RegexUtils.getTime(durationTime.getText());
        while(durationVideoTime-6>=RegexUtils.getTime(currentTime.getText())) {
            playButton.sendKeys(Keys.ARROW_RIGHT);
            System.out.println("---durationVideoTime-6 = " + (durationVideoTime-6) + "; currentTime = " + RegexUtils.getTime(currentTime.getText()));
        }
    }
    
    public boolean setAutoplayOff() {
        if(autopaly.getAttribute("aria-checked")=="true") {
//           setAttribute(autopaly, "aria-checked", "true");
           JavascriptExecutor js = (JavascriptExecutor) driver;
           js.executeScript("document.getElementsByClassName('ytd-watch-next-secondary-results-renderer').setAttribute('aria-checked', 'false')");
        }
        return autopaly.getAttribute("aria-checked") == "false" ? true : false;
    }
    
    public boolean setAutoplayOn() {
        if(autopaly.getAttribute("aria-checked")=="false") {
           JavascriptExecutor js = (JavascriptExecutor) driver;
           js.executeScript("document.getElementsByClassName('ytd-watch-next-secondary-results-renderer').setAttribute('aria-checked', 'true')");
        }
        return autopaly.getAttribute("aria-checked") == "true" ? true : false;
    }
    
    public int getDuration() {
        return  RegexUtils.getTime(durationTime.getText());
    }
    
    public int getCurrentTime() {
        return RegexUtils.getTime(currentTime.getText());
    }
    
    public String getTitle() {
        return title.getText().trim();
    }
    
    public int getRecommendationVideosQuantity() {
        return recommendationVideoResults.size();
    }
    
    public boolean isNextRecommendationVideoAvailable() {
        return recommendationVideoResults.size()>0;
    }

}
