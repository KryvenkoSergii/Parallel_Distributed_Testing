package com.softserve.tests.listeners;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.softserve.tests.TestRunner;

public class Retry extends TestRunner implements IRetryAnalyzer {

    private int count = 0;
    private static int maxTry = 1;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {
            if (count < maxTry) {
                count++;
                iTestResult.setStatus(ITestResult.FAILURE);
                extendReportsFailOperations(iTestResult);
                return true;
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }

    public void extendReportsFailOperations(ITestResult iTestResult) {
        logger.info(Status.FAIL + " " + getTestMethodName(iTestResult) + " test is failed.");
        String methodName = iTestResult.getName().toString().trim();
        ITestContext context = iTestResult.getTestContext();
        takeScreenShot(methodName, getDriver());
    }

    public byte[] takeScreenShot(String methodName, WebDriver driver) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = "./test-output/Screenshots/";
        // The below method will save the screen shot with test method name
        try {
            FileUtils.copyFile(scrFile, new File(filePath + methodName + driver.hashCode() + ".png"));
            System.out.println("***Placed screen shot in " + filePath + " ***");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
}