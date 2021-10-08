package com.softserve.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.softserve.utils.RegexUtils;

public class SearchVideoResult {

    private WebElement link;
    private String title;
    private int durationSec;

    public SearchVideoResult(WebElement searchResult) {
        setLink(searchResult);
        setTitle(searchResult);
        setDuration(searchResult);
    }

    private void setLink(WebElement searchResult) {
        this.link = searchResult.findElement(By.cssSelector("#title-wrapper #video-title"));
    }

    private void setTitle(WebElement searchResult) {
        this.title = searchResult.findElement(By.cssSelector("#title-wrapper #video-title")).getText().trim();
    }

    private void setDuration(WebElement searchResult) {
        String text = searchResult.findElement(By.cssSelector("#overlays #text")).getAttribute("aria-label");
//        System.out.println("---setDuration text = " + text);
//        System.out.println("---setDuration RegexUtils.getTime(text) = " + RegexUtils.getTime(text));
        this.durationSec = RegexUtils.getTime(text);
    }

    public WebElement getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public int getDurationSec() {
        return durationSec;
    }

}
