package com.softserve.webdriver.utils;

import org.openqa.selenium.Capabilities;

public class CapabilityFactory {

    private Capabilities capabilities;

    public Capabilities getCapabilities(String browser) {
        if (browser.equals("firefox")) {
            capabilities = OptionsManager.getFirefoxOptions();
        } else if (browser.equals("edge")) {
            capabilities = OptionsManager.getEdgeOptions();
        } else {
            capabilities = OptionsManager.getChromeOptions();
        }
        return capabilities;
    }

}
