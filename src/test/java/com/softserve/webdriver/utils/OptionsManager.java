package com.softserve.webdriver.utils;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OptionsManager {

    public static ChromeOptions getChromeOptions() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--disable-popup-blocking");
        return chromeOptions;
    }

    public static FirefoxOptions getFirefoxOptions() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--start-maximised");
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setPreference("network.proxy.type", 0);
        firefoxOptions.setCapability(FirefoxDriver.PROFILE, profile);
        return firefoxOptions;
    }
    
    public static EdgeOptions getEdgeOptions() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setCapability("browser", "Edge");
        edgeOptions.setCapability("acceptSslCerts", true);
        edgeOptions.setCapability("os", "Windows");
        edgeOptions.setCapability("os_version", "10");
        return edgeOptions;
    }
}

