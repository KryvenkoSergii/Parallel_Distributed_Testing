package com.softserve.tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends TestRunner implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {

        logger.info("I am in onStart method " + iTestContext.getName());

        iTestContext.setAttribute("WebDriver", getDriver());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        logger.info("I am in onFinish method " + iTestContext.getName());
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        logger.info(getTestMethodName(iTestResult) + " test is starting.");

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        logger.info(getTestMethodName(iTestResult) + " test is succeed.");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        logger.info(getTestMethodName(iTestResult) + " test is failed.");
        String methodName = iTestResult.getName().toString().trim();
        ITestContext context = iTestResult.getTestContext();
//        WebDriver driver = (WebDriver) context.getAttribute("driver");
        takeScreenShot(methodName, getDriver());

    }

//    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] takeScreenShot(String methodName, WebDriver driver) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = "./test-output/Screenshots/";
        // The below method will save the screen shot in d drive with test method name
        try {
            FileUtils.copyFile(scrFile, new File(filePath + methodName + driver.hashCode() + ".png"));
            System.out.println("***Placed screen shot in " + filePath + " ***");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        logger.info(getTestMethodName(iTestResult) + " test is skipped.");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        logger.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }
}