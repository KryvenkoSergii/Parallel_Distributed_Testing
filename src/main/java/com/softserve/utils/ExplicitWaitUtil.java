package com.softserve.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplicitWaitUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static ReadProjectProperties readProjectProperties = new ReadProjectProperties();
    private WebDriver driver;
    private WebDriverWait wait;

    public ExplicitWaitUtil(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, readProjectProperties.geExplicitWaitDelay());
    }
    
    public void elementToBeClickable(WebElement webElement) {
        logger.trace("wait until an element is clickable");
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wait.until(ExpectedConditions
                .elementToBeClickable(webElement));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    
    public void visibilityOfWebElement(WebElement webElement) {
        logger.trace("wait until a webElement is visible");
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wait.until(ExpectedConditions
                .visibilityOf(webElement));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    
    public void presenceOfElementLocated(By locator) {
        logger.trace("wait until an element is present");
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wait.until(ExpectedConditions
                .presenceOfElementLocated(locator));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    
    public void elementToBeSelected(WebElement webElement) {
        logger.trace("wait until an element is visible");
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wait.until(ExpectedConditions
                .elementToBeSelected(webElement));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    
    public void elementHasStyleDisplayNone(String selectorXPath) {
        logger.trace("wait until an element has CSS Style");
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath(selectorXPath + "[contains(@style, 'display: none')]")));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    
    public void elementIsStalenessOf(WebElement webElement) {
        logger.trace("wait until an element is no longer attached to the DOM");
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wait.until(ExpectedConditions
                .stalenessOf(webElement));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    
    public void waitForPageLoadComplete(long timeToWait) {
        new WebDriverWait(driver, timeToWait).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void waitForAjaxToComplete(long timeToWait) {
        new WebDriverWait(driver, timeToWait).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return window.jQuery != undefined && jQuery.active == 0;"));
    }
    
    public void waitForAjaxToCompletePdp(long timeToWait) {
        new WebDriverWait(driver, timeToWait).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return window.jQuery != undefined && jQuery.active <=2;"));
    }
}
