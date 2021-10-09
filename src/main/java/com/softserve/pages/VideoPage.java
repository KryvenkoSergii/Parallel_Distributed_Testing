package com.softserve.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.softserve.utils.RegexUtils;

public class VideoPage extends BasePage {

    @FindBy(id = "search-icon-legacy")
    protected WebElement searchButton;

    // duration time
    @FindBy(css = ".ytp-time-display .ytp-time-duration")
    protected WebElement durationTime;

    // current time
    @FindBy(css = ".ytp-time-display .ytp-time-current")
    protected WebElement currentTime;

    // next recommended videos
    @FindBy(css = ".ytd-watch-next-secondary-results-renderer #dismissible")
    protected List<WebElement> recommendationVideoResults;

    // autopaly
    @FindBy(css = ".ytp-autonav-toggle-button")
    protected WebElement autopaly;

    // rewind
    @FindBy(css = ".video-stream")
    protected WebElement videoStream;

    // ytp-ad-preview-container
    @FindBy(css = ".ytp-ad-preview-container")
    protected WebElement adVideo;

    // .ytp-ad-skip-button-icon
    @FindBy(css = ".ytp-ad-skip-button-icon")
    protected WebElement skipAdVideo;

    // .ytp-ad-text.ytp-ad-preview-text
    @FindBy(css = ".ytp-ad-text.ytp-ad-preview-text")
    protected WebElement skipAdVideo2;
    
    @FindBy(css = ".ytp-ad-skip-button ytp-button")
    protected WebElement skipAdVideo3;

    // title
    @FindBy(css = "h1.title.ytd-video-primary-info-renderer")
    protected WebElement title;

    // youtube premium pop-up
    @FindBy(css = "tp-yt-paper-dialog.ytd-popup-container")
    protected WebElement youtubePremiumPopUp;

    // youtube premium pop-up Skip trial button
    @FindBy(css = "ytd-button-renderer#dismiss-button tp-yt-paper-button>yt-formatted-string")
    protected WebElement skipTrialButton;
    
    @FindBy(css = "#dismiss-button tp-yt-paper-button#button")
    protected WebElement skipTrialButton2;
    
    @FindBy(css = "tp-yt-paper-dialog #dismiss-button")
    protected WebElement skipTrialButton3;

    // play button
    @FindBy(css = ".ytp-play-button")
    protected WebElement playButton;

    // ad info
    @FindBy(css = ".ytp-ad-player-overlay-instream-info")
    protected WebElement adInfo;

    // ad countdown
    @FindBy(css = ".countdown-next-to-thumbnail")
    protected WebElement adCountdown;

    // ad preview-container
    @FindBy(css = ".ytp-ad-preview-container")
    protected WebElement adPreviewContainer;

    // next video countdown
    @FindBy(css = ".ytp-autonav-endscreen-upnext-header>span")
    protected WebElement nextVideoCountdown;
    
    @FindBy(css = "a.ytp-autonav-endscreen-upnext-button.ytp-autonav-endscreen-upnext-play-button")
    protected WebElement nextRecommendedVideo;

    private Actions actionProvider;

    public VideoPage(WebDriver driver) throws Exception {
        super(driver);
        actionProvider = new Actions(driver);
        skipAdVideo();
        skipTrial();
    }

    private void moveMouse(WebElement webElement) {
        try {
            actionProvider.moveToElement(webElement).perform();
        } catch (NoSuchElementException e) {
            System.err.println(webElement.getTagName() + " Element not present");
        }
    }

    public void skipAdVideo() {
        try {
            wait.visibilityOfWebElement(adInfo);
            while (adInfo.isDisplayed()) {
                if (getDuration() > 15) {
                    moveMouse(skipAdVideo2);
                    wait.elementToBeClickable(skipAdVideo2);
                    skipAdVideo2.click();
                } else {
                    wait.invisibilityOfWebElement(driver.findElement(By.cssSelector(".ytp-ad-player-overlay-instream-info")));
                }
            }
        } catch (Exception e) {
            System.err.println("adInfo Element not present");
        }
        moveMouse(playButton);
    }

    public void skipTrial() {
        try {
            if (skipTrialButton2.isDisplayed()) {
                wait.elementToBeClickable(skipTrialButton2);
                skipTrialButton2.click();
            }
        } catch (Exception e) {
            System.err.println("skipTrialButton Element not present");
        }
    }

    public void rewindToEnd() {
        skipTrial();
        moveMouse(playButton);
        wait.visibilityOfWebElement(durationTime);
        skipTrial();
        moveMouse(autopaly);
        int durationVideoTime = RegexUtils.getTime(durationTime.getText());
        while (durationVideoTime - 6 >= RegexUtils.getTime(currentTime.getText())) {
            moveMouse(playButton);
            playButton.sendKeys(Keys.ARROW_RIGHT);
        }
    }

    public boolean isAutopalyOn() {
        moveMouse(autopaly);
        return autopaly.getAttribute("aria-checked").toString().trim().equalsIgnoreCase("true");
    }

    public void setAutoplayOff() {
        moveMouse(autopaly);
        if (isAutopalyOn()) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", autopaly, "aria-checked", "false");
        }
    }

    public void setAutoplayOn() {
        moveMouse(playButton);
        if (!isAutopalyOn()) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", autopaly, "aria-checked", "true");
        }
    }

    public int getDuration() {
        try {
            moveMouse(durationTime);
            wait.visibilityOfWebElement(durationTime);
        } catch (NoSuchElementException e) {
            System.err.println("durationTime Element not present");
        }
//        System.out.println("---Video Title = " + getTitle());
//        System.out.println("---setDuration text = " + durationTime.getText());
//        System.out.println("---setDuration RegexUtils.getTime(text) = " + RegexUtils.getTime(durationTime.getText()));
        return RegexUtils.getTime(durationTime.getText());
    }

    public int getCurrentTime() {
        return RegexUtils.getTime(currentTime.getText());
    }

    public String getTitle() {
        moveMouse(title);
        wait.visibilityOfWebElement(title);
        return title.getText().trim();
    }

    public int getRecommendationVideosQuantity() {
        return recommendationVideoResults.size();
    }

    public boolean isNextRecommendationVideoAvailable() {
        return recommendationVideoResults.size() > 0;
    }

    public String getFirstRecommendedVideoTitleFromList() {
        return recommendationVideoResults.get(0).findElement(By.id("video-title")).getText().trim();
    }

    public int getFirstRecommendedVideoDurationFromList() {
        return RegexUtils.getTime(recommendationVideoResults.get(0).findElement(By.id("text")).getText().trim());
    }

    public boolean isContdownDisplayed() {
        try {
            wait.visibilityOfWebElement(nextVideoCountdown);
            return nextVideoCountdown.isDisplayed();
        } catch (Exception e) {
            System.err.println("nextVideoCountdown Element not present");
        }
        return false;
    }

    public VideoPage getNextRecommendedVideo() throws Exception {
        isContdownDisplayed();
        wait.elementToBeClickable(nextRecommendedVideo);
        nextRecommendedVideo.click();
        return new VideoPage(driver);
    }

}
