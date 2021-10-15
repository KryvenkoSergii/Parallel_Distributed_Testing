package com.softserve.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.softserve.utils.RegexUtils;

import io.qameta.allure.Step;

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

    // .ytp-ad-player-overlay
    @FindBy(css = ".ytp-ad-player-overlay")
    protected WebElement adVideo2;

    // skip ad button
    // button.ytp-ad-skip-button.ytp-button
    // button>div.ytp-ad-text.ytp-ad-skip-button-text
    // span.ytp-ad-skip-button-icon
    @FindBy(css = "button.ytp-ad-skip-button.ytp-button")
    protected WebElement skipAdVideo;

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
    // .ytp-ad-preview-container.countdown-next-to-thumbnail
    @FindBy(css = ".countdown-next-to-thumbnail")
    protected WebElement adCountdown;

    // ad preview-container
    @FindBy(css = ".ytp-ad-preview-container")
    protected WebElement adPreviewContainer;

    // next video countdown
    @FindBy(css = ".ytp-autonav-endscreen-upnext-header>span")
    protected WebElement nextVideoCountdown;

    // quantity views
    @FindBy(css = ".view-count.style-scope.ytd-video-view-count-renderer")
    protected WebElement viewCount;

    @FindBy(css = "a.ytp-autonav-endscreen-upnext-button.ytp-autonav-endscreen-upnext-play-button")
    protected WebElement nextRecommendedVideo;

    private Actions actionProvider;

    public VideoPage(WebDriver driver) throws Exception {
        super(driver);
        actionProvider = new Actions(driver);
        wait.awaitFor(() -> driver.getCurrentUrl().contains("youtube.com/watch")); // Waiting with awaitillity
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

//    @Step(value = "skip AdVideo")
    public void skipAdVideo() {
        wait.visibilityOfWebElement(videoStream);
        try {
            while (adInfo.isDisplayed() || adVideo2.isDisplayed()) {
                try {
                    moveMouse(durationTime);
                    if (adCountdown.isDisplayed() || skipAdVideo.isDisplayed()) {
                        moveMouse(skipAdVideo);
                        wait.elementToBeClickable(skipAdVideo);
                        skipAdVideo.click();
                    } else {
                        wait.invisibilityOfWebElement(driver.findElement(By.cssSelector(".ytp-ad-player-overlay-instream-info")));
                    }
                } catch (Exception e) {
                    System.err.println("adCountdown Element not present" + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("adInfo Element not present" + e.getMessage());
        }
        moveMouse(playButton);
    }

//    @Step(value = "skip Trial")
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

//    @Step(value = "rewind To End")
    public void rewindToEnd() {
        skipTrial();
        skipAdVideo();
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

//    @Step(value = "check is Autopaly On")
    public boolean isAutopalyOn() {
        moveMouse(autopaly);
        return autopaly.getAttribute("aria-checked").toString().trim().equalsIgnoreCase("true");
    }

//    @Step(value = "check is Autopaly Off")
    public void setAutoplayOff() {
        moveMouse(autopaly);
        if (isAutopalyOn()) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", autopaly, "aria-checked", "false");
        }
    }

//    @Step(value = "set Autopaly On")
    public void setAutoplayOn() {
        moveMouse(playButton);
        if (!isAutopalyOn()) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", autopaly, "aria-checked", "true");
        }
    }

//    @Step(value = "get Duration of video")
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

//    @Step(value = "get Current Time")
    public int getCurrentTime() {
        return RegexUtils.getTime(currentTime.getText());
    }

//    @Step(value = "get Title")
    public String getTitle() {
        moveMouse(title);
        wait.visibilityOfWebElement(title);
        return title.getText().trim();
    }

//    @Step(value = "get Recommendation Videos Quantity")
    public int getRecommendationVideosQuantity() {
        return recommendationVideoResults.size();
    }

//    @Step(value = "if Next Recommendation Video is Available")
    public boolean isNextRecommendationVideoAvailable() {
        return recommendationVideoResults.size() > 0;
    }

//    @Step(value = "get First Recommended Video Title From List")
    public String getFirstRecommendedVideoTitleFromList() {
        return recommendationVideoResults.get(0).findElement(By.id("video-title")).getText().trim();
    }

//    @Step(value = "get First Recommended Video Duration From List")
    public int getFirstRecommendedVideoDurationFromList() {
        return RegexUtils.getTime(recommendationVideoResults.get(0).findElement(By.id("text")).getText().trim());
    }

//    @Step(value = "is Contdown Displayed")
    public boolean isContdownDisplayed() {
        try {
//          wait.visibilityOfWebElement(nextVideoCountdown);
            wait.awaitFor(() -> nextVideoCountdown.isDisplayed()); // Waiting with awaitillity
            return nextVideoCountdown.isDisplayed();
        } catch (Exception e) {
            System.err.println("nextVideoCountdown Element not present");
        }
        return false;
    }

//    @Step(value = "open Next Recommended Video")
    public VideoPage getNextRecommendedVideo() throws Exception {
        isContdownDisplayed();
        nextRecommendedVideo.click();
        return new VideoPage(driver);
    }
}
