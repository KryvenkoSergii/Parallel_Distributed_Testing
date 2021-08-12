package com.softserve.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.softserve.pages.HomePage;
import com.softserve.utils.CapabilityFactory;
import com.softserve.utils.ReadProjectProperties;

import io.qameta.allure.Step;

public abstract class TestRunner {
    private final Long ONE_SECOND_DELAY = 1000L;
    //
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    //
    private static ReadProjectProperties readProjectProperties = new ReadProjectProperties();
    protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    private CapabilityFactory capabilityFactory = new CapabilityFactory();
    //

    @AfterClass(alwaysRun = true)
    public void tearDownAfterClass() throws Exception {
        logger.info("lunch tearDownAfterClass()");
//	    presentationSleep(1);
        if (driver != null) {
            driver.remove();
        }
    }

    @BeforeMethod
    @Parameters(value = { "browser" })
    public void setUp(String browser) throws MalformedURLException {
        logger.info("lunch setUp() with a browser " + browser);
        driver.set(new RemoteWebDriver(new URL("http://192.168.136.1:4444/wd/hub"), capabilityFactory.getCapabilities(browser)));
        getDriver().get(readProjectProperties.getBaseUrl());
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
        logger.info("lunch tearDown()");
        if (!result.isSuccess()) {
            logger.warn("Test " + result.getName() + " ERROR");
            // Take Screenshot, save sourceCode, save to log, prepare report, Return to
            // previous state, logout, etc.
        }
        // logout, get(urlLogout), delete cookie, delete cache
        getDriver().close();
    }

    protected void presentationSleep() {
        presentationSleep(1);
    }

    protected void presentationSleep(int seconds) {
        try {
            Thread.sleep(seconds * ONE_SECOND_DELAY); // For Presentation ONLY
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    @Step(value = "load Home page")
    protected HomePage getHomePage() {
        return new HomePage(getDriver());
    }
}