package com.softserve.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserve.utils.ExplicitWaitUtil;

import io.qameta.allure.Step;


public class BasePage{
    
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    //
    protected static final long DEFAULT_WAITING_TIME = 60;
    //
    protected WebDriver driver;
    protected ExplicitWaitUtil wait;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new ExplicitWaitUtil(driver);
        PageFactory.initElements(driver, this);
    }
    
    @Step(value = "get current URL address")
    public String getCurrentUrl() {
        String currentURL = driver.getCurrentUrl();
        logger.info("current URL = " + currentURL);
        return currentURL;
    }
}
